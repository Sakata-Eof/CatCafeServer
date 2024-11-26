package com.chentu.mika.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.Order;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:处理与订单相关的API请求
 */
@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/orders")
	public Result add(@RequestBody Order form) {
		orderService.save(form);
		return Result.success(null);
	}
	
	@GetMapping("/orders/{orderID}")
	public Result get(@PathVariable("orderID") Integer orderID) {
		Order order = orderService.getOne(Wrappers.<Order>lambdaQuery()
				.eq(Order::getOrderId, orderID)
		);
		return Result.success(order);
	}
	
	@PutMapping("/order")
	public Result update(@RequestBody Order order) {
		orderService.updateById(order);
		return Result.success(null);
	}
	
	@GetMapping("/orders/user/{userID}")
	public Result list(@PathVariable("userID") Integer userID) {
		List<Order> list = orderService.list(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, userID));
		return Result.success(list);
	}
	
	@DeleteMapping("/orders/{userID}/{orderIDs}")
	public Result delete(@PathVariable("userID") Integer userID, @PathVariable("orderIDs") String orderIDs) {
		String[] split = orderIDs.split(",");
		for (String orderID : split) {
			orderService.remove(Wrappers.<Order>lambdaQuery()
					.in(Order::getOrderId, orderID)
			);
		}
		return Result.success(null);
	}
	
}
