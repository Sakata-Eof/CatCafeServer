package com.chentu.mika.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**

 * @description:
 */
@Data
@TableName("tb_order")
public class Order implements Serializable {
	
	Integer orderID;
	
	Integer userID;
	
	Integer productCode;
	
	Double productPrice;
	
	Integer count;
	Date orderTime;
	
	Integer orderPay;
}
