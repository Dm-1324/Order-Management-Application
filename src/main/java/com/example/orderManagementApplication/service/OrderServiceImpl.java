package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.OrderInputDto;
import com.example.orderManagementApplication.dto.OrderOutputDto;
import com.example.orderManagementApplication.entity.Coupon;
import com.example.orderManagementApplication.entity.Order;
import com.example.orderManagementApplication.entity.User;
import com.example.orderManagementApplication.entity.enums.OrderStatus;
import com.example.orderManagementApplication.entity.enums.UserStatus;
import com.example.orderManagementApplication.exception.InvalidStatusChangeException;
import com.example.orderManagementApplication.exception.ResourceNotFoundException;
import com.example.orderManagementApplication.mapper.EntityDtoMapper;
import com.example.orderManagementApplication.repository.CouponRepository;
import com.example.orderManagementApplication.repository.OrderRepository;
import com.example.orderManagementApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final EntityDtoMapper entityDtoMapper;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, CouponRepository couponRepository, EntityDtoMapper entityDtoMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.couponRepository = couponRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public OrderOutputDto placeOrder(OrderInputDto inputDto) {
        User user = userRepository.findById(inputDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + inputDto.getUserId()));

        BigDecimal originalAmount = inputDto.getTotalAmount();
        BigDecimal finalAmount = originalAmount;

        Set<Coupon> coupons = new HashSet<>();
        if (inputDto.getCouponIds() != null && !inputDto.getCouponIds().isEmpty()) {
            coupons = new HashSet<>(couponRepository.findAllById(inputDto.getCouponIds()));
        }

        for (Coupon coupon : coupons) {
            BigDecimal discount = finalAmount.multiply(BigDecimal.valueOf(coupon.getDiscountPercentage() / 100));
            finalAmount = finalAmount.subtract(discount);
        }

        Order order = Order.builder()
                .orderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .date(LocalDateTime.now())
                .orderStatus(OrderStatus.CREATED)
                .originalTotal(originalAmount)
                .totalAmount(finalAmount)
                .user(user)
                .coupons(coupons)
                .build();

        Order savedOrder = orderRepository.save(order);

        return entityDtoMapper.toOrderOutputDto(savedOrder);
    }

    @Override
    public OrderOutputDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        return entityDtoMapper.toOrderOutputDto(order);
    }

    @Override
    public List<OrderOutputDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return user.getOrders().stream()
                .map(entityDtoMapper::toOrderOutputDto)
                .toList();

    }

    @Override
    public OrderOutputDto updateOrderStatus(Long id, OrderStatus newOrderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = order.getUser();

        if (user.getUserStatus() == UserStatus.INACTIVE) {
            throw new InvalidStatusChangeException("Status cannot be change, the user is inactive");
        }
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new InvalidStatusChangeException("Cannot change order status of a cancelled order.");
        }
        if (order.getOrderStatus() == newOrderStatus) {
            return entityDtoMapper.toOrderOutputDto(order);
        }
        if (newOrderStatus == OrderStatus.CONFIRMED && order.getOrderStatus() != OrderStatus.PENDING) {
            throw new InvalidStatusChangeException("Only pending order can be changed to Confirmed");
        }

        order.setOrderStatus(newOrderStatus);
        Order updatedOrder = orderRepository.save(order);
        return entityDtoMapper.toOrderOutputDto(updatedOrder);
    }
}
