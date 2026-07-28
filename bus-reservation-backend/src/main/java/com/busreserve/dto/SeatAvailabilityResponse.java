package com.busreserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatAvailabilityResponse {
    private String busId;
    private String busName;
    private Integer totalSeats;
    private List<Integer> bookedSeats;
    private BigDecimal fare;
}
