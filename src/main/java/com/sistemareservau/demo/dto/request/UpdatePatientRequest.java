package com.sistemareservau.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientRequest {
@Size(min = 2, max = 50)
    private String nombre;

    @Size(min = 2, max = 50)
    private String apellido;

    @Email
    private String correo;

    private String telefono;
}
