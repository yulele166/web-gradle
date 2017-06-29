package com.zexi.utils;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) { 
		//checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		Map maps = applicationContext.getBeansOfType(clazz);
		Iterator<String> its = maps.keySet().iterator();
		Object obj = null;
		while (its.hasNext()) {
			obj = maps.get(its.next());
		}
		obj.getClass();
		return (T) obj;
		// return (T) applicationContext.getBeansOfType(clazz);
	}

	private static void checkApplicationContext() {
		Assert.notNull(applicationContext, "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
	}

}