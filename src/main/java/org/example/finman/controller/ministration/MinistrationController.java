package org.example.finman.controller.ministration;

import lombok.RequiredArgsConstructor;
import org.example.finman.dto.ministration.MinistrationDto;
import org.example.finman.service.ministration.MinistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ministrations")
@RequiredArgsConstructor
public class MinistrationController {

    private final MinistrationService ministrationService;

    @GetMapping
    public List<MinistrationDto> getAllMinistrations() {
        return ministrationService.getAllMinistrations();
    }

    @GetMapping("/{id}")
    public MinistrationDto getMinistrationById(@PathVariable Long id) {
        return ministrationService.getMinistrationById(id);
    }

    @PostMapping
    public MinistrationDto createMinistration(@RequestBody MinistrationDto ministrationDto) {
        return ministrationService.createMinistration(ministrationDto);
    }

    @PutMapping("/{id}")
    public MinistrationDto updateMinistration(@PathVariable Long id, @RequestBody MinistrationDto ministrationDto) {
        return ministrationService.updateMinistration(id, ministrationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMinistration(@PathVariable Long id) {
        ministrationService.deleteMinistration(id);
        return ResponseEntity.noContent().build();
    }
}
