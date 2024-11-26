package com.chentu.mika.content;

/**
 * <p> Description:存储和管理线程本地变量
 * <p>
 **/
public class BaseContent {
	
	public static ThreadLocal<String> local = new ThreadLocal<>();
	
	public static String getId() {
		return local.get();
	}
	
	public static void setId(String id) {
		local.set(id);
	}
	
	public static void removeId() {
		local.remove();
	}
}
