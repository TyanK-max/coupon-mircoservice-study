package com.twk.coupon.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/26 15:36
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.twk"})
public class CalculateApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculateApplication.class,args);
    }
}
