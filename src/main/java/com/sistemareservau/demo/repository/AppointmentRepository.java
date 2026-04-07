package com.sistemareservau.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistemareservau.demo.model.Appointment;
import com.sistemareservau.demo.model.AppointmentStatus;
import com.sistemareservau.demo.model.Doctor;
import com.sistemareservau.demo.model.Office;
import com.sistemareservau.demo.model.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>{

    List<Appointment> findByPatientAndStatus(Patient patient, AppointmentStatus status);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

    //Buscar citas de un paciente por estado.
    //Buscar citas por rango de fecha.

     // Validar traslape de citas para un doctor
    @Query("""
        SELECT COUNT(a) > 0
        FROM Appointment a
        WHERE a.doctor = :doctor
        AND a.date = :date
        AND a.startTime < :endTime
        AND a.endTime > :startTime
    """)
    boolean existsOverlappingAppointment(
            @Param("doctor") Doctor doctor,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    //Validar traslape de citas para un consultorio
    @Query("""
        SELECT COUNT(a) > 0
        FROM Appointment a
        WHERE a.office = :office
        AND a.date = :date
        AND a.startTime < :endTime
        AND a.endTime > :startTime
    """)
    boolean existsOverlappingAppointmentByOffice(
            @Param("office") Office office,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // Obtener citas de un doctor en una fecha (base para calcular slots disponibles)
   
    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.doctor = :doctor
        AND a.date = :date
    """)
    List<Appointment> findAppointmentsByDoctorAndDate(
            @Param("doctor") Doctor doctor,
            @Param("date") LocalDate date
    );

    //Ocupación de consultorios por día
    @Query("""
        SELECT a.office.id, COUNT(a)
        FROM Appointment a
        WHERE a.date = :date
        GROUP BY a.office.id
    """)
    List<Object[]> countAppointmentsByOfficeAndDate(@Param("date") LocalDate date);

    // 9. Contar citas canceladas y no asistidas por especialidad
    @Query("""
        SELECT ds.specialty.id, a.status, COUNT(a)
        FROM Appointment a
        JOIN a.doctor d
        JOIN d.doctorSpecialties ds
        WHERE a.status IN ('CANCELLED', 'NO_SHOW')
        GROUP BY ds.specialty.id, a.status
    """)
    List<Object[]> countCancelledAndNoShowBySpecialty();

    // 10. Ranking de doctores por citas completadas
    @Query("""
        SELECT a.doctor.id, COUNT(a) as total
        FROM Appointment a
        WHERE a.status = 'COMPLETED'
        GROUP BY a.doctor.id
        ORDER BY total DESC
    """)
    List<Object[]> findTopDoctorsByCompletedAppointments();

    // 11. Pacientes con más inasistencias (NO_SHOW)
    @Query("""
        SELECT a.patient.id, COUNT(a) as total
        FROM Appointment a
        WHERE a.status = 'NO_SHOW'
        AND a.date BETWEEN :startDate AND :endDate
        GROUP BY a.patient.id
        ORDER BY total DESC
    """)
    List<Object[]> findTopPatientsWithNoShow(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
    SELECT COUNT(a) > 0
    FROM Appointment a
    WHERE a.patient = :patient
    AND a.date = :date
    AND a.startTime < :endTime
    AND a.endTime > :startTime
""")
boolean existsOverlappingAppointmentByPatient(
        @Param("patient") Patient patient,
        @Param("date") LocalDate date,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime
);

}