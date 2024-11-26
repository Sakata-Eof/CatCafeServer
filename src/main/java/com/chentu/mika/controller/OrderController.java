package com.chentu.mika.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chentu.mika.model.entity.Order;
import com.chentu.mika.model.result.Result;
import com.chentu.mika.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @description:处理与订单相关的API请求
 */
@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/orders")
	public Result add(@RequestBody Map map) {
		Order order = new Order();
		LocalDateTime time = DateTime.of((String)map.get("orderTime"),DatePattern.NORM_DATETIME_PATTERN).toLocalDateTime();
		order.setUserID((Integer) map.get("userID"));
		order.setProductCode((Integer) map.get("productID"));
		order.setProductPrice((Double)map.get("productPrice"));
		order.setCount((Integer)map.get("productCount"));
		order.setOrderTime(time);
		order.setOrderPay(0);
		orderService.save(order);
		return Result.success(null);
	}
	
	@GetMapping("/orders/order/{orderID}")
	public Result get(@PathVariable("orderID") Integer orderID) {
		QueryWrapper<Order> qw = new QueryWrapper<>();
		qw.eq("order_i_d", orderID);
		Map<String, Object> map = orderService.getMap(qw);
		/*String orderTimeUn = (String)map.get("order_time");
		*/
		LocalDateTime orderTime = (LocalDateTime) map.get("order_time");

		String[] splitted = orderTime.toString().split("T");
		StringJoiner sj = new StringJoiner(" ").add(splitted[0]).add(splitted[1]);
		DateTime time = DateTime.of(sj.toString(), DatePattern.NORM_DATETIME_PATTERN);
		Order order = new Order();
		BigDecimal productPrice = new BigDecimal(map.get("product_price").toString());
		order.setOrderID((Integer) map.get("order_i_d"));
		order.setUserID((Integer) map.get("user_i_d"));
		order.setProductCode((Integer) map.get("product_code"));
		order.setProductPrice(productPrice.doubleValue());
		order.setCount((Integer)map.get("count"));
		order.setOrderTime(orderTime);
		order.setOrderPay((Boolean)map.get("order_pay")?1:0);
		return Result.success(order);
	}
	
	@PutMapping("/order")
	public Result update(@RequestBody Map map) {
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("order_i_d",(Integer) map.get("orderID")).set("order_pay", true);
		/*Order order = orderService.getOne(Wrappers.<Order>lambdaQuery()
				.eq(Order::getOrderID, map.get("orderID")));
		order.setOrderPay((Integer)map.get("orderPay"));
		orderService.updateById(order);
		return Result.success(null);*/
		boolean result = orderService.update(updateWrapper); // 调用 update 方法
		if (result) {
			return Result.success("success");
		} else {
			return Result.fail("未查询到该订单");
		}
	}
	
	@GetMapping("/orders/{userID}")
	public Result list(@PathVariable("userID") Integer userID) {
		QueryWrapper<Order> qw = new QueryWrapper<>();
		qw.eq("user_i_d", userID);
		List<Order> list = orderService.list(qw);

		return Result.success(list);
	}
	
	@DeleteMapping("/orders/{userID}/{orderIDs}")
	public Result delete(@PathVariable("userID") Integer userID, @PathVariable("orderIDs") String orderIDs) {
		String[] split = orderIDs.split(",");
		for (String orderID : split) {
			orderService.remove(Wrappers.<Order>lambdaQuery()
					.in(Order::getOrderID, orderID)
			);
		}
		return Result.success(null);
	}
	
}