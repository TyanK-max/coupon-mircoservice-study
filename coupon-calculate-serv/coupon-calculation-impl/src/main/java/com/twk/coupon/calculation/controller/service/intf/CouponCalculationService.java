package com.twk.coupon.calculation.controller.service.intf;

import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculate.api.beans.SimulationOrder;
import com.twk.coupon.calculate.api.beans.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 14:42
 */
public interface CouponCalculationService {
    
    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);
    
    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}
