package com.chentu.mika.controller;

import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.User;
import com.chentu.mika.model.form.LoginForm;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * @description:处理与用户相关的API请求，特别是用户登录和注册功能
 */
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public Result login(@RequestBody LoginForm form) {
		Assert.notNull(form, "参数不能为空");
		Assert.notNull(form.getUserEmail(), "邮箱不能为空");
		Assert.notNull(form.getUserPassword(), "密码不能为空");
		User one = userService.getOne(Wrappers.<User>lambdaQuery()
				.eq(User::getUserEmail, form.getUserEmail())
				.eq(User::getUserPassword, form.getUserPassword())
		);
		if (one == null) {
			throw new RuntimeException("用户名或密码错误");
		}
		HashMap<String, Integer> hash = new HashMap<>();
		hash.put("sub", one.getUserID());
		hash.put("userType", one.getUserType()?1:0);
		String sign = JWT.create().addPayloads(hash).setKey("CATCAFE".getBytes()).
				setExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).
				sign();
		
		return Result.success(sign);
	}
	
	@PostMapping("/register")
	public Result register(@RequestBody User user) {
		userService.save(user);
		return Result.success(null);
	}
}
