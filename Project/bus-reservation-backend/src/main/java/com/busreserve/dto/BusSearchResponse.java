package com.busreserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusSearchResponse {
    private String busId;
    private String busName;
    private String busNumber;
    private String busType;
    private Integer totalSeats;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private BigDecimal fare;
    private String origin;
    private String destination;
    private Integer availableSeats;
}
