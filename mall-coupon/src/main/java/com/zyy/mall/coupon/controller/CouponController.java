package com.zyy.mall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyy.mall.coupon.entity.CouponEntity;
import com.zyy.mall.coupon.service.CouponService;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.R;

@RefreshScope // 自动刷新配置的修改
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Value("${coupon.description}")
    private String couponDescription;

    @Value("${coupon.detail}")
    private String couponDetail;

    /**
     *  测试 nacos 做配置中心
     *  效果： 具体值放在yml配置文件，上线后要修改值，尽管只需要改配置文件还是要重启服务
     *          用nacos做配置中心 将配置放到nacos实现配置实时更新
     */
    @RequestMapping("/config")
    public R testNacosConfig() {
        return R.ok().put("description", couponDescription)
                .put("detail", couponDetail);
    }

    /**
     *  模拟 获取某个会员的优惠券情况
     */
    @RequestMapping("/member/list")
    public R memberInvoke() {
        CouponEntity coupon = new CouponEntity();
        coupon.setCouponName("模拟优惠券.");
        return R.ok().put("coupons", Arrays.asList(coupon));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:coupon:delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
