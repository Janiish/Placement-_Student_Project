package com.busreserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private String busId;
    private String travelDate;
    private List<Integer> seatNumbers;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
}
