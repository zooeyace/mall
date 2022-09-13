package com.zyy.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author zyy
 * @email zyy@gmail.com
 * @date 2022-09-06 12:17:41
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

