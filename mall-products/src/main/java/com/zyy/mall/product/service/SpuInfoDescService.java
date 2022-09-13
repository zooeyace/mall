package com.zyy.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author zyy
 * @email zyy@gmail.com
 * @date 2022-09-06 12:17:41
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

