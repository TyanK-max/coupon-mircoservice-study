package com.twk.coupon.customer.controller;

import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculate.api.beans.SimulationOrder;
import com.twk.coupon.calculate.api.beans.SimulationResponse;
import com.twk.coupon.customer.api.beans.RequestCoupon;
import com.twk.coupon.customer.api.beans.SearchCoupon;
import com.twk.coupon.customer.dao.entity.Coupon;
import com.twk.coupon.customer.service.intf.CouponCustomerService;
import com.twk.coupon.template.api.beans.CouponInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author TyanK
 * @description: 用户优惠券业务接口
 * @date 2022/11/26 17:36
 */
@Slf4j
@RestController
@RequestMapping("coupon-customer")
public class CouponCustomerController {
    
    @Autowired
    private CouponCustomerService customerService;

    @PostMapping("requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        return customerService.requestCoupon(request);
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId,
                             @RequestParam("couponId") Long couponId) {
        customerService.deleteCoupon(userId, couponId);
    }

    // 用户模拟计算每个优惠券的优惠价格
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    // ResponseEntity - 指定返回状态码
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


    // 实现的时候最好封装一个search object类
    @PostMapping("findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }
}
