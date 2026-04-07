package com.sistemareservau.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemareservau.demo.model.*;

import java.time.DayOfWeek;
import java.util.*;

public interface DoctorScheduleRepository extends JpaRepository <DoctorSchedule, UUID> {

    List<DoctorSchedule> findByDayOfWeekAndDoctor(DayOfWeek dayOfWeek, Doctor doctor);

}

//Buscar horarios de un doctor por día de la semana.