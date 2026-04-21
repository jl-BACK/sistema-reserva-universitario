package com.sistemareservau.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentTypeRequest {
    
    @NotBlank(message = "El nombre del tipo de cita es obligatorio")
    private String nombre; // Ej: "Chequeo Preventivo"

    private String descripcion;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 15, message = "La duración mínima debe ser de 15 minutos")
    private Integer duracionMinutos; 
}
