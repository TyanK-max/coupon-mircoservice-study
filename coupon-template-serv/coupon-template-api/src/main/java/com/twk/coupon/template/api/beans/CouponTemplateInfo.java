package com.twk.coupon.template.api.beans;

import com.twk.coupon.template.api.beans.rules.TemplateRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author TyanK
 * @description: 优惠券模板类
 * @date 2022/11/23 20:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponTemplateInfo {

    private Long id;

    // 券名
    @NotNull
    private String name;

    // 描述
    @NotNull
    private String desc;

    // 优惠券类型
    @NotNull
    private String type;

    // 适用门店
    private Long shopId;
    
    // 使用规则
    private TemplateRule rule;

    // 是否可使用
    private Boolean available;

}
