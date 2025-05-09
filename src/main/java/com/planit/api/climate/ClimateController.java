package com.planit.api.climate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.climate.dtos.ClimateResponseDto;

@RestController
@RequestMapping("/climate")
public class ClimateController {

    @Autowired
    private ClimateService climateService;

    @GetMapping
    public ResponseEntity<List<ClimateResponseDto>> getClimate() {
        List<ClimateResponseDto> result = climateService.getAllClimate();
        return ResponseEntity.ok(result);
    }

}
