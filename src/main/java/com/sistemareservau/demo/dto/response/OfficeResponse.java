package com.sistemareservau.demo.dto.response;
import lombok.Data;

@Data
public class OfficeResponse {
    private Long id;
    private String numeroConsultorio;
    private String ubicacion;
    private Boolean disponible;
}
