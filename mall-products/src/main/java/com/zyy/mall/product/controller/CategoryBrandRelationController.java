package com.zyy.mall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.R;
import com.zyy.mall.product.entity.CategoryBrandRelationEntity;
import com.zyy.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     *  关系表 brand-category 做分类关联 即一个brand可属于多个分类
     *  {
     * 	"msg": "success",
     * 	"code": 0,
     * 	"data": [{
     * 		"catelogId": 0,
     * 		"catelogName": "string",
     *        }]
     *   响应的数据为 List，且就是关系表字段
     * }
     */
    @GetMapping("/catelog/list")
    public R categoryBrandRelationList(@RequestParam("brandId") Long brandId){
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", brandId);
        List<CategoryBrandRelationEntity> res = categoryBrandRelationService.list(wrapper);
        return R.ok().put("data", res);
    }

    /**
     *  新增品牌与分类关联关系
     *  req: {"brandId":1,"catelogId":2}  前端将其封装成json格式
     *  而数据库有冗余字段 所以先设置冗余字段值，再调用默认的save方法
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelationEntity) {
        categoryBrandRelationService.saveDetails(categoryBrandRelationEntity);
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    // @RequiresPermissions("product:categorybrandrelation:save")
//    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
//		categoryBrandRelationService.save(categoryBrandRelation);
//
//        return R.ok();
//    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
