package com.chentu.mika.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**

 * @description:
 */
@Data
@TableName("tb_order")
public class Order implements Serializable {
	
	Integer orderId;
	
	Integer userId;
	
	Integer productCode;
	
	Double productPrice;
	
	Integer count;
	Date orderTime;
	
	Integer orderPay;
}
