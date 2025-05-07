package com.planit.api.trips;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.models.TripModel;
import com.planit.api.trips.dtos.CreateTripDto;
import com.planit.api.trips.dtos.TripListDto;

import lombok.RequiredArgsConstructor;

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
    public Map<String, Object> list(
        @RequestParam(required = false, defaultValue = "") String search,
        @RequestParam(required = false, defaultValue = "10") int take,
        @RequestParam(required = false, defaultValue = "0") int skip) {
       List<TripModel> trips = tripService.listTrips(search, take, skip);
        long total = trips.size();

        int pages = (int) Math.ceil((double) total / take);

        System.out.println(trips);

        Map<String, Object> response = new HashMap<>();
        response.put("data", trips);
        response.put("total", total);
        response.put("pages", pages);

        return response;
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
