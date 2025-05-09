package com.planit.api.about;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.planit.api.about.dtos.SystemListDto;
import com.planit.api.models.SystemModel;
import com.planit.api.repositories.SystemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AboutService {

    private final SystemRepository systemRepository;

    public List<SystemListDto> getSystemInformation() {
        List<SystemModel> system = systemRepository.findAll();
        return system.stream().map(sys -> new SystemListDto(
                sys.getProject_name(),
                sys.getIntegrate_name()
        )).collect(Collectors.toList());
    }
}
