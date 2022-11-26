package com.twk.coupon.calculate.api.beans;

import com.twk.coupon.template.api.beans.CouponInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author TyanK
 * @description: 价格试算
 * @date 2022/11/25 21:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationOrder {
    @NotEmpty
    private List<Product> products;
    
    @NotEmpty
    private List<Long> couponIDs;
    
    @NotEmpty
    private List<CouponInfo> couponInfos;
    
    @NotNull
    private Long userId;
}
