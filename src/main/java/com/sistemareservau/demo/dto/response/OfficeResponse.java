package com.sistemareservau.demo.dto.response;
import java.util.UUID;

import com.sistemareservau.demo.model.OfficeStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OfficeResponse {
    private UUID id;
    private String numeroConsultorio;
    private String ubicacion;
    private OfficeStatus status;
}
