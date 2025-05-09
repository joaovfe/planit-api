package com.planit.api.baggage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.baggage.dtos.CreateBaggageItemDto;
import com.planit.api.models.BaggageItemModel;

@RestController
@RequestMapping("/bagagem")
public class BaggageItemController {

    @Autowired
    private BaggageItemService baggageItemService;

    @PostMapping("/novo")
    public ResponseEntity<BaggageItemModel> createBaggageItem(@RequestBody CreateBaggageItemDto baggageItem) {
        BaggageItemModel savedBaggageItem = baggageItemService.createBaggageItem(baggageItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBaggageItem);
    }

    @GetMapping
    public List<BaggageItemModel> getAllBaggageItems() {
        return baggageItemService.getAllBaggageItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaggageItemModel> getBaggageItem(@PathVariable Long id) {
        BaggageItemModel baggageItem = baggageItemService.getBaggageItem(id);
        return ResponseEntity.ok(baggageItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaggageItemModel> updateBaggageItem(@PathVariable Long id, @RequestBody CreateBaggageItemDto baggageItemDetails) {
        BaggageItemModel updatedBaggageItem = baggageItemService.updateBaggageItem(id, baggageItemDetails);
        return ResponseEntity.ok(updatedBaggageItem);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteBaggageItem(@PathVariable Long id) {
        baggageItemService.deleteBaggageItem(id);
        return ResponseEntity.noContent().build();
    }
}
