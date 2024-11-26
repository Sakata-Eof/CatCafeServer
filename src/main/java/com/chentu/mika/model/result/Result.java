package com.chentu.mika.model.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> Description:Result 的泛型类，用于在API响应中包装返回结果
 * <p>
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long total;
	private Integer code;
	
	private String msg;
	
	private T data;
	
	private Result(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	private Result(Status status, T data) {
		this.code = status.code();
		this.msg = status.msg();
		this.data = data;
	}
	
	private Result(Long total, T data) {
		this.code = Code.SUCCESS.code();
		this.msg = Code.SUCCESS.msg();
		this.total = total;
		this.data = data;
	}
	
	public static <T> Result<T> success(T data) {
		return new Result<>(Code.SUCCESS, data);
	}
	
	public static <T> Result<T> success(Long total, T data) {
		return new Result<>(total, data);
	}
	
	public static <T> Result<T> fail(String msg) {
		return new Result<>(0, msg, null);
	}
	
}
