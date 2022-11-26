package com.twk.coupon.customer.dao;

import com.twk.coupon.customer.dao.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 16:08
 */

public interface CouponDao extends JpaRepository<Coupon,Long> {
    
    long countByUserIdAndTemplateId(Long userId,Long templateId);
}
