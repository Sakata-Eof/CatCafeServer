package com.chentu.mika.model.form;

import lombok.Data;

import java.io.Serializable;

/**

 * @description:LoginForm 的表单类，用于接收和传递用户登录相关的数据
 */
@Data
public class LoginForm implements Serializable {
	
	Integer userId;
	String userName;
	String userEmail;
	String userPassword;
}
