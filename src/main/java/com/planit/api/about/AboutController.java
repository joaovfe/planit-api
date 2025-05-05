package com.planit.api.about;

import com.planit.api.about.dtos.SystemListDto;
import com.planit.api.trips.dtos.TripListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sobre")
@RequiredArgsConstructor
public class AboutController {

    private final AboutService aboutService;

    @GetMapping
    public ResponseEntity<List<SystemListDto>> list() {
        List<SystemListDto> system = aboutService.getSystemInformation();
        return ResponseEntity.ok(system);
    }

}
