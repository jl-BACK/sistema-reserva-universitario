package com.sistemareservau.demo.dto.request;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorScheduleRequest {
    @NotNull
    private Long doctorId;
    
    @NotNull
    private DayOfWeek diaSemana;
    
    @NotNull
    private LocalTime horaInicio;
    
    @NotNull
    private LocalTime horaFin;
}
