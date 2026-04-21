package com.sistemareservau.demo.dto.response;

import lombok.Data;

@Data
public class AppointmentTypeResponse {
    private Long id;
    private String nombre;
    private Integer duracionMinutos;
    private String descripcion;
}
