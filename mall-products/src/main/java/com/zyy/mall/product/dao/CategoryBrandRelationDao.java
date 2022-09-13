package com.zyy.mall.product.dao;

import com.zyy.mall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author zyy
 * @email zyy@gmail.com
 * @date 2022-09-06 12:17:41
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategoryInfo(@Param("catId") Long catId, @Param("name") String name);
}
