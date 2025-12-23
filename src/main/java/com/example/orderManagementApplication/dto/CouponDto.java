package com.example.orderManagementApplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponDto {

    @NotNull(message = "Coupon code is required")
    private String code;

    @NotNull(message = "Discount percentage is required")
    @Positive(message = "Discount must be positive")
    private Double discountPercentage;
}