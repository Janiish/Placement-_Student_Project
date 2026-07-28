package com.busreserve.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    private String id;

    private String origin;

    private String destination;

    @Field("distance_km")
    private Integer distanceKm;
}
