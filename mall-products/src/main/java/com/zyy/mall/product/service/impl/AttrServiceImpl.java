package com.zyy.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyy.common.utils.PageUtils;
import com.zyy.common.utils.Query;
import com.zyy.mall.product.dao.AttrAttrgroupRelationDao;
import com.zyy.mall.product.dao.AttrDao;
import com.zyy.mall.product.dao.AttrGroupDao;
import com.zyy.mall.product.dao.CategoryDao;
import com.zyy.mall.product.entity.AttrAttrgroupRelationEntity;
import com.zyy.mall.product.entity.AttrEntity;
import com.zyy.mall.product.entity.AttrGroupEntity;
import com.zyy.mall.product.entity.CategoryEntity;
import com.zyy.mall.product.service.AttrService;
import com.zyy.mall.product.service.CategoryService;
import com.zyy.mall.product.vo.request.AttrVo;
import com.zyy.mall.product.vo.resposne.AttrResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrGroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        // 本表
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        // 关联关系
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId()); // 保存之后才会有attrId，传过来的VO是没有的
        attrGroupRelationDao.insert(relationEntity);
    }

    @Override
    public PageUtils pageBaseAttr(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", "base".equalsIgnoreCase(type) ? 1 : 0);

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper
                    .like("attr_name", key)
                    .or()
                    .eq("attr_id", key)
            ;
        }

        if (catelogId != 0) {
            queryWrapper.and(e -> e.eq("catelog_id", catelogId));
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);

        /** 操作分组名称attrGroupName、分类名称catelogName字段 */
        // 三级和属性是一对多,三级和分组是一对多,分组和属性是 1 对多
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> res = records.stream().map((attrEntity) -> {
            // AttrEntity => AttrResponseVo
            AttrResponseVo responseVo = new AttrResponseVo();
            BeanUtils.copyProperties(attrEntity, responseVo);
            // groupName
            AttrAttrgroupRelationEntity relation =
                    attrGroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (relation != null && relation.getAttrGroupId() != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relation.getAttrGroupId());
                responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            // catelogName
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null && categoryEntity.getName() != null)
                responseVo.setCatelogName(categoryEntity.getName());
            return responseVo;
        }).collect(Collectors.toList());
        pageUtils.setList(res);
        return pageUtils;
    }

    @Override
    public AttrResponseVo getAttrInfo(Long attrId) {
        AttrEntity byId = this.getById(attrId);
        AttrResponseVo responseVo = new AttrResponseVo();
        BeanUtils.copyProperties(byId, responseVo);
        // 分组信息 groupId,groupName
        AttrAttrgroupRelationEntity relation =
                attrGroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", byId.getAttrId()));
        if (relation != null && relation.getAttrGroupId() != null) {
            responseVo.setAttrGroupId(relation.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relation.getAttrGroupId());
            if (attrGroupEntity != null) responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
        }
        // 分类信息 catelogPath,catelogName
        Long catelogId = byId.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        responseVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) responseVo.setCatelogName(categoryEntity.getName());
        return responseVo;
    }

    @Override
    @Transactional
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        // 关联关系 修改
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(attr.getAttrId());
        relationEntity.setAttrGroupId(attr.getAttrGroupId());

        // 要判断当前关系表内 有无该条属性(attr_id)的记录
        int count = attrGroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        if (count > 0) {
            attrGroupRelationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        } else {
            // 新增
            attrGroupRelationDao.insert(relationEntity);
        }
    }

}