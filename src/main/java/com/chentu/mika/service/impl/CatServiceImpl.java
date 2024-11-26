package com.chentu.mika.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chentu.mika.mapper.CatMapper;
import com.chentu.mika.mapper.UserMapper;
import com.chentu.mika.model.entity.Cat;
import com.chentu.mika.model.entity.User;
import com.chentu.mika.service.CatService;
import org.springframework.stereotype.Service;

/*
 */
@Service
public class CatServiceImpl  extends ServiceImpl<CatMapper, Cat> implements CatService {
}
