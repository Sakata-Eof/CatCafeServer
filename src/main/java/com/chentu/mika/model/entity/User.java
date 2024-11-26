package com.chentu.mika.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @description:
 */
@Data
@TableName("tb_user")
public class User implements Serializable {
	@TableId(value = "user_id", type = IdType.AUTO)
	Integer userId;
	String userName;
	String userPassword;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime registerTime;
	Boolean userType;
	Boolean userSex;
	String userEmail;
}
