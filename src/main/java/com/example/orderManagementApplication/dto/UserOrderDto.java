package com.example.orderManagementApplication.dto;

import com.example.orderManagementApplication.entity.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserOrderDto {
    private Long id;

    private String name;

    private UserStatus userStatus;

    private List<OrderPartialDto> userOrderData;
}
