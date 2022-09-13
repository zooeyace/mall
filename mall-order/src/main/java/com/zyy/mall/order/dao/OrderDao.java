package com.zyy.mall.order.dao;

import com.zyy.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author ${author}
 * @email ${email}
 * @date 2022-09-06 14:23:40
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
