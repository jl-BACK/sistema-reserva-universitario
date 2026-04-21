package com.sistemareservau.demo.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AppointmentResponse {
    private Long id;
    private LocalDateTime fechaHora;
    private String nombrePaciente;
    private String nombreDoctor;
    private String consultorio;
    private String tipoCita;
    private String estado; 
}
