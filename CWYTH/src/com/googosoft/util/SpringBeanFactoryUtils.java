package com.googosoft.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanFactoryUtils implements ApplicationContextAware{
	
	private static ApplicationContext appCtx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 appCtx = applicationContext;
	}
	/**
	 * 获取applicationcontext对象
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
        return appCtx;
    }
	/**
	 * 返回相应的spring管理的bean对象
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
	    return appCtx.getBean(beanName);
	}

}
