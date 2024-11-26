package com.chentu.mika.common.exception;

/**
 * <p> Description: 缓存异常
 * <p>
 **/
public class CacheException extends RuntimeException{
	
	public CacheException(String message) {
		super(message);
	}
	
	public CacheException() {
		super();
	}
}
