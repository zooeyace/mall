package com.zyy.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-09-06 14:07:45
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

