package com.zyy.mall.product.exception;

import com.zyy.common.exception.BizCodeEnum;
import com.zyy.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 *  全局异常捕获 利用javax的validation
 */

@RestControllerAdvice(basePackages = {"com.zyy.mall.product.controller"})
@Slf4j
public class GlobalException {

    /**
     *  参数合法性异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errInfo = new HashMap<>();
        result.getAllErrors().forEach((err) -> {
            errInfo.put(err.getObjectName(), err.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMessage())
                .put("data", errInfo);
    }

    @ExceptionHandler(Throwable.class)
    public R handleException(Throwable throwable) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMessage());
    }

}
