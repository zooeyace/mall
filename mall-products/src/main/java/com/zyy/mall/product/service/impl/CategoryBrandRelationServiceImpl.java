package com.zyy.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.Query;
import com.zyy.mall.product.dao.BrandDao;
import com.zyy.mall.product.dao.CategoryBrandRelationDao;
import com.zyy.mall.product.dao.CategoryDao;
import com.zyy.mall.product.entity.BrandEntity;
import com.zyy.mall.product.entity.CategoryBrandRelationEntity;
import com.zyy.mall.product.entity.CategoryEntity;
import com.zyy.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetails(CategoryBrandRelationEntity categoryBrandRelationEntity) {
        CategoryEntity categoryEntity = categoryDao.selectById(categoryBrandRelationEntity.getCatelogId());
        BrandEntity brandEntity = brandDao.selectById(categoryBrandRelationEntity.getBrandId());
        categoryBrandRelationEntity.setBrandName(brandEntity.getName());
        categoryBrandRelationEntity.setCatelogName(categoryEntity.getName());
        // 设置完冗余字段 最后调用save方法
        this.save(categoryBrandRelationEntity);
    }

    @Override
    public void updateBrandInfo(Long brandId, String name) {
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        relationEntity.setBrandId(brandId);
        relationEntity.setBrandName(name);
        this.update(relationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateCategoryInfo(Long catId, String name) {
        this.baseMapper.updateCategoryInfo(catId, name);
    }

}