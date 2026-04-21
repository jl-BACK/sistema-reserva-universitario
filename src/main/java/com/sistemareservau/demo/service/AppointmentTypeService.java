package com.sistemareservau.demo.service;

import com.sistemareservau.demo.dto.request.CreateAppointmentTypeRequest;
import com.sistemareservau.demo.dto.response.AppointmentTypeResponse;
import com.sistemareservau.demo.exception.ConflictException;
import com.sistemareservau.demo.model.AppointmentType;
import com.sistemareservau.demo.repository.AppointmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentTypeService {

    private final AppointmentTypeRepository appointmentTypeRepository;

    // POST /api/appointment-types
    public AppointmentTypeResponse create(CreateAppointmentTypeRequest request) {
        if (appointmentTypeRepository.existsByType(request.getNombre())) {
            throw new ConflictException("El tipo de cita '" + request.getNombre() + "' ya existe.");
        }

        AppointmentType type = AppointmentType.builder()
                .type(request.getNombre())
                .durationMinutes(request.getDuracionMinutos())
                .descripcion(request.getDescripcion())
                .active(true)
                .build();

        return mapToResponse(appointmentTypeRepository.save(type));
    }

    // GET /api/appointment-types
    public List<AppointmentTypeResponse> getAll() {
        return appointmentTypeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AppointmentTypeResponse mapToResponse(AppointmentType Appotype) {
        return AppointmentTypeResponse.builder()
                .id(Appotype.getId())
                .nombre(Appotype.getType())
                .duracionMinutos(Appotype.getDurationMinutes())
                .descripcion(Appotype.getDescripcion())
                .activo(Appotype.isActive())
                .build();
    }
}
