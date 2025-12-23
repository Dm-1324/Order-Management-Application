package com.example.orderManagementApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "coupons")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Coupon code cannot be empty")
    @Column(unique = true)
    private String code;

    @NotNull(message = "Discount percentage cannot be empty")
    private Double discountPercentage;
    
    @ManyToMany(mappedBy = "coupons")
    private Set<Order> orders;
}