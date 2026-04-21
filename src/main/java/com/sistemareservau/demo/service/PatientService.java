package com.sistemareservau.demo.service;

import com.sistemareservau.demo.dto.request.CreatePatientRequest;
import com.sistemareservau.demo.dto.request.UpdatePatientRequest;
import com.sistemareservau.demo.dto.response.PatientResponse;
import com.sistemareservau.demo.exception.ConflictException;
import com.sistemareservau.demo.exception.ResourceNotFoundException;
import com.sistemareservau.demo.model.Patient;
import com.sistemareservau.demo.model.PatientStatus;
import com.sistemareservau.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // POST /api/patients -> Usa CreatePatientRequest
    public PatientResponse createPatient(CreatePatientRequest request) {
        // Validación: No permitir documentos duplicados
        if (patientRepository.existsByDocNumber(request.getDocumentoIdentidad())) {
            throw new ConflictException("El paciente con documento " + request.getDocumentoIdentidad() + " ya está registrado.");
        }

        Patient patient = Patient.builder()
                .fullName(request.getNombreCompleto())
                .docNumber(request.getDocumentoIdentidad())
                .birthDate(request.getFechaNacimiento())
                .phone(request.getTelefono())
                .email(request.getEmail())
                .status(PatientStatus.ACTIVE) // Todo paciente nuevo inicia activo
                .build();

        return mapToResponse(patientRepository.save(patient));
    }

    // GET /api/patients/{id}
    public PatientResponse getPatientById(UUID id) {
        return patientRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con el ID: " + id));
    }

    // GET /api/patients
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // PUT /api/patients/{id} -> Usa UpdatePatientRequest
    public PatientResponse updatePatient(UUID id, UpdatePatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar: Paciente no encontrado"));

        // Actualizamos solo los campos permitidos en el UpdateRequest
        patient.setFullName(request.getNombreCompleto());
        patient.setPhone(request.getTelefono());
        patient.setEmail(request.getCorreo());
        // El docNumber y docType normalmente no se cambian en un Update simple por seguridad

        return mapToResponse(patientRepository.save(patient));
    }

    // Método de búsqueda por documento (el que tenías antes)
    public PatientResponse getPatientByDocNumber(String docNumber) {
        return patientRepository.findByDocNumber(docNumber)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("No existe paciente con documento: " + docNumber));
    }

    // MAPPERS (Para no repetir código)
    private PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .nombreCompleto(patient.getFullName())
                .documentoIdentidad(patient.getDocNumber())
                .correo(patient.getEmail())
                .status(patient.getStatus())
                .build();
    }
}
