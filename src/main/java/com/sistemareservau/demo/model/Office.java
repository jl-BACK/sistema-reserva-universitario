package com.sistemareservau.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "offices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Office {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OfficeStatus status;

    @Column(name = "office_number", nullable = false, unique = true)
    private String officeNumber;

    @Column(name = "location", length = 100)
    private String location;

    @ToString.Exclude
    @OneToMany(mappedBy = "office")
    private List<Appointment> appointments;

}