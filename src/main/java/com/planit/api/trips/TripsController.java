package com.planit.api.trips;

import com.planit.api.trips.dtos.CreateTripDto;
import com.planit.api.trips.dtos.TripListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/viagens")
@RequiredArgsConstructor
public class TripsController {

    private final TripsService tripService;

    @PostMapping("/novo")
    public ResponseEntity<?> create(@RequestBody CreateTripDto dto) {
        LocalDateTime departureDatetime = LocalDateTime.parse(dto.departureDatetime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        tripService.createTrip(dto, departureDatetime);
        return ResponseEntity.ok("Viagem criada com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<TripListDto>> list() {
        List<TripListDto> trips = tripService.listTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripListDto> getById(@PathVariable Long id) {
        TripListDto trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateTripDto dto) {
        LocalDateTime departureDatetime = LocalDateTime.parse(dto.departureDatetime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        tripService.updateTrip(id, dto, departureDatetime);
        return ResponseEntity.ok("Viagem atualizada com sucesso");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
