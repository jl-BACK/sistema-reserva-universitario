package com.sistemareservau.demo.dto.response;

import lombok.Data;

@Data
public class OfficeOccupancyResponse {
    private Long officeId;
    private String numeroConsultorio;
    private Integer totalCitasAsignadas;
}
