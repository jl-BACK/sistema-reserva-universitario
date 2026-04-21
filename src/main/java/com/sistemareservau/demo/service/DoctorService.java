package com.sistemareservau.demo.service;


import com.sistemareservau.demo.dto.request.CreateDoctorRequest;
import com.sistemareservau.demo.dto.request.UpdateDoctorRequest;
import com.sistemareservau.demo.dto.response.DoctorResponse;
import com.sistemareservau.demo.exception.ConflictException;
import com.sistemareservau.demo.exception.ResourceNotFoundException;
import com.sistemareservau.demo.model.Doctor;
import com.sistemareservau.demo.model.DoctorStatus;
import com.sistemareservau.demo.model.Specialty;
import com.sistemareservau.demo.repository.DoctorRepository;
import com.sistemareservau.demo.repository.SpecialtyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository; 

    // POST /api/doctors
    public DoctorResponse createDoctor(CreateDoctorRequest request) {
        // 1. Validar si ya existe por tarjeta profesional o documento
        if (doctorRepository.existsByLicenseNumber(request.getTarjetaProfesional())) {
            throw new ConflictException("La tarjeta profesional " + request.getTarjetaProfesional() + " ya está registrada.");
        
        }

        Specialty specialty = specialtyRepository.findById(request.getEspecialidadId())
            .orElseThrow(() -> new ResourceNotFoundException("La especialidad con ID " + request.getEspecialidadId() + " no existe."));

        // 2. Mapear Request a Entidad
        Doctor doctor = Doctor.builder()
                .fullName(request.getNombreCompleto())
                .licenseNumber(request.getTarjetaProfesional())
                .email(request.getCorreo())
                .phone(request.getTelefono())
                .specialty(specialty) 
                .status(DoctorStatus.ACTIVE)
                .build();

        return mapToResponse(doctorRepository.save(doctor));
    }

    // GET /api/doctors/{id}
    public DoctorResponse getDoctorById(UUID id) {
        return doctorRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + id));
    }

    // GET /api/doctors
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // PUT /api/doctors/{id}
    public DoctorResponse updateDoctor(UUID id, UpdateDoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Médico no encontrado"));

        // Actualizamos campos permitidos
        doctor.setStatus(request.getStatus());
       
        Specialty newSpecialty = specialtyRepository.findById(request.getEspecialidadId())
           .orElseThrow(() -> new ResourceNotFoundException("Especialidad no válida"));
        doctor.setSpecialty(newSpecialty);

        return mapToResponse(doctorRepository.save(doctor));
    }

    // Mapper Privado
    private DoctorResponse mapToResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .nombreCompleto(doctor.getFullName())
                .tarjetaProfesional(doctor.getLicenseNumber())
                .nombreEspecialidad(doctor.getSpecialty().getName()) // Asumiendo que tiene especialidad
                .status(doctor.getStatus())
                .build();
    }
}