package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.CouponDto;
import com.example.orderManagementApplication.entity.Coupon;
import com.example.orderManagementApplication.mapper.EntityDtoMapper;
import com.example.orderManagementApplication.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final EntityDtoMapper entityDtoMapper;

    public CouponServiceImpl(CouponRepository couponRepository, EntityDtoMapper mapper, EntityDtoMapper entityDtoMapper) {
        this.couponRepository = couponRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public CouponDto createCoupon(CouponDto couponDto) {
        Coupon coupon = entityDtoMapper.toCouponEntity(couponDto);
        Coupon savedCoupon = couponRepository.save(coupon);
        return entityDtoMapper.toCouponDto(savedCoupon);
    }

    @Override
    public List<CouponDto> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(entityDtoMapper::toCouponDto)
                .toList();
    }
}
