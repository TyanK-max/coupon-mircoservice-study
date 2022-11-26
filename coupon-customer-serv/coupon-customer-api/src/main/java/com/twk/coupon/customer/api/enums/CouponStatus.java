package com.twk.coupon.customer.api.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 15:53
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {
    AVAILABLE("未使用",1),
    USED("已使用",2),
    INACTIVE("已过期",3);
    
    private String desc;
    private Integer code;
    
    public static CouponStatus convert(Integer code){
        if(code == null){
            return null;
        }
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
