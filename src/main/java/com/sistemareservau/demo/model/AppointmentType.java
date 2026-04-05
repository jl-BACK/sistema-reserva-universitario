package com.sistemareservau.demo.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "appointment_types")
public class AppointmentType {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    private UUID id;

    @Column( name = "name", nullable = false)
    private String name;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @ToString.Exclude
    @OneToMany 
    (mappedBy = "appointmentType")
    private List<Appointment> appointments;





}