package org.example.finman.dto.ministration;

import lombok.Data;

@Data
public class MinistrationDto {
    private Long id;
    private String name;
    private double costPerUse;
    private int maxUsage;
    private boolean isActive;
}
