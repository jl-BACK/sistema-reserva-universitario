package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.Doctor;
import com.sistemareservau.demo.model.DoctorSchedule;
import com.sistemareservau.demo.repository.DoctorScheduleRepository;
import com.sistemareservau.demo.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public DoctorSchedule createSchedule(UUID doctorId, DoctorSchedule schedule) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        schedule.setDoctor(doctor);

        return scheduleRepository.save(schedule);
    }

    public List<DoctorSchedule> getByDoctorAndDay(UUID doctorId, java.time.DayOfWeek day) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        return scheduleRepository.findByDayOfWeekAndDoctor(day, doctor);
    }
}