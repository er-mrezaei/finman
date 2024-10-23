package org.example.finman.service.ministration.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.finman.domain.ministration.Ministration;
import org.example.finman.dto.ministration.MinistrationDto;
import org.example.finman.repository.ministration.MinistrationRepository;
import org.example.finman.service.ministration.MinistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinistrationServiceImpl implements MinistrationService {

    private final MinistrationRepository ministrationRepository;

    @Override
    public List<MinistrationDto> getAllMinistrations() {
        return ministrationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public MinistrationDto getMinistrationById(Long id) {
        Ministration ministration = ministrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ministration not found"));
        return convertToDto(ministration);
    }

    @Override
    public MinistrationDto createMinistration(MinistrationDto ministrationDto) {
        Ministration ministration = convertToEntity(ministrationDto);
        ministrationRepository.save(ministration);
        return convertToDto(ministration);
    }

    @Override
    public void deleteMinistration(Long id) {
        ministrationRepository.deleteById(id);
    }

    @Override
    public MinistrationDto updateMinistration(Long id, MinistrationDto ministrationDto) {
        Ministration existingMinistration = ministrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ministration not found"));
        existingMinistration.setName(ministrationDto.getName());
        existingMinistration.setCostPerUse(ministrationDto.getCostPerUse());
        existingMinistration.setMaxUsage(ministrationDto.getMaxUsage());
        existingMinistration.setActive(ministrationDto.isActive());
        ministrationRepository.save(existingMinistration);
        return convertToDto(existingMinistration);
    }

    private MinistrationDto convertToDto(Ministration ministration) {
        MinistrationDto ministrationDto = new MinistrationDto();
        ministrationDto.setId(ministration.getId());
        ministrationDto.setName(ministration.getName());
        ministrationDto.setCostPerUse(ministration.getCostPerUse());
        ministrationDto.setMaxUsage(ministration.getMaxUsage());
        ministrationDto.setActive(ministration.isActive());
        return ministrationDto;
    }

    private Ministration convertToEntity(MinistrationDto ministrationDto) {
        Ministration ministration = new Ministration();
        ministration.setName(ministrationDto.getName());
        ministration.setCostPerUse(ministrationDto.getCostPerUse());
        ministration.setMaxUsage(ministrationDto.getMaxUsage());
        ministration.setActive(ministrationDto.isActive());
        return ministration;
    }
}
