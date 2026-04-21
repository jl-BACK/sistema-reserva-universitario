package com.sistemareservau.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareservau.demo.dto.request.CreateDoctorScheduleRequest;
import com.sistemareservau.demo.dto.response.DoctorScheduleResponse;
import com.sistemareservau.demo.service.DoctorScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors/{doctorId}/schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<DoctorScheduleResponse> create(
            @PathVariable UUID doctorId, 
            @Valid @RequestBody CreateDoctorScheduleRequest request) {
        return new ResponseEntity<>(scheduleService.createSchedule(doctorId, request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorScheduleResponse>> getByDoctor(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(scheduleService.getDoctorSchedules(doctorId));
    }
}
