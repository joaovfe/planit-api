package com.planit.api.season;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.api.models.SeasonModel;
import com.planit.api.repositories.SeasonRepository;
import com.planit.api.season.dtos.SeasonResponseDto;

@Service
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    public List<SeasonResponseDto> getAllSeason() {
        List<SeasonModel> roles = seasonRepository.findAll();
    
        return roles.stream()
                .map(role -> SeasonResponseDto.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build()).toList();
    }
}
