package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.Specialty;
import com.sistemareservau.demo.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public Specialty createSpecialty(String name, String description) {

        specialtyRepository.findByName(name).ifPresent(s -> {
            throw new RuntimeException("La especialidad ya existe");
        });

        Specialty specialty = Specialty.builder()
                .name(name)
                .description(description)
                .createdAt(Instant.now())
                .build();

        return specialtyRepository.save(specialty);
    }


    public Specialty getById(UUID id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
    }
}