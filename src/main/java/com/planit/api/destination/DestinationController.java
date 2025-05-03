package com.planit.api.destination;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationFilterDto;
import com.planit.api.models.DestinationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destination")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<?> list(DestinationFilterDto filter) {
        Page<DestinationModel> result = destinationService.listDestinations(filter);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateDestinationDto dto) {
        destinationService.updateDestination(id, dto);
        return ResponseEntity.ok("Destino atualizado com sucesso");
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