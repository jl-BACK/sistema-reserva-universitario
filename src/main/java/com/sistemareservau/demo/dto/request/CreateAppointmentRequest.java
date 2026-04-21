package com.sistemareservau.demo.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {
    @NotNull
    private Long pacienteId;
    
    @NotNull
    private Long doctorId;
    
    @NotNull
    private Long consultorioId;
    
    @NotNull
    private LocalDateTime fechaHora;
    
    @NotNull
    private Long tipoCitaId; // Referencia a AppointmentType
}
