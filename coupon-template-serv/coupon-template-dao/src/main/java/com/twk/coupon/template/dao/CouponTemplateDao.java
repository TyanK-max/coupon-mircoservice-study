package com.twk.coupon.template.dao;

import com.twk.coupon.template.dao.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 19:27
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate,Long> {
    
    // 根据shopId查找所有优惠券
    List<CouponTemplate> findAllByShopId(Long shopId);
    
    // 分页
    Page<CouponTemplate> findAllByIdIn(List<Long> id, Pageable page);
    
    // 查询店铺有多少可用的优惠券模板
    Integer countByShopIdAndAvailable(Long shopId,Boolean available);

    // 将优惠券设置为不可用
    @Modifying
    @Query("update CouponTemplate c set c.available = 0 where c.id = :id")
    int makeCouponUnavailable(@Param("id") Long id);
}
