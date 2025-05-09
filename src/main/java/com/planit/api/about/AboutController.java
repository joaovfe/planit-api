package com.planit.api.about;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planit.api.about.dtos.SystemListDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sobre")
@RequiredArgsConstructor
public class AboutController {

    private final AboutService aboutService;

    @GetMapping
    public ResponseEntity<List<SystemListDto>> list() {
        List<SystemListDto> system = aboutService.getSystemInformation();
        return ResponseEntity.ok(system);
    }

}
