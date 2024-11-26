package com.chentu.mika.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 */
@Data
@TableName("tb_cat")
public class Cat implements Serializable {
	
	Integer catID;
	
	String catName;
	
	String catImage;
	
	Integer catAge;
	
	String catBrief;
	
	Integer catSex;
	
	Integer catState;
}
