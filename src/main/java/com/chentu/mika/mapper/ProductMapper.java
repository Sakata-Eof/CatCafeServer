package com.chentu.mika.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
* @description 针对表【user(用户表)】的数据库操作Mapper
* @Entity com.chentu.mislogic.model.entity.UserVO
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}




