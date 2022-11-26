package com.twk.coupon.calculation.template;

import com.twk.coupon.calculate.api.beans.ShoppingCart;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 21:28
 */

public interface RuleTemplate {
    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
