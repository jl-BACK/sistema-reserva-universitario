package com.sistemareservau.demo.service;

import com.sistemareservau.demo.dto.request.CreateOfficeRequest;
import com.sistemareservau.demo.dto.request.UpdateOfficeRequest;
import com.sistemareservau.demo.dto.response.OfficeResponse;
import com.sistemareservau.demo.exception.ConflictException;
import com.sistemareservau.demo.exception.ResourceNotFoundException;
import com.sistemareservau.demo.model.Office;
import com.sistemareservau.demo.model.OfficeStatus;
import com.sistemareservau.demo.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    // POST /api/offices
    public OfficeResponse createOffice(CreateOfficeRequest request) {
        if (officeRepository.existsByOfficeNumber(request.getNumeroConsultorio())) {
            throw new ConflictException("El consultorio número " + request.getNumeroConsultorio() + " ya existe.");
        }

        Office office = Office.builder()
                .officeNumber(request.getNumeroConsultorio())
                .location(request.getUbicacion())
                .status(OfficeStatus.AVAILABLE)
                .build();

        return mapToResponse(officeRepository.save(office));
    }

    // GET /api/offices
    public List<OfficeResponse> getAllOffices() {
        return officeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // PUT /api/offices/{id}
    public OfficeResponse updateOffice(UUID id, UpdateOfficeRequest request) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado"));

        // Validar si el nuevo número ya lo tiene otro consultorio
        if (!office.getOfficeNumber().equals(request.getNumeroConsultorio()) && 
            officeRepository.existsByOfficeNumber(request.getNumeroConsultorio())) {
            throw new ConflictException("El número de consultorio " + request.getNumeroConsultorio() + " ya está en uso.");
        }

        office.setOfficeNumber(request.getNumeroConsultorio());
        office.setLocation(request.getUbicacion());
        office.setStatus(request.getStatus());

        return mapToResponse(officeRepository.save(office));
    }

    private OfficeResponse mapToResponse(Office office) {
        return OfficeResponse.builder()
                .id(office.getId())
                .numeroConsultorio(office.getOfficeNumber())
                .ubicacion(office.getLocation())
                .status(office.getStatus())
                .build();
    }
}