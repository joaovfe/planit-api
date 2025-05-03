package com.planit.api.baggage;

import com.planit.api.baggage.dtos.CreateBaggageItemDto;
import com.planit.api.models.BaggageItemModel;
import com.planit.api.models.ClimateModel;
import com.planit.api.models.SeasonModel;
import com.planit.api.repositories.BaggageItemRepository;

import com.planit.api.repositories.ClimateRepository;
import com.planit.api.repositories.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaggageItemService {

    @Autowired
    private BaggageItemRepository baggageItemRepository;
    private ClimateRepository climateRepository;
    private SeasonRepository seasonRepository;

    public BaggageItemModel createBaggageItem(CreateBaggageItemDto baggageItemDto) {

        ClimateModel climate = climateRepository.findById(baggageItemDto.climatePreferenceId())
                .orElseThrow(() -> new IllegalArgumentException("Clima não encontrado"));

        SeasonModel season = seasonRepository.findById(baggageItemDto.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        BaggageItemModel baggageItem = BaggageItemModel.builder()
                .name(baggageItemDto.name())
                .category(baggageItemDto.category())
                .climatePreference(climate)
                .season(season)
                .description(baggageItemDto.description())
                .build();

        return baggageItemRepository.save(baggageItem);
    }

    public List<BaggageItemModel> getAllBaggageItems() {
        return baggageItemRepository.findAll();
    }

    public BaggageItemModel getBaggageItem(Long id) {
        return baggageItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item de bagagem não encontrado"));
    }

    public BaggageItemModel updateBaggageItem(Long id, CreateBaggageItemDto baggageItemDetails) {
        BaggageItemModel baggageItem = baggageItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item de bagagem não encontrado"));

        ClimateModel climate = climateRepository.findById(baggageItemDetails.climatePreferenceId())
                .orElseThrow(() -> new IllegalArgumentException("Clima não encontrado"));

        SeasonModel season = seasonRepository.findById(baggageItemDetails.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        baggageItem.setName(baggageItemDetails.name());
        baggageItem.setCategory(baggageItemDetails.category());
        baggageItem.setClimatePreference(climate);
        baggageItem.setSeason(season);
        baggageItem.setDescription(baggageItemDetails.description());

        return baggageItemRepository.save(baggageItem);
    }

    public void deleteBaggageItem(Long id) {
        BaggageItemModel baggageItem = baggageItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item de bagagem não encontrado"));

        baggageItemRepository.delete(baggageItem);
    }
}
