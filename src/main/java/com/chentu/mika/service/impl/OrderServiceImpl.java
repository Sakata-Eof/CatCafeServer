package com.chentu.mika.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chentu.mika.mapper.CatMapper;
import com.chentu.mika.mapper.OrderMapper;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.Order;
import com.chentu.mika.service.OrderService;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
