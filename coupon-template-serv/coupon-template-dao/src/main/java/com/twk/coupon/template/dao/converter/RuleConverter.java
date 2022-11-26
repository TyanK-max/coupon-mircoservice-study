package com.twk.coupon.template.dao.converter;

import com.alibaba.fastjson.JSON;
import com.twk.coupon.template.api.beans.rules.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author TyanK
 * @description: TODO
 * @date 2022/11/25 19:19
 */
@Converter
public class RuleConverter implements AttributeConverter<TemplateRule,String> {
    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String dbData) {
        return JSON.parseObject(dbData,TemplateRule.class);
    }
}
