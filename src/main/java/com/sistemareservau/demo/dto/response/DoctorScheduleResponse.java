package com.sistemareservau.demo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Data
public class DoctorScheduleResponse {
    private UUID id;
    private String nombreDoctor;
    private DayOfWeek diaSemana; 
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean activo;
}
