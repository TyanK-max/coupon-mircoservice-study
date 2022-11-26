package com.twk.coupon.customer.dao.convertor;

import com.twk.coupon.customer.api.enums.CouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;

/**
 * @author TyanK
 * @description: Convertor
 * @date 2022/11/26 16:04
 */
@Converter
public class CouponStatusConverter implements AttributeConverter<CouponStatus,Integer> {


    @Override
    public Integer convertToDatabaseColumn(CouponStatus status) {
        return status.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer code) {
        return CouponStatus.convert(code);
    }
}
