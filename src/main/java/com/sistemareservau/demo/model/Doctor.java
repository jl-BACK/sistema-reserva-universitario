package com.sistemareservau.demo.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table (name = "doctors")
public class Doctor {


@Id
@GeneratedValue
@EqualsAndHashCode.Include    
private UUID id;

@Column(nullable = false, unique = true) 
    private String licenseNumber;

@Column(name = "full_name", nullable = false)
private String fullName;

@Enumerated(EnumType.STRING)
@Column (name = "status", nullable = false)
private DoctorStatus status;

@Column(name = "phone", length = 13)
private String phone;

@Column(name = "email", unique = true)
private String email;


@Column(name = "created_at", updatable = false)
private Instant createdAt;

@Column(name = "updated_at")
private Instant updatedAt;



@ToString.Exclude
@OneToMany (mappedBy = "doctor")
private List<Appointment> appointments;

@ToString.Exclude
@OneToMany (mappedBy = "doctor")
private List<DoctorSchedule> schedules;

@ManyToOne
@JoinColumn(name = "specialty_id")
private Specialty specialty;

    
}
