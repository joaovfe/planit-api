package com.planit.api.climate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.api.climate.dtos.ClimateResponseDto;
import com.planit.api.models.ClimateModel;
import com.planit.api.repositories.ClimateRepository;

@Service
public class ClimateService {

    @Autowired
    private ClimateRepository climateRepository;

    public List<ClimateResponseDto> getAllClimate() {
        List<ClimateModel> climates = climateRepository.findAll();
    
        return climates.stream()
                .map(climate -> ClimateResponseDto.builder()
                        .id(climate.getId())
                        .name(climate.getName())
                        .build()).toList();
    }
}
