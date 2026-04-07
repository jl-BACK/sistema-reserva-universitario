package com.sistemareservau.demo.service;


import com.sistemareservau.demo.model.Doctor;
import com.sistemareservau.demo.model.DoctorStatus;
import com.sistemareservau.demo.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    // ==================== CREAR DOCTOR ====================

    public Doctor createDoctor(String fullName, String phone, String email) {

        doctorRepository.findByEmail(email).ifPresent(d -> {
            throw new RuntimeException("El email ya está registrado");
        });

        Doctor doctor = Doctor.builder()
                .fullName(fullName)
                .phone(phone)
                .email(email)
                .status(DoctorStatus.ACTIVE)
                .createdAt(Instant.now())
                .build();

        return doctorRepository.save(doctor);
    }

    // ==================== ACTIVAR DOCTOR ====================

    public void activateDoctor(UUID id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        if (doctor.getStatus() == DoctorStatus.ACTIVE) {
            throw new RuntimeException("El doctor ya está activo");
        }

        doctor.setStatus(DoctorStatus.ACTIVE);
        doctor.setUpdatedAt(Instant.now());

        doctorRepository.save(doctor);
    }

    // ==================== DESACTIVAR DOCTOR ====================

    public void deactivateDoctor(UUID id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        if (doctor.getStatus() == DoctorStatus.INACTIVE) {
            throw new RuntimeException("El doctor ya está inactivo");
        }

        doctor.setStatus(DoctorStatus.INACTIVE);
        doctor.setUpdatedAt(Instant.now());

        doctorRepository.save(doctor);
    }

    // ==================== BUSCAR POR ID ====================

    public Doctor getDoctorById(UUID id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
    }

    // ==================== LISTAR DOCTORES ACTIVOS ====================

    public List<Doctor> getActiveDoctors() {
        return doctorRepository.findByStatus(DoctorStatus.ACTIVE);
    }


    // ==================== BUSCAR POR ESPECIALIDAD ====================

    public List<Doctor> getDoctorsBySpecialty(UUID specialtyId) {
        return doctorRepository.findActiveDoctorsBySpecialty(specialtyId);
    }

}
