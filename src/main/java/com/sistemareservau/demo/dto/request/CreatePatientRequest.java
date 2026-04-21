package com.sistemareservau.demo.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientRequest {
@NotBlank @Size(min = 7, max = 15)
    private String documentoIdentidad;

    @NotBlank @Size(min = 2, max = 50)
    private String nombreCompleto;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String telefono;
    
    private LocalDate fechaNacimiento;
}
