package com.twk.coupon.customer.service;

import com.google.common.collect.Lists;
import com.twk.coupon.calculate.api.beans.ShoppingCart;
import com.twk.coupon.calculate.api.beans.SimulationOrder;
import com.twk.coupon.calculate.api.beans.SimulationResponse;
import com.twk.coupon.calculation.controller.service.intf.CouponCalculationService;
import com.twk.coupon.customer.api.beans.RequestCoupon;
import com.twk.coupon.customer.api.beans.SearchCoupon;
import com.twk.coupon.customer.api.enums.CouponStatus;
import com.twk.coupon.customer.dao.CouponDao;
import com.twk.coupon.customer.dao.entity.Coupon;
import com.twk.coupon.customer.service.intf.CouponCustomerService;
import com.twk.coupon.template.api.beans.CouponInfo;
import com.twk.coupon.template.api.beans.CouponTemplateInfo;
import com.twk.coupon.template.service.intf.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TyanK
 * @description: 用户优惠券服务实现
 * @date 2022/11/26 16:40
 */
@Slf4j
@Service
public class CouponCustomerServiceImpl implements CouponCustomerService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponTemplateService templateService;

    @Autowired
    private CouponCalculationService calculationService;


    /**
     * @description: 领取优惠券
     * @author TyanK
     * @date 2022/11/26 17:34
     */
    @Override
    public Coupon requestCoupon(RequestCoupon request) {
        CouponTemplateInfo templateInfo = templateService.loadTemplate(request.getCouponTemplateId());

        // 优惠券是否存在
        if (templateInfo == null) {
            log.error("Invalid template id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("Invalid template id");
        }

        // 优惠券是否过期
        long now = Calendar.getInstance().getTimeInMillis();
        Long expTime = templateInfo.getRule().getDeadLine();
        if (expTime != null && now > expTime || BooleanUtils.isFalse(templateInfo.getAvailable())) {
            log.error("template is not available,id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("template is unavailable!");
        }

        // 领券上限
        Long count = couponDao.countByUserIdAndTemplateId(request.getUserId(), request.getCouponTemplateId());
        if (count >= templateInfo.getRule().getLimitation()) {
            log.error("exceed maximum coupon number");
            throw new IllegalArgumentException("you got to much this coupon");
        }

        Coupon coupon = Coupon.builder()
                .id(1L)
                .templateId(request.getCouponTemplateId())
                .userId(request.getUserId())
                .shopId(templateInfo.getShopId())
                .status(CouponStatus.AVAILABLE)
                .build();
        couponDao.save(coupon);
        return coupon;
    }

    /**
     * @description: 优惠券清算
     * @author TyanK
     * @date 2022/11/26 17:35
     */
    @Override
    @Transactional
    public ShoppingCart placeOrder(ShoppingCart order) {
        if (CollectionUtils.isEmpty(order.getProducts())) {
            log.error("invalid check out request,order={}", order);
            throw new IllegalArgumentException("cart is Empty!");
        }

        Coupon coupon = null;
        if (order.getCouponId() != null) {
            // 如果有优惠券，验证是否可用，并且是当前客户的
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .id(order.getCouponId())
                    .status(CouponStatus.AVAILABLE)
                    .build();
            coupon = couponDao.findAll(Example.of(example))
                    .stream()
                    .findFirst()
                    // 如果找不到券，就抛出异常
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            // 导出优惠券模板信息
            // 模板中有discount用于计算
            CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
            couponInfo.setTemplate(templateService.loadTemplate(coupon.getTemplateId()));
            order.setCouponInfos(Lists.newArrayList(couponInfo));
        }

        // 调用calculation计算优惠后订单价格
        ShoppingCart checkOutInfo = calculationService.calculateOrderPrice(order);

        if (coupon != null) {
            // 如果优惠券没有被结算掉，而用户传递了优惠券，报错提示该订单满足不了优惠条件
            if (CollectionUtils.isEmpty(checkOutInfo.getCouponInfos())) {
                log.error("cannot use coupon in this order, couponId={}", coupon.getId());
                throw new IllegalArgumentException("coupon is not applicable to this order");
            }
            log.info("update coupon status to used, couponId={}", coupon.getId());
            coupon.setStatus(CouponStatus.USED);
            couponDao.save(coupon);
        }
        return checkOutInfo;
    }

    @Override
    public SimulationResponse simulateOrderPrice(SimulationOrder order) {
        List<CouponInfo> couponInfos = Lists.newArrayList();

        // 挨个循环，把优惠券信息加载出来
        // 高并发场景下不能这么一个个循环，更好的做法是批量查询
        // 而且券模板一旦创建不会改内容，所以在创建端做数据异构放到缓存里，使用端从缓存捞template信息
        for (Long couponId : order.getCouponIDs()) {
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .id(couponId)
                    .status(CouponStatus.AVAILABLE)
                    .build();
            Optional<Coupon> couponOptional = couponDao.findAll(Example.of(example))
                    .stream()
                    .findFirst();
            // 加载优惠券模板信息
            if (couponOptional.isPresent()) {
                Coupon coupon = couponOptional.get();
                CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
                couponInfo.setTemplate(templateService.loadTemplate(coupon.getTemplateId()));
                couponInfos.add(couponInfo);
            }
        }
        order.setCouponInfos(couponInfos);

        // 调用接口试算服务
        return calculationService.simulateOrder(order);
    }

    // 删除优惠券
    @Override
    public void deleteCoupon(Long userId, Long couponId) {
        Coupon example = Coupon.builder()
                .userId(userId)
                .id(couponId)
                .status(CouponStatus.AVAILABLE)
                .build();
        Coupon coupon = couponDao.findAll(Example.of(example))
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find available coupon with this id"));
        coupon.setStatus(CouponStatus.INACTIVE);
        couponDao.save(coupon);
    }

    // 查询优惠券接口
    @Override
    public List<CouponInfo> findCoupon(SearchCoupon requests) {
        Coupon example = Coupon.builder()
                .userId(requests.getUserId())
                .status(CouponStatus.convert(requests.getCouponStatus()))
                .shopId(requests.getShopId())
                .build();
        List<Coupon> coupons = couponDao.findAll(Example.of(example));
        if (coupons.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Long> templateIds = coupons.stream()
                .map(Coupon::getTemplateId)
                .collect(Collectors.toList());
        Map<Long, CouponTemplateInfo> templateInfoMap = templateService.getTemplateInfoMap(templateIds);
        coupons.stream().forEach(e -> e.setTemplateInfo(templateInfoMap.get(e.getTemplateId())));

        return coupons.stream()
                .map(CouponConverter::convertToCoupon)
                .collect(Collectors.toList());
    }
}
