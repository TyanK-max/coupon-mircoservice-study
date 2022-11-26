package com.twk.coupon.template.service;

import com.twk.coupon.template.api.beans.CouponTemplateInfo;
import com.twk.coupon.template.api.beans.PageCouponTemplateInfo;
import com.twk.coupon.template.api.beans.TemplateSearchParams;
import com.twk.coupon.template.api.enums.CouponType;
import com.twk.coupon.template.converter.CouponTemplateConverter;
import com.twk.coupon.template.dao.CouponTemplateDao;
import com.twk.coupon.template.dao.entity.CouponTemplate;
import com.twk.coupon.template.service.intf.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TyanK
 * @description: 优惠券 Service 层
 * @date 2022/11/25 19:51
 */
@Slf4j
@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Autowired
    private CouponTemplateDao templateDao;

    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        if (request.getShopId() != null) {
            Integer available = templateDao.countByShopIdAndAvailable(request.getShopId(), true);
            if (available >= 100) {
                log.error("the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException("exceeded the maximum of coupon templates that you can create");
            }
        }

        CouponTemplate template = CouponTemplate.builder()
                .name(request.getName())
                .description(request.getDesc())
                .available(true)
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .rule(request.getRule())
                .build();
        template = templateDao.save(template);
        return CouponTemplateConverter.convertCouponTemplate(template);
    }


    @Override
    public CouponTemplateInfo loadTemplate(Long id) {
        if (id == null) {
            throw new IllegalStateException("wrong id!");
        }
        Optional<CouponTemplate> template = templateDao.findById(id);
        return template.map(CouponTemplateConverter::convertCouponTemplate).orElse(null);
    }

    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        log.info("cloning template id {}", templateId);
        CouponTemplate source = templateDao
                .findById(templateId).orElseThrow(() -> new IllegalArgumentException("invalid template Id"));
        
        // copy
        CouponTemplate target = new CouponTemplate();
        BeanUtils.copyProperties(source, target);
        
        // 恢复出厂设置
        target.setAvailable(true);
        target.setId(null);
        
        // 保存
        templateDao.save(target);
        return CouponTemplateConverter.convertCouponTemplate(target);
    }

    @Override
    public PageCouponTemplateInfo search(TemplateSearchParams request) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();

        Pageable page = PageRequest.of(request.getPage(), request.getPageSize());
        Page<CouponTemplate> result = templateDao.findAll(Example.of(example), page);
        List<CouponTemplateInfo> couponTemplateInfos = result.stream()
                .map(CouponTemplateConverter::convertCouponTemplate)
                .collect(Collectors.toList());

        PageCouponTemplateInfo response = PageCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();

        return response;
    }

    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        int rows = templateDao.makeCouponUnavailable(id);
        if(rows == 0){
            throw new IllegalArgumentException("Template not found " + id);
        }
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> templates = templateDao.findAllById(ids);
        return templates.stream().map(CouponTemplateConverter::convertCouponTemplate)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
    }
}
