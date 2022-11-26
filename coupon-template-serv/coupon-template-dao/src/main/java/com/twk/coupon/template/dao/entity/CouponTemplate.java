package com.twk.coupon.template.dao.entity;

import com.twk.coupon.template.api.beans.rules.TemplateRule;
import com.twk.coupon.template.api.enums.CouponType;
import com.twk.coupon.template.dao.converter.CouponTypeConverter;
import com.twk.coupon.template.dao.converter.RuleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 19:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    
    // 是否可用
    @Column(name = "available",nullable = false)
    private Boolean available;
    
    @Column(name = "name",nullable = false)
    private String name;
    
    // 适用门店，如果为空，全场通用
    @Column(name = "shop_id")
    private Long shopId;
    
    @Column(name = "description",nullable = false)
    private String description;
    
    // 优惠券类型
    @Column(name = "type",nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType category;
    
    // 创建时间
    @CreatedDate
    @Column(name = "created_time",nullable = false)
    private Date createdTime;
    
    // 优惠券核算规则，转换为JSON
    @Column(name = "rule",nullable = false)
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;
    
}
