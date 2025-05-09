package com.planit.api.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.country.dtos.CountryResponseDto;

@RestController
@RequestMapping("/paises")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryResponseDto>> getClimate() {
        List<CountryResponseDto> result = countryService.getAllCountry();
        return ResponseEntity.ok(result);
    }

}
