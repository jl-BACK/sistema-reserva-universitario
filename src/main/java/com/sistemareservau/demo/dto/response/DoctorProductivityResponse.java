package com.sistemareservau.demo.dto.response;

import lombok.Data;

@Data
public class DoctorProductivityResponse {
    private Long doctorId;
    private String nombreDoctor;
    private Integer citasAtendidas;
    private Double horasTotalesTrabajadas;
    private Double promedioPacientesPorHora;
}

