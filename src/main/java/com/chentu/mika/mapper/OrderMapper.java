package com.chentu.mika.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @description 针对表【tuser(用户表)】的数据库操作Mapper
* @Entity com.chentu.mislogic.model.entity.UserVO
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




