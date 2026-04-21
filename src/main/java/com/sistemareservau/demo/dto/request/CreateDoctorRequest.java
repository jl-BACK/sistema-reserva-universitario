package com.sistemareservau.demo.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorRequest {

    @NotBlank(message = "La tarjeta es obligatoria")
    @Size(min = 2, max = 20)
    private String tarjetaProfesional;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50)
    private String nombreCompleto;
    
    @NotBlank @Email(message = "El email es obligatorio")
    @Size(min = 2, max = 50)
    private String correo;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 2, max = 50)
    private String telefono;


    @NotNull
    @Positive
    private UUID especialidadId; // Referencia por ID
}
