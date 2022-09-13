package com.zyy.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyy.common.validation.AddGroup;
import com.zyy.common.validation.ListValue;
import com.zyy.common.validation.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @NotNull(groups = {UpdateGroup.class}, message = "修改时必须携带品牌id")
    @Null(groups = {AddGroup.class}, message = "新增时不可携带id")
    private Long brandId;

    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不可为空", groups = {AddGroup.class, UpdateGroup.class}) // 意思是 新增修改都不可以空
    private String name;

    /**
     * 品牌logo地址  修改时候可以不传logo字段，但是携带了就必须是url格式
     */
    @NotBlank(groups = {AddGroup.class}) // 但是 只有新增时不可为空
    @URL(message = "logo地址必须是URL格式", groups = {AddGroup.class, UpdateGroup.class}) // 对于URL的格式 新增和修改都校验
    private String logo;

    /**
     * 介绍
     */
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     * 使用自定义注解做校验
     */
    @ListValue(val = {0, 1}, groups = {AddGroup.class})
    private Integer showStatus;

    /**
     * 检索首字母
     */
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母只能是一个字母", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;

    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0, message = "排序字段最小为0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}
