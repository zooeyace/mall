package com.zyy.mall.product.vo.request;

import com.zyy.mall.product.entity.AttrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttrVo extends AttrEntity {

    /**
     * 分组id
     */
    private Long attrGroupId;
}
