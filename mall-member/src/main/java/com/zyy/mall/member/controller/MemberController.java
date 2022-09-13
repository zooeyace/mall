package com.zyy.mall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.zyy.mall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyy.mall.member.entity.MemberEntity;
import com.zyy.mall.member.service.MemberService;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.R;


/**
 * 会员
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-09-06 14:07:45
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    /**
     * 测试远程调用coupon服务
     */
    @RequestMapping("/coupons")
    public R testFeign() {
        MemberEntity member = new MemberEntity();
        member.setNickname("zhangsan");
        R couponsOfMember = couponFeignService.memberInvoke();
//        System.out.println("---" + couponsOfMember + "---");
        return R.ok().put("member", member)
                .put("couponList", couponsOfMember.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
