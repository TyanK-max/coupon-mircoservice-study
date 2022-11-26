package com.twk.coupon.template.dao.converter;

import com.twk.coupon.template.api.enums.CouponType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 19:18
 */
@Converter
public class CouponTypeConverter implements AttributeConverter<CouponType,String> {

    @Override
    public String convertToDatabaseColumn(CouponType couponCategory) {
        return couponCategory.getCode();
    }

    @Override
    public CouponType convertToEntityAttribute(String dbData) {
        return CouponType.convert(dbData);
    }
}
