package com.chentu.mika.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.jwt.JWT;
import com.chentu.mika.content.BaseContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p> Description:JWTInterceptor 的拦截器类
 * <p>
 **/
@Component
public class JWTInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		if (request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
			return true;
		}
		try {
			String token = request.getHeader("Authorization").substring(7);
			Object claim = JWT.of(token).getPayload().getClaim("sub");
			if (claim == null) {
				return true;
			}
			Long id = Convert.toLong(claim);
			BaseContent.setId(id.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		BaseContent.removeId();
	}
}
