package org.example.finman.dto.ministration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUsageReportDto {
    private String username;
    private int usageCount;
    private String serviceName;
    private double remainingCredit;
}