package com.twk.coupon.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author TyanK
 * @description: application
 * @date 2022/11/25 20:30
 */
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.twk"})
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class,args);
    }
}
