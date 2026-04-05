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

@Column(name = "full_name", nullable = false)
private String fullName;


@Column(name = "phone", length = 13)
private String phone;

@Column(name = "email", unique = true)
private String email;

@ToString.Exclude
@OneToMany (mappedBy = "doctor")
private List<Appointment> appointments;

@ToString.Exclude
@OneToMany (mappedBy = "doctor")
private List<DoctorSchedule> schedules;


    
}
