package com.busreserve.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    private String id;

    @Field("passenger_name")
    private String passengerName;

    @Field("passenger_email")
    private String passengerEmail;

    @Field("passenger_phone")
    private String passengerPhone;

    @DBRef
    private Bus bus;

    @Field("travel_date")
    private LocalDate travelDate;

    @Field("seat_numbers")
    private List<Integer> seatNumbers;

    @Field("total_fare")
    private BigDecimal totalFare;

    private String status = "CONFIRMED";

    @Field("booking_time")
    private LocalDateTime bookingTime = LocalDateTime.now();
}
