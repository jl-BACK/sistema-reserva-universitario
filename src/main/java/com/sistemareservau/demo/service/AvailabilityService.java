package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.*;
import com.sistemareservau.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentTypeRepository typeRepository;


    public List<LocalTime> getAvailableSlots(UUID doctorId,
                                         UUID typeId,
                                         LocalDate date) {

    //Obtener doctor
    Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

    //Obtener tipo de cita (duración)
    AppointmentType type = typeRepository.findById(typeId)
            .orElseThrow(() -> new RuntimeException("Tipo de cita no encontrado"));

    int duration = type.getDurationMinutes();

    //Obtener horario del doctor ese día
    DayOfWeek day = date.getDayOfWeek();

    List<DoctorSchedule> schedules =
            scheduleRepository.findByDayOfWeekAndDoctor(day, doctor);

    if (schedules.isEmpty()) {
        throw new RuntimeException("El doctor no trabaja ese día");
    }

    //Obtener citas existentes
    List<Appointment> appointments =
            appointmentRepository.findAppointmentsByDoctorAndDate(doctor, date);

    List<LocalTime> availableSlots = new ArrayList<>();

    //Generar slots correctamente
    for (DoctorSchedule schedule : schedules) {

        LocalTime current = schedule.getStartTime();

        while (!current.plusMinutes(duration).isAfter(schedule.getEndTime())) {

            LocalTime startSlot = current; // variable auxiliar
            LocalTime end = startSlot.plusMinutes(duration);

            boolean overlap = appointments.stream().anyMatch(a ->
                    startSlot.isBefore(a.getEndTime()) &&
                    end.isAfter(a.getStartTime())
            );

            if (!overlap) {
                availableSlots.add(startSlot);
            }

            current = current.plusMinutes(duration);
        }
    }

    return availableSlots;
}
}