package com.twk.coupon.template.api.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/23 20:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    // 满减 - 减掉的钱数
    // 折扣 - 90 = 9折,  95=95折
    // 随机减 - quota是最高减免金额
    // 晚间减 - quota是日间优惠额，晚间翻倍
    private Long quota;

    // 最低达到多少消费才能用 货币单位为 分
    private Long threshold;
}
