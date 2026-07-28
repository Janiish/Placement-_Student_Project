package com.busreserve.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalTime;

@Document(collection = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    private String id;

    @Field("bus_name")
    private String busName;

    @Field("bus_number")
    private String busNumber;

    @Field("bus_type")
    private String busType;

    @Field("total_seats")
    private Integer totalSeats = 40;

    @DBRef
    private Route route;

    @Field("departure_time")
    private LocalTime departureTime;

    @Field("arrival_time")
    private LocalTime arrivalTime;

    private BigDecimal fare;

    @Field("operating_days")
    private String operatingDays;
}
