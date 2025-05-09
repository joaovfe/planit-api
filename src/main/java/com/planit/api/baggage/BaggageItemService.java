package com.planit.api.baggage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planit.api.GoogleAI.GoogleAIService;
import com.planit.api.baggage.dtos.BaggageItemDto;
import com.planit.api.baggage.dtos.CreateBaggageItemDto;
import com.planit.api.destination.dtos.DestinationRequestDto;
import com.planit.api.models.BaggageItemModel;
import com.planit.api.models.ClimateModel;
import com.planit.api.models.DestinationModel;
import com.planit.api.models.SeasonModel;
import com.planit.api.repositories.BaggageItemRepository;
import com.planit.api.repositories.ClimateRepository;
import com.planit.api.repositories.DestinationRepository;
import com.planit.api.repositories.SeasonRepository;

@Service
public class BaggageItemService {

    private final BaggageItemRepository baggageItemRepository;
    private final DestinationRepository destinationRepository;
    private final ClimateRepository climateRepository;
    private final SeasonRepository seasonRepository;
    private final GoogleAIService googleAIService;

    @Autowired
    public BaggageItemService(
        BaggageItemRepository baggageItemRepository,
        DestinationRepository destinationRepository,
        ClimateRepository climateRepository,
        SeasonRepository seasonRepository,
        GoogleAIService googleAIService
    ) {
        this.baggageItemRepository = baggageItemRepository;
        this.destinationRepository = destinationRepository;
        this.climateRepository = climateRepository;
        this.seasonRepository = seasonRepository;
        this.googleAIService = googleAIService;
    }



        public BaggageItemModel createBaggageItem(CreateBaggageItemDto baggageItemDto) {

                BaggageItemModel baggageItem = BaggageItemModel.builder()
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

                baggageItem.setDescription(baggageItemDetails.description());

                return baggageItemRepository.save(baggageItem);
        }

        public void deleteBaggageItem(Long id) {
                BaggageItemModel baggageItem = baggageItemRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Item de bagagem não encontrado"));

                baggageItemRepository.delete(baggageItem);
        }

        public BaggageItemDto suggestBaggageItems(Long destinationId) {

                DestinationModel destination = destinationRepository.findById(destinationId)
                                .orElseThrow(() -> new RuntimeException("Tipo de destino não encontrado"));

                String prompt = String.format(
                                "Você é um assistente de viagens. Um usuário está se preparando para uma viagem para %s, do tipo %s.\n"
                                                +
                                                "Com base nisso, gere uma lista de até 7 itens essenciais que ele deve levar na bagagem. "
                                                +
                                                "Seja objetivo e direto. Retorne apenas a lista, sem explicações.\n\n" +
                                                "Exemplo de formato:\n" +
                                                "- Mochila leve\n" +
                                                "- Garrafa de água reutilizável\n" +
                                                "- Jaqueta impermeável",
                                destination.getName(),
                                destination.getType());

                String suggestions = googleAIService.sendPrompt(prompt);

                return new BaggageItemDto(suggestions);
        }
}
