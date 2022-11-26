package com.twk.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author TyanK
 * @description: 优惠券模板分页查找类
 * @date 2022/11/25 18:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageCouponTemplateInfo {
    public List<CouponTemplateInfo> templates;
    public int page;
    public long total;
}
