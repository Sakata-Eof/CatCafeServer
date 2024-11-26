package com.chentu.mika.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:CatForm 的表单类，用于接收和传递猫的相关数据
 */
@Data
public class CatForm implements Serializable {
	
	Integer catId;
	
	String catName;
	
	String catImage;
	
	Integer catAge;
	
	String catBrief;
	
	Boolean catSex;
	
	Boolean catState;
}
