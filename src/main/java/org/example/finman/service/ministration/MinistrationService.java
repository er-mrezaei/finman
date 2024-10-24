package org.example.finman.service.ministration;

import org.example.finman.dto.ministration.MinistrationDto;
import org.example.finman.dto.ministration.ServiceUsageReportDto;

import java.util.List;

public interface MinistrationService {
    List<MinistrationDto> getAllMinistrations();

    MinistrationDto getMinistrationById(Long id);

    MinistrationDto createMinistration(MinistrationDto ministrationDto);

    void deleteMinistration(Long id);

    MinistrationDto updateMinistration(Long id, MinistrationDto ministrationDto);

    void useService(long userId, long serviceId);

    void setMinistrationActive(long id, boolean isActive);

    List<ServiceUsageReportDto> getServiceUsageReport(long serviceId);

    List<ServiceUsageReportDto> getAllServiceUsageReports();

    List<MinistrationDto> getAllowedServicesForUser(long userId);

    List<MinistrationDto> getActiveServices();
}
