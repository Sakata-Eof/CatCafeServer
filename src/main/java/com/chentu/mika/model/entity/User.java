package com.chentu.mika.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:
 */
@Data
@TableName("tb_user")
public class User implements Serializable {
	@TableId(value = "user_i_d", type = IdType.AUTO)
	Integer userID;
	String userName;
	String userPassword;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime registerTime;
	Boolean userType;
	Boolean userSex;
	String userEmail;
}
