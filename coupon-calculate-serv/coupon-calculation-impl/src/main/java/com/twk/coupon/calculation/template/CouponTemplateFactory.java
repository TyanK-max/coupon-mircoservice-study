package com.twk.coupon.calculation.template;

import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculation.template.impl.*;
import com.twk.coupon.template.api.beans.CouponTemplateInfo;
import com.twk.coupon.template.api.enums.CouponType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 15:04
 */
@Component
@Slf4j
public class CouponTemplateFactory {
    @Autowired
    private DiscountTemplate discountTemplate;
    
    @Autowired
    private DummyTemplate dummyTemplate;
    
    @Autowired
    private LonelyNightTemplate nightTemplate;
    
    @Autowired
    private MoneyOffTemplate moneyOffTemplate;
    
    @Autowired
    private RandomReductionTemplate randomReductionTemplate;
    
    public RuleTemplate getTemplate(ShoppingCart order){
        
        if(CollectionUtils.isEmpty(order.getCouponInfos())){
            // 无优惠券
            return dummyTemplate;
        }
        
        // get coupon
        CouponTemplateInfo template = order.getCouponInfos().get(0).getTemplate();
        CouponType couponType = CouponType.convert(template.getType());
        
        // return coupon template
        switch (couponType){
            // 满减
            case MONEY_OFF:
                return moneyOffTemplate;
            // 随机立减
            case RANDOM_DISCOUNT:
                return randomReductionTemplate;
            // 午夜优惠
            case LONELY_NIGHT_MONEY_OFF:
                return nightTemplate;
            // 折扣
            case DISCOUNT:
                return discountTemplate;
            // 未知类
            default:
                return dummyTemplate;
        }
    }
}
