package com.zyy.mall.member.feign;

import com.zyy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  feign做远程调用
 */

@FeignClient("mall-coupon") // 注册中心中的名称
public interface CouponFeignService {

    /**
     *  要调用的方法的完整签名
     */
    @RequestMapping("/coupon/coupon/member/list")
    R memberInvoke();

}
