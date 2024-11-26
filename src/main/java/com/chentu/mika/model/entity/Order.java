package com.chentu.mika.model.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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

	LocalDateTime orderTime;
	
	Integer orderPay;
}
