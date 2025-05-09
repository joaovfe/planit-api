package com.planit.api.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.api.country.dtos.CountryResponseDto;
import com.planit.api.models.CountryModel;
import com.planit.api.repositories.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryResponseDto> getAllCountry() {
        List<CountryModel> countries = countryRepository.findAll();
    
        return countries.stream()
                .map(country -> CountryResponseDto.builder()
                        .id(country.getId())
                        .name(country.getName())
                        .code(country.getCode())
                        .build()).toList();
    }
}
