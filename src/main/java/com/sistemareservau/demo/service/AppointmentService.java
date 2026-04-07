package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.*;
import com.sistemareservau.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final OfficeRepository officeRepository;
    private final AppointmentTypeRepository typeRepository;
    private final DoctorScheduleRepository scheduleRepository;

    // ==================== CREAR CITA ====================

    public Appointment createAppointment(UUID patientId,
                                         UUID doctorId,
                                         UUID officeId,
                                         UUID typeId,
                                         LocalDate date,
                                         LocalTime startTime) {

        //Validar paciente
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (patient.getStatus() != PatientStatus.ACTIVE) {
            throw new RuntimeException("Paciente inactivo");
        }

        //Validar doctor
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        if (doctor.getStatus() != DoctorStatus.ACTIVE) {
            throw new RuntimeException("Doctor inactivo");
        }

        //Validar consultorio
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));

        if (office.getStatus() != OfficeStatus.AVAILABLE) {
            throw new RuntimeException("Consultorio inactivo");
        }

        //Validar fecha futura
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentDateTime = LocalDateTime.of(date, startTime);

        if (appointmentDateTime.isBefore(now)) {
            throw new RuntimeException("No se puede agendar en el pasado");
        }

        //Obtener tipo de cita
        AppointmentType type = typeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Tipo de cita no encontrado"));

        //Calcular hora fin
        LocalTime endTime = startTime.plusMinutes(type.getDurationMinutes());

        //Validar horario del doctor
        DayOfWeek day = date.getDayOfWeek();

        List<DoctorSchedule> schedules =
                scheduleRepository.findByDayOfWeekAndDoctor(day, doctor);

        boolean validSchedule = schedules.stream().anyMatch(s ->
                !startTime.isBefore(s.getStartTime()) &&
                !endTime.isAfter(s.getEndTime())
        );

        if (!validSchedule) {
            throw new RuntimeException("Fuera del horario del doctor");
        }

        //Validar traslape DOCTOR
        boolean doctorOverlap = appointmentRepository.existsOverlappingAppointment(
                doctor, date, startTime, endTime);

        if (doctorOverlap) {
            throw new RuntimeException("El doctor ya tiene una cita en ese horario");
        }

        //Validar traslape CONSULTORIO
        boolean officeOverlap = appointmentRepository.existsOverlappingAppointmentByOffice(
                office, date, startTime, endTime);

        if (officeOverlap) {
            throw new RuntimeException("El consultorio ya está ocupado");
        }

        //Validar traslape PACIENTE
        boolean patientOverlap = appointmentRepository.existsOverlappingAppointmentByPatient(
                patient, date, startTime, endTime);

        if (patientOverlap) {
            throw new RuntimeException("El paciente ya tiene una cita en ese horario");
        }

        // Crear cita
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .office(office)
                .appointmentType(type)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        return appointmentRepository.save(appointment);
    }

    // ==================== CONFIRMAR ====================

    public void confirmAppointment(UUID id) {

        Appointment appointment = getById(id);

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new RuntimeException("Solo citas SCHEDULED pueden confirmarse");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
    }

    // ==================== CANCELAR ====================

    public void cancelAppointment(UUID id, String reason) {

        Appointment appointment = getById(id);

        if (appointment.getStatus() == AppointmentStatus.COMPLETED ||
            appointment.getStatus() == AppointmentStatus.NO_SHOW) {
            throw new RuntimeException("No se puede cancelar esta cita");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setNotes(reason);

        appointmentRepository.save(appointment);
    }

    // ==================== COMPLETAR ====================

    public void completeAppointment(UUID id) {

        Appointment appointment = getById(id);

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new RuntimeException("Solo citas CONFIRMED pueden completarse");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(
                appointment.getDate(),
                appointment.getStartTime()
        );

        if (now.isBefore(start)) {
            throw new RuntimeException("No se puede completar antes de iniciar");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    // ==================== NO SHOW ====================

    public void markNoShow(UUID id) {

        Appointment appointment = getById(id);

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new RuntimeException("Solo citas CONFIRMED pueden marcarse NO_SHOW");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(
                appointment.getDate(),
                appointment.getStartTime()
        );

        if (now.isBefore(start)) {
            throw new RuntimeException("Aún no inicia la cita");
        }

        appointment.setStatus(AppointmentStatus.NO_SHOW);
        appointmentRepository.save(appointment);
    }

    // ==================== AUX ====================

    public Appointment getById(UUID id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }
}