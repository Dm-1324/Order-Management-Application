package com.example.orderManagementApplication.entity;

import com.example.orderManagementApplication.entity.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order Number cannot be empty")
    @Column(unique = true)
    private String orderNumber;

    @NotNull(message = "Order Date cannot be empty")
    private LocalDateTime date;

    @NotNull(message = "Order Status cannot be empty")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull(message = "Order Amount cannot be empty")
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_coupons",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> coupons = new HashSet<>();
}