package com.zyy.mall.product.vo.resposne;

import com.zyy.mall.product.vo.request.AttrVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性值返回时的其他字段
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class AttrResponseVo extends AttrVo {
    private String catelogName;
    private String groupName;

    /**
     * 完整路径
     */
    private Long[] catelogPath;
}
