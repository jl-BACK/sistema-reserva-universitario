package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.Office;
import com.sistemareservau.demo.model.OfficeStatus;
import com.sistemareservau.demo.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    public Office createOffice(String officeNumber, String location, OfficeStatus status) {

        officeRepository.findByOfficeNumber(officeNumber).ifPresent( o -> {
            throw new RuntimeException("El consultorio ya existe");
        });

        Office office = Office.builder()
                .officeNumber(officeNumber)
                .location(location)
                .status(OfficeStatus.AVAILABLE)
                .createdAt(Instant.now())
                .build();

        return officeRepository.save(office);
    }

    public List<Office> getActiveOffices() {
        return officeRepository.findByStatus(OfficeStatus.AVAILABLE);
    }

    public Office getById(UUID id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultorio no encontrado"));
    }
}