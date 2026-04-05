package com.sistemareservau.demo.model;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table (name = "patients")
public class Patient {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PatientStatus status;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column (name = "document_type")
    private String docType;

    @Column (name = "document_number")
    private String docNumber;

    @Column (name = "birth_date")
    private LocalDate birthDate;

    @Column (name = "phone", length = 13)
    private String phone;

    @Column (name = "email", unique = true)
    private String email;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;




    
}