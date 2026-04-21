package com.sistemareservau.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistemareservau.demo.model.Doctor;
import com.sistemareservau.demo.model.DoctorStatus;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>{

    
    List<Doctor> findByDoctorSpecialties_Specialty_IdAndStatus(UUID specialtyId, DoctorStatus status);

    Optional<Doctor> findByEmail(String email);
    //Buscar doctores activos por especialidad.

    List<Doctor> findByStatus(DoctorStatus status);

    List<Doctor> findByFullNameContainingIgnoreCase(String fullName);

    // Para validar antes de crear
    boolean existsByLicenseNumber(String licenseNumber);
    
    // Para las búsquedas
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

// JPQL
@Query("""
    SELECT d
    FROM Doctor d
    JOIN d.doctorSpecialties ds
    WHERE ds.specialty.id = :specialtyId
    AND d.status = 'ACTIVE'
""")
List<Doctor> findActiveDoctorsBySpecialty(@Param("specialtyId") UUID specialtyId);
}
