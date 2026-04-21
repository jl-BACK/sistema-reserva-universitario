package com.sistemareservau.demo.dto.request;

import com.sistemareservau.demo.model.OfficeStatus;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOfficeRequest {
    @Size(max = 20)
    private String numeroConsultorio;
    
    @Size(max = 255)
    private String ubicacion;
    
    private OfficeStatus status; // Para inhabilitar un consultorio por mantenimiento
}
