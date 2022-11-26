package com.twk.coupon.customer.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author TyanK
 * @description: get coupon
 * @date 2022/11/26 15:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCoupon {
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long couponTemplateId;
}
