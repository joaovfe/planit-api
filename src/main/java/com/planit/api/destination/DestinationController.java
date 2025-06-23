package com.planit.api.destination;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationDto;
import com.planit.api.models.DestinationModel;

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

    
}