package com.twk.coupon.calculate.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TyanK
 * @description: product Info
 * @date 2022/11/25 21:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long price;
    
    private Integer count;
    
    private Long shopId;
}
