package com.sistemareservau.demo.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AvailabilitySlotResponse {
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private boolean disponible;
}
