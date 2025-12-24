package com.example.orderManagementApplication.mapper;

import com.example.orderManagementApplication.dto.*;
import com.example.orderManagementApplication.entity.Coupon;
import com.example.orderManagementApplication.entity.Order;
import com.example.orderManagementApplication.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {


    public User toUserEntity(UserInputDto dto) {
        if (dto == null) return null;
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .userStatus(dto.getUserStatus())
                .build();
    }

    public UserOutputDto toUserOutputDto(User user) {
        if (user == null) return null;
        return UserOutputDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userStatus(user.getUserStatus())
                .build();
    }


    public Coupon toCouponEntity(CouponDto dto) {
        if (dto == null) return null;
        return Coupon.builder()
                .code(dto.getCode())
                .discountPercentage(dto.getDiscountPercentage())
                .build();
    }

    public CouponDto toCouponDto(Coupon entity) {
        if (entity == null) return null;
        return CouponDto.builder()
                .code(entity.getCode())
                .discountPercentage(entity.getDiscountPercentage())
                .build();
    }


    public OrderOutputDto toOrderOutputDto(Order order) {
        if (order == null) return null;

        return OrderOutputDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .date(order.getDate())
                .orderStatus(order.getOrderStatus())
                .originalTotal(order.getOriginalTotal())
                .totalAmount(order.getTotalAmount())
                .userName(order.getUser() != null ? order.getUser().getName() : null)
                .appliedCoupons(mapCouponsToDto(order.getCoupons()))
                .build();
    }

    private Set<CouponDto> mapCouponsToDto(Set<Coupon> coupons) {
        if (coupons == null || coupons.isEmpty()) {
            return Collections.emptySet();
        }
        return coupons.stream()
                .map(this::toCouponDto)
                .collect(Collectors.toSet());
    }

    private OrderPartialDto toOrderPartialDto(Order order) {
        return OrderPartialDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .date(order.getDate())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    public UserOrderDto toUserOrderDto(User user) {
        if (user == null) return null;

        List<OrderPartialDto> listOrderPartialDto;
        if (user.getOrders() != null) {
            listOrderPartialDto = user.getOrders().stream()
                    .map(this::toOrderPartialDto)
                    .toList();
        } else {
            listOrderPartialDto = Collections.emptyList();
        }

        return UserOrderDto.builder()
                .id(user.getId())
                .name(user.getName())
                .userStatus(user.getUserStatus())
                .userOrderData(listOrderPartialDto)
                .build();
    }
}