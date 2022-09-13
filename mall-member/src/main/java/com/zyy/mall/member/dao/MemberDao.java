package com.zyy.mall.member.dao;

import com.zyy.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ${author}
 * @email ${email}
 * @date 2022-09-06 14:07:45
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
