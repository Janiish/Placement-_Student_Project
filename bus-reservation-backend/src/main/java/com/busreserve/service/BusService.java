package com.busreserve.service;

import com.busreserve.dto.BusSearchResponse;
import com.busreserve.dto.SeatAvailabilityResponse;
import com.busreserve.entity.Booking;
import com.busreserve.entity.Bus;
import com.busreserve.entity.Route;
import com.busreserve.repository.BookingRepository;
import com.busreserve.repository.BusRepository;
import com.busreserve.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;
    private final BookingRepository bookingRepository;
    private final RouteRepository routeRepository;

    public List<BusSearchResponse> searchBuses(String from, String to, String dateStr) {
        LocalDate travelDate = LocalDate.parse(dateStr);
        String day = travelDate.getDayOfWeek().name().substring(0, 3);

        List<Route> routes = routeRepository.findByOriginIgnoreCaseAndDestinationIgnoreCase(from, to);
        if (routes.isEmpty()) return Collections.emptyList();

        return busRepository.findByRoute(routes.get(0)).stream()
                .filter(bus -> bus.getOperatingDays() != null && bus.getOperatingDays().contains(day))
                .map(bus -> {
                    int booked = countBookedSeats(bus.getId(), travelDate);
                    return new BusSearchResponse(bus.getId(), bus.getBusName(), bus.getBusNumber(), bus.getBusType(),
                            bus.getTotalSeats(), bus.getDepartureTime(), bus.getArrivalTime(), bus.getFare(),
                            bus.getRoute().getOrigin(), bus.getRoute().getDestination(), bus.getTotalSeats() - booked);
                }).collect(Collectors.toList());
    }

    public SeatAvailabilityResponse getSeatAvailability(String busId, String dateStr) {
        LocalDate travelDate = LocalDate.parse(dateStr);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + busId));

        List<Integer> bookedSeats = bookingRepository.findByBusIdAndTravelDateAndStatus(busId, travelDate, "CONFIRMED").stream()
                .filter(b -> b.getSeatNumbers() != null)
                .flatMap(b -> b.getSeatNumbers().stream())
                .distinct().collect(Collectors.toList());

        return new SeatAvailabilityResponse(bus.getId(), bus.getBusName(), bus.getTotalSeats(), bookedSeats, bus.getFare());
    }

    public List<String> getAllCities() {
        return routeRepository.findAll().stream()
                .flatMap(r -> List.of(r.getOrigin(), r.getDestination()).stream())
                .distinct().sorted().collect(Collectors.toList());
    }

    private int countBookedSeats(String busId, LocalDate date) {
        return bookingRepository.findByBusIdAndTravelDateAndStatus(busId, date, "CONFIRMED").stream()
                .filter(b -> b.getSeatNumbers() != null)
                .mapToInt(b -> b.getSeatNumbers().size()).sum();
    }
}
