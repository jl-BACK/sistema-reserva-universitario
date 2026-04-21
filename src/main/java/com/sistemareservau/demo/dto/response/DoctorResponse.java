package com.sistemareservau.demo.dto.response;

import lombok.Data;

@Data
public class DoctorResponse {
    private Long id;
    private String nombre;
    private String tarjetaProfesional;
    private String nombreEspecialidad; 
    private Boolean activo;
}
