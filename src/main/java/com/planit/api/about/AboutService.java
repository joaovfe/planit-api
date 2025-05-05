package com.planit.api.about;

import com.planit.api.about.dtos.SystemListDto;
import com.planit.api.models.DestinationModel;
import com.planit.api.models.SystemModel;
import com.planit.api.models.TripModel;
import com.planit.api.repositories.SystemRepository;
import com.planit.api.trips.dtos.TripListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
