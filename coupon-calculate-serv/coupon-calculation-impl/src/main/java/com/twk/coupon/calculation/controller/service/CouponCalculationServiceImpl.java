package com.twk.coupon.calculation.controller.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculate.api.beans.SimulationOrder;
import com.twk.coupon.calculate.api.beans.SimulationResponse;
import com.twk.coupon.calculation.controller.service.intf.CouponCalculationService;
import com.twk.coupon.calculation.template.CouponTemplateFactory;
import com.twk.coupon.calculation.template.RuleTemplate;
import com.twk.coupon.template.api.beans.CouponInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author TyanK
 * @description: 优惠券结算及试算实现
 * @date 2022/11/26 14:44
 */
@Slf4j
@Service
public class CouponCalculationServiceImpl implements CouponCalculationService {
    
    @Autowired
    private CouponTemplateFactory factory;
    
    // 结算优惠券，工厂模式
    @Override
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart) {
        log.info("calculate order price:{}", JSON.toJSONString(cart));
        RuleTemplate template = factory.getTemplate(cart);
        return template.calculate(cart);
    }

    // try to find the best coupon, but only can calculate the first coupon
    @Override
    public SimulationResponse simulateOrder(@RequestBody SimulationOrder order) {
        SimulationResponse response = new SimulationResponse();
        Long minOrderPrice = Long.MAX_VALUE;
        
        // 计算每一个优惠券的订单价格
        for(CouponInfo info : order.getCouponInfos()){
            
            ShoppingCart cart = new ShoppingCart();
            cart.setProducts(order.getProducts());
            cart.setCouponInfos(Lists.newArrayList(info));
            cart = factory.getTemplate(cart).calculate(cart);
            
            Long couponId = info.getId();
            Long orderPrice = cart.getCost();

            // 设置当前优惠券对应的订单价格
            response.getCouponToOrderPrice().put(couponId,orderPrice);

            // 比较订单价格，设置当前最优优惠券的ID
            if(minOrderPrice > orderPrice){
                response.setBestCouponId(info.getId());
                minOrderPrice = orderPrice;
            }
        }
        return response;
    }
}
