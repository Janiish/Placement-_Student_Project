package com.busreserve.repository;

import com.busreserve.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByBusIdAndTravelDateAndStatus(String busId, LocalDate travelDate, String status);
}
