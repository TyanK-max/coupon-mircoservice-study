package com.twk.coupon.template.converter;

import com.twk.coupon.template.api.beans.CouponTemplateInfo;
import com.twk.coupon.template.api.enums.CouponType;
import com.twk.coupon.template.dao.entity.CouponTemplate;

/**
 * @author TyanK
 * @description: 根据创建的template来建立一个优惠券信息模板
 * @date 2022/11/25 19:55
 */
public class CouponTemplateConverter {
    public static CouponTemplateInfo convertCouponTemplate(CouponTemplate template){
        
        return CouponTemplateInfo.builder()
                .id(template.getId())
                .name(template.getName())
                .type(template.getCategory().getCode())
                .desc(template.getDescription())
                .available(template.getAvailable())
                .rule(template.getRule())
                .shopId(template.getShopId())
                .build();
    }
}
