package com.sistemareservau.demo.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import lombok.Builder;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String codigo;
    private String mensaje;
    private LocalDateTime timestamp;
    private Map<String, String> detalles; //para mostrar campos fallaron en la validación
}

