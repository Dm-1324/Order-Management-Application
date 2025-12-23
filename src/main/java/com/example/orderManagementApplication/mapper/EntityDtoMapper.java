package com.example.orderManagementApplication.mapper;

import com.example.orderManagementApplication.dto.CouponDto;
import com.example.orderManagementApplication.dto.OrderOutputDto;
import com.example.orderManagementApplication.dto.UserInputDto;
import com.example.orderManagementApplication.dto.UserOutputDto;
import com.example.orderManagementApplication.entity.Coupon;
import com.example.orderManagementApplication.entity.Order;
import com.example.orderManagementApplication.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
                .totalAmount(order.getTotalAmount())
                .userName(order.getUser() != null ? order.getUser().getName() : null)
                .appliedCoupons(mapCouponsToDtos(order.getCoupons()))
                .build();
    }

    private Set<CouponDto> mapCouponsToDtos(Set<Coupon> coupons) {
        if (coupons == null || coupons.isEmpty()) {
            return Collections.emptySet();
        }
        return coupons.stream()
                .map(this::toCouponDto)
                .collect(Collectors.toSet());
    }
}