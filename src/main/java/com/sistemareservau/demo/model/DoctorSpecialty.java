package com.sistemareservau.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "doctor_specialties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DoctorSpecialty {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @ToString.Exclude
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    @ToString.Exclude
    private Specialty specialty;


}