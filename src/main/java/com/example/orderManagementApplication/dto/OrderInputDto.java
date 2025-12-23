package com.example.orderManagementApplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderInputDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Total Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal totalAmount;

    
    private Set<Long> couponIds;
}