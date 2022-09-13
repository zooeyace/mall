package com.zyy.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 *  自定义约束校验器
 */

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        for (int i : constraintAnnotation.val()) {
            set.add(i);
        }
    }

    /**
     * @param value 传过来的值
     * @param context 上下文
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return set.contains(value);
    }
}
