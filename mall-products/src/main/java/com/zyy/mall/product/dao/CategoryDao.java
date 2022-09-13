package com.zyy.mall.product.dao;

import com.zyy.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zyy
 * @email zyy@gmail.com
 * @date 2022-09-06 12:17:41
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
