package com.zyy.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyy.common.utils.PageUtils;
import com.zyy.mall.product.entity.AttrEntity;
import com.zyy.mall.product.vo.request.AttrVo;
import com.zyy.mall.product.vo.resposne.AttrResponseVo;

import java.util.Map;

/**
 * 商品属性
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils pageBaseAttr(Map<String, Object> params, Long catelogId, String type);

    /**
     *  [属性]修改时需要回显的信息获取
     */
    AttrResponseVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);
}

