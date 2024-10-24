package org.example.finman.service.ministration.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.finman.domain.ministration.Ministration;
import org.example.finman.domain.ministration.Permission;
import org.example.finman.domain.user.SimpleUser;
import org.example.finman.dto.ministration.MinistrationDto;
import org.example.finman.dto.ministration.ServiceUsageReportDto;
import org.example.finman.repository.ministration.MinistrationRepository;
import org.example.finman.repository.ministration.PermissionRepository;
import org.example.finman.repository.user.UserRepository;
import org.example.finman.service.ministration.MinistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MinistrationServiceImpl implements MinistrationService {
    private final MinistrationRepository ministrationRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MinistrationDto> getAllMinistrations() {
        return ministrationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    public void useService(long userId, long serviceId) {
        Permission permission = permissionRepository.readByUserIdAndMinistrationId(userId, serviceId);
        Ministration service = permission.getMinistration();
        SimpleUser user = permission.getUser();
        if (user.getCredit() < service.getCostPerUse()) {
            throw new IllegalStateException("Insufficient credit");
        }

        user.setCredit(user.getCredit() - service.getCostPerUse());
        permission.setUsageCount(permission.getUsageCount() + 1);
        userRepository.save(user);
        permissionRepository.save(permission);
    }

    @Override
    public void setMinistrationActive(long id, boolean isActive) {
        Ministration ministration = ministrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ministration not found"));
        ministration.setActive(isActive);
        ministrationRepository.save(ministration);
    }

    @Override
    public List<ServiceUsageReportDto> getServiceUsageReport(long serviceId) {
        Ministration service = ministrationRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        List<Permission> permissions = permissionRepository.findByMinistrationId(serviceId);

        return permissions.stream()
                .map(permission -> new ServiceUsageReportDto(
                        permission.getUser().getUsername(),
                        permission.getUsageCount(),
                        service.getName(),
                        permission.getUser().getCredit()
                )).toList();
    }

    @Override
    public List<ServiceUsageReportDto> getAllServiceUsageReports() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream()
                .map(permission -> new ServiceUsageReportDto(
                        permission.getUser().getUsername(),
                        permission.getUsageCount(),
                        permission.getMinistration().getName(),
                        permission.getUser().getCredit()
                )).toList();
    }

    @Override
    public List<MinistrationDto> getAllowedServicesForUser(long userId) {
        List<Permission> permissions = permissionRepository.findByUserIdAndGrantedTrue(userId);

        return permissions.stream()
                .map(permission -> convertToDto(permission.getMinistration()))
                .toList();
    }

    @Override
    public List<MinistrationDto> getActiveServices() {
        List<Ministration> activeServices = ministrationRepository.findByIsActiveTrue();
        return activeServices.stream()
                .map(this::convertToDto)
                .toList();
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
