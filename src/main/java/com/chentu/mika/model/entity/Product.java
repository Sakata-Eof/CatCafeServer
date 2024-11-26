package com.chentu.mika.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 */
@Data
@TableName("tb_product")
public class Product implements Serializable {
	
	Integer productID;
	
	String productName;
	
	Double price;
	
	String productImage;
	
	String productBrief;
}
