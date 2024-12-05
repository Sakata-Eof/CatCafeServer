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
import java.util.*;

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

		OrderRequest order = new OrderRequest();
		BigDecimal productPrice = new BigDecimal(map.get("product_price").toString());
		order.setOrderID((Integer) map.get("order_i_d"));
		order.setUserID((Integer) map.get("user_i_d"));
		order.setProductID((Integer) map.get("product_code"));
		order.setProductPrice(productPrice.doubleValue());
		order.setProductCount((Integer)map.get("count"));
		order.setOrderTime(orderTime);
		order.setOrderPay((Boolean)map.get("order_pay"));
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
		//获取userID所对应的order，包装成OrderRequest的list发送
		List<OrderRequest> orders = new ArrayList<>();
		for (Order order : orderService.list()) {
			if(order.getUserID().equals(userID)) {
				OrderRequest temp = new OrderRequest();
				temp.setOrderID(order.getOrderID());
				temp.setUserID(order.getUserID());
				temp.setProductID(order.getProductCode());
				temp.setProductPrice(order.getProductPrice());
				temp.setOrderTime(order.getOrderTime());
				temp.setOrderPay(order.getOrderPay() == 1);
				temp.setProductCount(order.getCount());
				orders.add(temp);
			}
		}
		return Result.success(orders);
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

class OrderRequest{
	private Integer orderID;
	private Integer userID;
	private Integer productID;
	private double productPrice;
	private Integer productCount;
	private LocalDateTime orderTime;
	private boolean orderPay;

	public boolean isOrderPay() {
		return orderPay;
	}

	public void setOrderPay(boolean orderPay) {
		this.orderPay = orderPay;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
}