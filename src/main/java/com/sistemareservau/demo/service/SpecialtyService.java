package com.sistemareservau.demo.service;

import com.sistemareservau.demo.dto.request.CreateSpecialtyRequest;
import com.sistemareservau.demo.dto.response.SpecialtyResponse;
import com.sistemareservau.demo.exception.ConflictException;
import com.sistemareservau.demo.model.Specialty;
import com.sistemareservau.demo.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    // POST /api/specialties
    public SpecialtyResponse createSpecialty(CreateSpecialtyRequest request) {
        // Validar que el nombre sea único (ignorando mayúsculas/minúsculas si prefieres)
        if (specialtyRepository.findByName(request.getNombre()).isPresent()) {
            throw new ConflictException("La especialidad '" + request.getNombre() + "' ya existe.");
        }

        Specialty specialty = Specialty.builder()
                .name(request.getNombre())
                .description(request.getDescripcion())
                .build();

        return mapToResponse(specialtyRepository.save(specialty));
    }

    // GET /api/specialties
    public List<SpecialtyResponse> getAllSpecialties() {
        return specialtyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SpecialtyResponse mapToResponse(Specialty specialty) {
        return SpecialtyResponse.builder()
                .id(specialty.getId())
                .nombre(specialty.getName())
                .descripcion(specialty.getDescription())
                .build();
    }
}