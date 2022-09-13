package com.zyy.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.Query;
import com.zyy.mall.product.dao.BrandDao;
import com.zyy.mall.product.entity.BrandEntity;
import com.zyy.mall.product.service.BrandService;
import com.zyy.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((ele) -> {
                ele.eq("brand_id", key).or().like("name", key);
            });
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetails(BrandEntity brand) {
        // 保证冗余字段数据一致
        this.updateById(brand); // brand实体类先修改
        if (!StringUtils.isEmpty(brand.getName())) {
            categoryBrandRelationService.updateBrandInfo(brand.getBrandId(), brand.getName());

            //TODO 更新其他关联  categoryInfo....
        }
    }

}