package org.example.finman.service.ministration;

import org.example.finman.domain.ministration.Ministration;
import org.example.finman.dto.ministration.MinistrationDto;
import org.example.finman.repository.ministration.MinistrationRepository;
import org.example.finman.service.ministration.impl.MinistrationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinistrationServiceTest {

    @Mock
    private MinistrationRepository ministrationRepository;

    @InjectMocks
    private MinistrationServiceImpl ministrationService;

    @Test
    public void testGetAllMinistrations() {
        // Arrange
        List<Ministration> ministrations = Arrays.asList(
                new Ministration(1L, "Service1", 50, 100, true),
                new Ministration(2L, "Service2", 100, 200, false)
        );
        when(ministrationRepository.findAll()).thenReturn(ministrations);

        // Act
        List<MinistrationDto> result = ministrationService.getAllMinistrations();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Service1", result.get(0).getName());
        assertEquals("Service2", result.get(1).getName());
    }

    @Test
    public void testGetMinistrationById() {
        // Arrange
        Ministration ministration = new Ministration(1L, "Service1", 50, 100, true);
        when(ministrationRepository.findById(1L)).thenReturn(Optional.of(ministration));

        // Act
        MinistrationDto result = ministrationService.getMinistrationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Service1", result.getName());
    }

    @Test
    public void testCreateMinistration() {
        // Arrange
        MinistrationDto ministrationDto = new MinistrationDto();
        ministrationDto.setName("Service1");
        ministrationDto.setCostPerUse(50);
        ministrationDto.setMaxUsage(100);
        ministrationDto.setActive(true);

        Ministration ministration = new Ministration();
        ministration.setName("Service1");
        ministration.setCostPerUse(50);
        ministration.setMaxUsage(100);
        ministration.setActive(true);

        when(ministrationRepository.save(any(Ministration.class))).thenReturn(ministration);

        // Act
        MinistrationDto result = ministrationService.createMinistration(ministrationDto);

        // Assert
        assertNotNull(result);
        assertEquals("Service1", result.getName());
    }

    @Test
    public void testUpdateMinistration() {
        // Arrange
        Ministration existingMinistration = new Ministration(1L, "Service1", 50, 100, true);
        when(ministrationRepository.findById(1L)).thenReturn(Optional.of(existingMinistration));

        MinistrationDto ministrationDto = new MinistrationDto();
        ministrationDto.setName("UpdatedService");
        ministrationDto.setCostPerUse(150);
        ministrationDto.setMaxUsage(200);
        ministrationDto.setActive(false);

        // Act
        MinistrationDto result = ministrationService.updateMinistration(1L, ministrationDto);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedService", result.getName());
        assertEquals(150, result.getCostPerUse());
        assertEquals(200, result.getMaxUsage());
        assertFalse(result.isActive());
    }

    @Test
    public void testDeleteMinistration() {
        // Act
        ministrationService.deleteMinistration(1L);

        // Assert
        verify(ministrationRepository, times(1)).deleteById(1L);
    }
}