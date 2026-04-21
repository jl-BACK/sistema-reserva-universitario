package com.sistemareservau.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemareservau.demo.model.Specialty;
import java.util.List;


public interface SpecialtyRepository extends JpaRepository<Specialty, UUID> {

    // Buscar especialidad por nombre
    Optional<Specialty> findByName(String name);


}