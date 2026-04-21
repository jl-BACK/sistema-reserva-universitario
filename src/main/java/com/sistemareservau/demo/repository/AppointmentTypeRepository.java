package com.sistemareservau.demo.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemareservau.demo.model.AppointmentType;
import java.util.List;


public interface AppointmentTypeRepository extends JpaRepository <AppointmentType, UUID> {

    // Buscar tipos de cita por nombre (búsqueda parcial)
    List<AppointmentType> findByTypeContainingIgnoreCase(String type);

    Optional <AppointmentType> findByType(String type);

    boolean existsByType(String type);
}
