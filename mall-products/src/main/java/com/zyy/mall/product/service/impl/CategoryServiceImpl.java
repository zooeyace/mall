package com.zyy.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.Query;
import com.zyy.mall.product.dao.CategoryDao;
import com.zyy.mall.product.entity.CategoryEntity;
import com.zyy.mall.product.service.CategoryBrandRelationService;
import com.zyy.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1.查出所有
        List<CategoryEntity> categories = baseMapper.selectList(null);
        // 2.组装成父子结构
        List<CategoryEntity> res = categories.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .peek(menu -> menu.setChildren(recursionChildren(categories, menu)))
                .sorted((a, b) -> {
                    return a.getSort() - b.getSort();
                })
                .collect(Collectors.toList());

        return res;
    }

    @Override
    public void removeLogicByIds(List<Long> asList) {
        // 先检查
        //TODO 逻辑删除category时先校验

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        LinkedList<Long> list = new LinkedList<>();
        findParents(list, catelogId);
        return list.toArray(new Long[list.size()]);
    }

    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        // self
        this.updateById(category);
        // relation
        categoryBrandRelationService.updateCategoryInfo(category.getCatId(), category.getName());
    }

    private void findParents(LinkedList<Long> list, Long catelogId) {
        CategoryEntity curr = baseMapper.selectById(catelogId);
        while (curr != null && curr.getParentCid() != null) {
            list.addFirst(curr.getCatId());
            curr = baseMapper.selectById(curr.getParentCid());
        }
    }

    /**
     * 递归查询 子分类
     * 从all找出 符合当前root的子分类
     */
    private List<CategoryEntity> recursionChildren(List<CategoryEntity> all, CategoryEntity root) {
        List<CategoryEntity> children = all.stream()
                .filter(category -> root.getCatId().equals(category.getParentCid()))
                .peek(e -> e.setChildren(recursionChildren(all, e))) // 处理NPE
                .sorted(Comparator.comparingInt(e -> e.getSort() == null ? 0 : e.getSort()))
                .collect(Collectors.toList());
        return children;
    }

}