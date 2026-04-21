package com.sistemareservau.demo.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SpecialtyResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
}
