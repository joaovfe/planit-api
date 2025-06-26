package com.planit.api.destination;

import java.util.List;

import com.planit.api.destination.dtos.DestinationRankingDto;
import com.planit.api.enums.EVisualizationType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationDto;
import com.planit.api.models.DestinationModel;
import com.planit.api.security.userdetailimp.UserDetailImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/destino")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<List<DestinationDto>> list() {
        List<DestinationDto> result = destinationService.listDestinations();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateDestinationDto dto) {
        destinationService.updateDestination(id, dto);
        return ResponseEntity.ok("Destino atualizado com sucesso");
    }


    @GetMapping("/{id}")
    public ResponseEntity<DestinationModel> getDestination(@PathVariable Long id) {
        DestinationModel destination = destinationService.getDestinationAndIncrementView(id);
        return ResponseEntity.ok(destination);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<DestinationRankingDto>> getDestinationsByRanking(
            @RequestParam(value = "visualizationType", required = false) EVisualizationType visualizationType) {
        List<DestinationRankingDto> destinations = destinationService.getDestinationsByRanking(visualizationType);
        return ResponseEntity.ok(destinations);
    }

    @PostMapping("/novo")
    public ResponseEntity<?> create(@RequestBody CreateDestinationDto dto) {
        destinationService.createDestination(dto);
        return ResponseEntity.ok("Destino criado com sucesso");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorite/{id}")
    public ResponseEntity<String> favorite(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailImpl userDetails) {

        String result = destinationService.favorite(id, userDetails.getUser());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/favorite/{id}")
    public ResponseEntity<String> unfavorite(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetailImpl userDetails) {

        String result = destinationService.unfavorite(id, userDetails.getUser());
        return ResponseEntity.ok(result);
    }


    
}