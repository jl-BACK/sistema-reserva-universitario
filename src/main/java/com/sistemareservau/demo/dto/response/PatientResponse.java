package com.sistemareservau.demo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

import com.sistemareservau.demo.model.PatientStatus;

@Data
@Builder
public class PatientResponse {
    private UUID id;
    private String documentoIdentidad;
    private String nombreCompleto; 
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private PatientStatus status;
}
