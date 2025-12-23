package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.CouponDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {
    CouponDto createCoupon(CouponDto couponDto);

    List<CouponDto> getAllCoupons();
}
