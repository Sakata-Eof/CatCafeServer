package com.chentu.mika.model.result;

/**
 * <p> Description:
 * <p>
 **/
public enum Code implements Status {
	
	SUCCESS(1, "操作成功"),
	
	FAIL(0, "操作失败"),
	
	;
	
	Code(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private final Integer code;
	
	private final String msg;
	
	@Override
	public Integer code() {
		return this.code;
	}
	
	@Override
	public String msg() {
		return this.msg;
	}
}
