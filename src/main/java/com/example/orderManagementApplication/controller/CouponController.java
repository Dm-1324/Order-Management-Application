package com.example.orderManagementApplication.controller;

import com.example.orderManagementApplication.dto.CouponDto;
import com.example.orderManagementApplication.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponDto> createCoupon(@Valid @RequestBody CouponDto couponDto) {
        return new ResponseEntity<>(couponService.createCoupon(couponDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CouponDto>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
}