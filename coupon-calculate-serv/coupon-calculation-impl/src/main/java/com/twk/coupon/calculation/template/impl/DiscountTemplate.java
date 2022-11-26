package com.twk.coupon.calculation.template.impl;

import com.twk.coupon.calculation.template.AbstractRuleTemplate;
import com.twk.coupon.calculation.template.RuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author TyanK
 * @description: 打折优惠券
 * @date 2022/11/26 14:48
 */
@Slf4j
@Component
public class DiscountTemplate extends AbstractRuleTemplate implements RuleTemplate {
    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        Long newPrice = convertToDecimal(shopTotalAmount * (quota.doubleValue()/100));
        log.debug("original price={},new price={}",orderTotalAmount,newPrice);
        return newPrice;
    }
}
