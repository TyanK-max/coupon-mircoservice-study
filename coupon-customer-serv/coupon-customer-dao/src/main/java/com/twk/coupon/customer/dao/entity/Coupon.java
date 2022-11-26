package com.twk.coupon.customer.dao.entity;

import com.twk.coupon.customer.api.enums.CouponStatus;
import com.twk.coupon.customer.dao.convertor.CouponStatusConverter;
import com.twk.coupon.template.api.beans.CouponTemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author TyanK
 * @description: coupon in DB
 * @date 2022/11/26 15:57
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
public class Coupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    
    @Column(name = "template_id",nullable = false)
    private Long templateId;
    
    @Column(name = "user_id",nullable = false)
    private Long userId;
    
    @Column(name = "shop_id",nullable = false)
    private Long shopId;
    
    @CreatedDate
    @Column(name = "created_time",nullable = false)
    private Date createdTime;
    
    @Column(name = "status",nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    // 被Transient标记的属性是不会被持久化的
    @Transient
    private CouponTemplateInfo templateInfo;
    
}
