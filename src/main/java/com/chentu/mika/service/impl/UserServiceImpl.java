package com.chentu.mika.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chentu.mika.mapper.UserMapper;
import com.chentu.mika.model.entity.User;
import com.chentu.mika.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 31465
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2024-10-20 13:13:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
		implements UserService {
	
}




