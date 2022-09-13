package com.zyy.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-09-06 14:07:45
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

