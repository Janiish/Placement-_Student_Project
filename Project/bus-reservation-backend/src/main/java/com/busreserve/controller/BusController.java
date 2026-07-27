package com.busreserve.controller;

import com.busreserve.dto.BusSearchResponse;
import com.busreserve.dto.SeatAvailabilityResponse;
import com.busreserve.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @GetMapping("/buses/search")
    public ResponseEntity<List<BusSearchResponse>> searchBuses(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("date") String date) {
        return ResponseEntity.ok(busService.searchBuses(from, to, date));
    }

    @GetMapping("/buses/{id}/seats")
    public ResponseEntity<SeatAvailabilityResponse> getSeatAvailability(
            @PathVariable("id") String id,
            @RequestParam("date") String date) {
        return ResponseEntity.ok(busService.getSeatAvailability(id, date));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(busService.getAllCities());
    }
}
