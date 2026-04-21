package com.sistemareservau.demo.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppointmentTypeResponse {
    private UUID id;
    private String nombre;
    private Integer duracionMinutos;
    private String descripcion;
    private boolean activo;
}
