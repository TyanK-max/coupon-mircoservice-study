package com.twk.coupon.customer.service;

import com.twk.coupon.customer.dao.entity.Coupon;
import com.twk.coupon.template.api.beans.CouponInfo;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 16:17
 */
public class CouponConverter {
    
    public static CouponInfo convertToCoupon(Coupon coupon){
        return CouponInfo.builder()
                .id(coupon.getId())
                .templateId(coupon.getTemplateId())
                .status(coupon.getStatus().getCode())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .template(coupon.getTemplateInfo())
                .build();
    }
}
