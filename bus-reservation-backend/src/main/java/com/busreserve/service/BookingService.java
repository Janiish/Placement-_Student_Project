package com.busreserve.service;

import com.busreserve.dto.BookingRequest;
import com.busreserve.dto.BookingResponse;
import com.busreserve.entity.Booking;
import com.busreserve.entity.Bus;
import com.busreserve.repository.BookingRepository;
import com.busreserve.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Bus bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        LocalDate travelDate = LocalDate.parse(request.getTravelDate());
        Set<Integer> bookedSeats = getBookedSeatNumbers(bus.getId(), travelDate);

        for (Integer seatNo : request.getSeatNumbers()) {
            if (seatNo < 1 || seatNo > bus.getTotalSeats()) throw new RuntimeException("Invalid seat number: " + seatNo);
            if (bookedSeats.contains(seatNo)) throw new RuntimeException("Seats already booked: " + seatNo);
        }

        Booking booking = new Booking();
        booking.setPassengerName(request.getPassengerName());
        booking.setPassengerEmail(request.getPassengerEmail());
        booking.setPassengerPhone(request.getPassengerPhone());
        booking.setBus(bus);
        booking.setTravelDate(travelDate);
        booking.setTotalFare(bus.getFare().multiply(BigDecimal.valueOf(request.getSeatNumbers().size())));
        booking.setSeatNumbers(request.getSeatNumbers());
        booking.setStatus("CONFIRMED");
        booking.setBookingTime(LocalDateTime.now());

        return mapToResponse(bookingRepository.save(booking));
    }

    public BookingResponse getBooking(String id) {
        return mapToResponse(bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found")));
    }

    private Set<Integer> getBookedSeatNumbers(String busId, LocalDate date) {
        return bookingRepository.findByBusIdAndTravelDateAndStatus(busId, date, "CONFIRMED").stream()
                .filter(b -> b.getSeatNumbers() != null)
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toSet());
    }

    private BookingResponse mapToResponse(Booking b) {
        return new BookingResponse(b.getId(), b.getPassengerName(), b.getPassengerEmail(), b.getPassengerPhone(),
                b.getBus().getBusName(), b.getBus().getBusNumber(), b.getBus().getRoute().getOrigin(),
                b.getBus().getRoute().getDestination(), b.getTravelDate(), b.getSeatNumbers(),
                b.getTotalFare(), b.getStatus(), b.getBookingTime());
    }
}
