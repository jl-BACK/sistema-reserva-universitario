package com.sistemareservau.demo.service;

import com.sistemareservau.demo.model.Patient;
import com.sistemareservau.demo.model.PatientStatus;
import com.sistemareservau.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // ==================== CREAR PACIENTE ====================

    public Patient createPatient(String fullName,
                                 String docType,
                                 String docNumber,
                                 LocalDate birthDate,
                                 String phone,
                                 String email) {


        patientRepository.findByEmail(email).ifPresent(p -> {
                    throw new RuntimeException("El email ya está registrado");
                });

        //Crear paciente
        Patient patient = Patient.builder().fullName(fullName).docType(docType).docNumber(docNumber).birthDate(birthDate).phone(phone).email(email).createdAt(Instant.now()).build();

        //Guardar en BD
        return patientRepository.save(patient);
    }

    public Patient getPatientByDocNumber(String docNumber) {
    return patientRepository.findByDocNumber(docNumber)
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }


public void deactivatePatient(UUID id) {

Patient patient = patientRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

//Validación opcional: ya está inactivo
    if (patient.getStatus() == PatientStatus.INACTIVE) {
    throw new RuntimeException("El paciente ya está inactivo");
    }

//Cambiar estado
    patient.setStatus(PatientStatus.INACTIVE);

//Auditoría
    patient.setUpdatedAt(Instant.now());

//Guardar cambios
    patientRepository.save(patient);
}

public void activatePatient(UUID id){

    Patient patient = patientRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

    if(patient.getStatus() == PatientStatus.ACTIVE){
        throw new RuntimeException("El paciente ya esta activo");
    }

    patient.setStatus(PatientStatus.ACTIVE);

    patient.setUpdatedAt(Instant.now());

    patientRepository.save(patient);

}

}