package com.sistemareservau.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemareservau.demo.model.Patient;

public interface PatientRepository extends JpaRepository <Patient, UUID> {

    // Buscar pacientes por nombre (búsqueda parcial, sin importar mayúsculas)
    List<Patient> findByFullNameContainingIgnoreCase(String fullName);

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByDocNumber(String docNumber);

    boolean existsByDocNumber (String docNumber);
}