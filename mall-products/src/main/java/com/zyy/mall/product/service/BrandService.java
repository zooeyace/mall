package com.zyy.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author zyy
 * @email zyy@gmail.com
 * @date 2022-09-06 12:17:41
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetails(BrandEntity brand);
}

