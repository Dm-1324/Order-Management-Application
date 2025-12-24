package com.example.orderManagementApplication.dto;

import com.example.orderManagementApplication.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class OrderOutputDto {
    private Long id;
    private String orderNumber;
    private LocalDateTime date;
    private OrderStatus orderStatus;
    private BigDecimal originalTotal;
    private BigDecimal totalAmount;

    private String userName;
    private Set<CouponDto> appliedCoupons;
}