package com.twk.coupon.calculation.controller;

import com.alibaba.fastjson.JSON;
import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculate.api.beans.SimulationOrder;
import com.twk.coupon.calculate.api.beans.SimulationResponse;
import com.twk.coupon.calculation.controller.service.intf.CouponCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author TyanK
 * @description: 优惠券结算接口
 * @date 2022/11/26 15:28
 */
@Slf4j
@RestController
@RequestMapping("/calculator")
public class CouponCalculationController {
    
    @Autowired
    private CouponCalculationService calculationService;
    
    // check out price
    @PostMapping("/checkout")
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart settlement){
        log.info("will do this calculation:{}", JSON.toJSONString(settlement));
        return calculationService.calculateOrderPrice(settlement);
    }

    // 优惠券列表挨个试算
    // 给客户提示每个可用券的优惠额度，帮助挑选
    @PostMapping("/simulate")
    public SimulationResponse simulate(@RequestBody SimulationOrder simulator){
        log.info("will do this simulation:{}",JSON.toJSONString(simulator));
        return calculationService.simulateOrder(simulator);
    }
}
