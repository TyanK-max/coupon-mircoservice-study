package com.twk.coupon.calculate.api.beans;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 21:15
 */
@Data
@NoArgsConstructor
public class SimulationResponse {
    
    // 最省钱的coupon
    private Long bestCouponId;
    
    // 每一个coupon对应的order价格
    private Map<Long, Long> couponToOrderPrice = Maps.newHashMap();
}
