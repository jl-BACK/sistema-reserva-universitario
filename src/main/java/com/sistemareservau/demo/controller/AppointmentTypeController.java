package com.sistemareservau.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareservau.demo.dto.request.CreateAppointmentTypeRequest;
import com.sistemareservau.demo.dto.response.AppointmentTypeResponse;
import com.sistemareservau.demo.service.AppointmentTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointment-types")
@RequiredArgsConstructor
public class AppointmentTypeController {

    private final AppointmentTypeService appointmentTypeService;

    @PostMapping
    public ResponseEntity<AppointmentTypeResponse> create(@Valid @RequestBody CreateAppointmentTypeRequest request) {
        return new ResponseEntity<>(appointmentTypeService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentTypeResponse>> getAll() {
        return ResponseEntity.ok(appointmentTypeService.getAll());
    }
}
