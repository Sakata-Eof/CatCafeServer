package com.chentu.mika.handle;

import com.chentu.mika.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p> Description: 全局异常处理
 * <p>
 **/
@RestControllerAdvice
@Slf4j
public class ExceptionHandle {
	
	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		log.error("Exception: {}", e.getMessage());
		return Result.fail(e.getMessage());
	}
}
