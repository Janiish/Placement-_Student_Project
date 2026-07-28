package com.busreserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private String bookingId;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    private String busName;
    private String busNumber;
    private String origin;
    private String destination;
    private LocalDate travelDate;
    private List<Integer> seatNumbers;
    private BigDecimal totalFare;
    private String status;
    private LocalDateTime bookingTime;
}
