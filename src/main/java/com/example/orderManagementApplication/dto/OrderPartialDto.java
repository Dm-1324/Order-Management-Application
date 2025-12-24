package com.example.orderManagementApplication.dto;

import com.example.orderManagementApplication.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderPartialDto {
    private Long id;
    private String orderNumber;
    private LocalDateTime date;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
}
