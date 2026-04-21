package com.sistemareservau.demo.service;

import com.sistemareservau.demo.dto.request.CreateDoctorScheduleRequest;
import com.sistemareservau.demo.dto.response.DoctorScheduleResponse;
import com.sistemareservau.demo.exception.ResourceNotFoundException;
import com.sistemareservau.demo.model.Doctor;
import com.sistemareservau.demo.model.DoctorSchedule;
import com.sistemareservau.demo.repository.DoctorScheduleRepository;
import com.sistemareservau.demo.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public DoctorScheduleResponse createSchedule(UUID doctorId, CreateDoctorScheduleRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado"));

        // para Validar que no se crucen horarios
        
        DoctorSchedule schedule = DoctorSchedule.builder()
                .doctor(doctor)
                .dayOfWeek(request.getDiaSemana())
                .startTime(request.getHoraInicio())
                .endTime(request.getHoraFin())
                .build();

        return mapToResponse(scheduleRepository.save(schedule));
    }

    public List<DoctorScheduleResponse> getDoctorSchedules(UUID doctorId) {
        return scheduleRepository.findByDoctorIdAndActiveTrue(doctorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private DoctorScheduleResponse mapToResponse(DoctorSchedule schedule) {
        return DoctorScheduleResponse.builder()
                .id(schedule.getId())
                .diaSemana(schedule.getDayOfWeek())
                .horaInicio(schedule.getStartTime())
                .horaFin(schedule.getEndTime())
                .activo(schedule.getActive())
                .build();
    }
}