package com.planit.api.season;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.season.dtos.SeasonResponseDto;

@RestController
@RequestMapping("/estacoes")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @GetMapping
    public ResponseEntity<List<SeasonResponseDto>> getRoles() {
        List<SeasonResponseDto> result = seasonService.getAllSeason();
        return ResponseEntity.ok(result);
    }

}
