package com.sistemareservau.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOfficeRequest {
    
    @NotBlank
    private String numeroConsultorio;
    @NotBlank
    private String ubicacion; // Ej: "Piso 2, Ala Norte"
}
