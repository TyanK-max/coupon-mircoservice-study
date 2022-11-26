package com.twk.coupon.customer.service.intf;

import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculate.api.beans.SimulationOrder;
import com.twk.coupon.calculate.api.beans.SimulationResponse;
import com.twk.coupon.customer.api.beans.RequestCoupon;
import com.twk.coupon.customer.api.beans.SearchCoupon;
import com.twk.coupon.customer.dao.entity.Coupon;
import com.twk.coupon.template.api.beans.CouponInfo;

import java.util.List;

/**
 * @author TyanK
 * @description: 用户服务接口
 * @date 2022/11/26 16:13
 */
public interface CouponCustomerService {
    
    // 领券接口
    Coupon requestCoupon(RequestCoupon request);
    
    // 核销/验证 优惠券
    ShoppingCart placeOrder(ShoppingCart info);
    
    // 试算优惠券
    SimulationResponse simulateOrderPrice(SimulationOrder order);
    
    void deleteCoupon(Long userId,Long couponId);
    
    // 查询用户拥有的优惠券
    List<CouponInfo> findCoupon(SearchCoupon requests);
}
