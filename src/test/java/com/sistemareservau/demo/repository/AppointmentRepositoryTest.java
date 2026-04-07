package com.sistemareservau.demo.repository;

import com.sistemareservau.demo.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase; // <--- ESTE FALTA
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection; // Recomendado
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppointmentRepositoryTest {

    @Container
    @ServiceConnection // Esto ayuda a Spring a conectarse al Docker automáticamente
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TestEntityManager em;

    private Appointment createBaseData() {
        // Usando Builder como en tu código
        Patient patient = Patient.builder()
                .fullName("Ana Gomez")
                .docNumber("1122")
                .docType("CC")
                .email("jose@test.com")
                .status(PatientStatus.ACTIVE)
                .build();
        em.persist(patient);

        Doctor doctor = Doctor.builder()
                .fullName("Dr. Juan Perez")
                .email("doctor@test.com")
                .status(DoctorStatus.ACTIVE)
                .build();
        em.persist(doctor);

        // Si Office y AppointmentType no tienen Builder, usamos constructor normal
        Office office = new Office();
        office.setStatus(OfficeStatus.AVAILABLE);
        office.setOfficeNumber("123");
        office.setLocation("colombia");
    
        // office.setName("Consultorio 101"); // Si tiene nombre, añádelo
        em.persist(office);

        AppointmentType type = new AppointmentType();
        type.setType("GENERAL");
        type.setDurationMinutes(30);
        em.persist(type);

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .office(office)
                .appointmentType(type)
                .date(LocalDate.now())
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .status(AppointmentStatus.SCHEDULED)
                .build();

        em.persist(appointment);
        em.flush();

        return appointment;
    }

    @Test
    void shouldFindByPatientAndStatus() {
        Appointment appointment = createBaseData();

        List<Appointment> result = appointmentRepository.findByPatientAndStatus(
                appointment.getPatient(),
                AppointmentStatus.SCHEDULED
        );

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldDetectDoctorOverlap() {
        Appointment appointment = createBaseData();

        boolean overlap = appointmentRepository.existsOverlappingAppointment(
                appointment.getDoctor(),
                appointment.getDate(),
                LocalTime.of(10, 15),
                LocalTime.of(10, 45)
        );

        assertThat(overlap).isTrue();
    }

    @Test
    void shouldCountAppointmentsByOffice() {
        createBaseData();

        List<Object[]> result = appointmentRepository.countAppointmentsByOfficeAndDate(LocalDate.now());

        assertThat(result).isNotEmpty();
    }
}