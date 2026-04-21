package com.sistemareservau.demo.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemareservau.demo.model.*;


public interface OfficeRepository extends JpaRepository<Office, UUID> {

    // Buscar consultorios activos
    List<Office> findByStatus(OfficeStatus status);

    Optional<Office> findByOfficeNumber(String officeNumber);

    boolean existsByOfficeNumber(String officeNumber);
}
