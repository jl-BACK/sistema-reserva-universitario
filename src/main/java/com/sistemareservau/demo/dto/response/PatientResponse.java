package com.sistemareservau.demo.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientResponse {
    private Long id;
    private String documentoIdentidad;
    private String nombreCompleto; 
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
}
