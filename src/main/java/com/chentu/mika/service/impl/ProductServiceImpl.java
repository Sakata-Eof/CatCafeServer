package com.chentu.mika.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chentu.mika.mapper.CatMapper;
import com.chentu.mika.mapper.ProductMapper;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.Product;
import com.chentu.mika.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author: LiZhong Wang
 * @createTime: 2024/11/25 22:19
 * @description:
 */
@Service
public class ProductServiceImpl  extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
