package com.sistemareservau.demo.service;

import com.sistemareservau.demo.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AppointmentRepository appointmentRepository;

    //OCUPACIÓN POR CONSULTORIO 

    public List<Object[]> getOfficeOccupancyByDate(LocalDate date) {

        return appointmentRepository.countAppointmentsByOfficeAndDate(date);
    }

    //CANCELADAS Y NO SHOW POR ESPECIALIDAD 

    public List<Object[]> getCancelledAndNoShowBySpecialty() {

        return appointmentRepository.countCancelledAndNoShowBySpecialty();
    }

    //RANKING DE DOCTORES

    public List<Object[]> getTopDoctors() {

        return appointmentRepository.findTopDoctorsByCompletedAppointments();
    }

    //PACIENTES CON MÁS NO SHOW

    public List<Object[]> getTopPatientsWithNoShow(LocalDate startDate, LocalDate endDate) {

        return appointmentRepository.findTopPatientsWithNoShow(startDate, endDate);
    }
}