package com.twk.coupon.template.service.intf;

import com.twk.coupon.template.api.beans.CouponInfo;
import com.twk.coupon.template.api.beans.CouponTemplateInfo;
import com.twk.coupon.template.api.beans.PageCouponTemplateInfo;
import com.twk.coupon.template.api.beans.TemplateSearchParams;

import java.util.Collection;
import java.util.Map;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 19:45
 */
public interface CouponTemplateService {
    // 创建优惠券模板
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);
    
    // 根据Id查询优惠券模板
    CouponTemplateInfo loadTemplate(Long id);
    
    // 克隆优惠券模板
    CouponTemplateInfo cloneTemplate(Long templateId);
    
    PageCouponTemplateInfo search(TemplateSearchParams request);
    
    // 让模板失效
    void deleteTemplate(Long id);
    
    // 根据id批量查找模板
    Map<Long,CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);
}
