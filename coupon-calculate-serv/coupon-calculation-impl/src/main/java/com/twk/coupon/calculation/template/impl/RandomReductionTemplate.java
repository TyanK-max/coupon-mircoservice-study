package com.twk.coupon.calculation.template.impl;

import com.twk.coupon.calculation.template.AbstractRuleTemplate;
import com.twk.coupon.calculation.template.RuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 15:01
 */
@Slf4j
@Component
public class RandomReductionTemplate extends AbstractRuleTemplate implements RuleTemplate {
    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        Long maxBenefit = Math.min(shopTotalAmount,quota);
        int reductionAmount = new Random().nextInt(maxBenefit.intValue());
        Long newCost = orderTotalAmount - reductionAmount;
        log.debug("original price={},new price={}",orderTotalAmount,newCost);
        return newCost;
    }
}
