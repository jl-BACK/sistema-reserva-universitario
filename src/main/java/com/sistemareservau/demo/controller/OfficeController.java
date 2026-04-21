package com.sistemareservau.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareservau.demo.dto.request.CreateOfficeRequest;
import com.sistemareservau.demo.dto.request.UpdateOfficeRequest;
import com.sistemareservau.demo.dto.response.OfficeResponse;
import com.sistemareservau.demo.service.OfficeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @PostMapping
    public ResponseEntity<OfficeResponse> create(@Valid @RequestBody CreateOfficeRequest request) {
        return new ResponseEntity<>(officeService.createOffice(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OfficeResponse>> getAll() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateOfficeRequest request) {
        return ResponseEntity.ok(officeService.updateOffice(id, request));
    }
}
