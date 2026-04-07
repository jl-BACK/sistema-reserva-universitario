package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.AppointmentType;
import com.sistemareservau.demo.repository.AppointmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentTypeService {

    private final AppointmentTypeRepository appointmentTypeRepository;

    public AppointmentType createType(String type, Integer durationMinutes) {

        appointmentTypeRepository.findByType(type).ifPresent(t -> {
            throw new RuntimeException("El tipo de cita ya existe");
        });

        AppointmentType appointmentType = AppointmentType.builder()
                .type(type)
                .durationMinutes(durationMinutes)
                .createdAt(Instant.now())
                .build();

        return appointmentTypeRepository.save(appointmentType);
    }

    public List<AppointmentType> getAll() {
        return appointmentTypeRepository.findAll();
    }

    public AppointmentType getById(UUID id) {
        return appointmentTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de cita no encontrado"));
    }
}
