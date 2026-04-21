package com.sistemareservau.demo.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoShowPatientResponse {
    private Long pacienteId;
    private String nombrePaciente;
    private Integer cantidadFaltas;
    private LocalDateTime ultimaCitaNoAtendida;
}
