package com.sistemareservau.demo.dto.response;

import java.util.UUID;

import com.sistemareservau.demo.model.DoctorStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorResponse {
    private UUID id;
    private String nombreCompleto;
    private String tarjetaProfesional;
    private String nombreEspecialidad; 
    private DoctorStatus status;
}
