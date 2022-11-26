package com.twk.coupon.template.api.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TyanK
 * @description: 优惠券规则
 * @date 2022/11/23 20:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRule {
    
    // 折扣
    private Discount discount;
    // 最多领取数量
    private Integer limitation;
    // 过期时间
    private Long deadLine;
    
}
