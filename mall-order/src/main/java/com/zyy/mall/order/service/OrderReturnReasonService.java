package com.zyy.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-09-06 14:23:40
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

