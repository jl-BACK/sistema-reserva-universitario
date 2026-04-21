package com.sistemareservau.demo.dto.request;

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
public class CancelAppointmentRequest {
    @NotNull
    private Long citaId;
    
    @NotBlank(message = "El motivo es obligatorio")
    private String motivoCancelacion;
}
