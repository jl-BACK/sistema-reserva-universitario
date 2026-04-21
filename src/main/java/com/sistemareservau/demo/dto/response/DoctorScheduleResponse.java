package com.sistemareservau.demo.dto.response;

import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class DoctorScheduleResponse {
    private Long id;
    private String nombreDoctor;
    private DayOfWeek diaSemana; 
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
