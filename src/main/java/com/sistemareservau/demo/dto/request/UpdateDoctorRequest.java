package com.sistemareservau.demo.dto.request;

import java.util.UUID;

import com.sistemareservau.demo.model.DoctorStatus;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDoctorRequest {
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    private UUID especialidadId; // Por si se cambio de especialidad o se asignó una nueva
    
    private DoctorStatus status; //para dar de baja sin borrar el registro
}
