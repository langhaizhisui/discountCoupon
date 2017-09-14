package com.iba.drp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextAware方式注册springContent，如果使用spring boot方式加载由于没有使用web.xml中加载ContextLoaderListener
 * 会导致ContextLoader.getCurrentWebApplicationContext()获取的为Null
 */

@Component
@Lazy(false)
public class SpringContentRegister implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContentRegister.class);
	private static ApplicationContext APPLICATION_CONTEXT;

	/**
	 * 设置spring上下文
	 * 
	 * @param applicationContext  spring上下文
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOGGER.debug("ApplicationContext registed-->{}", applicationContext);
		APPLICATION_CONTEXT = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return APPLICATION_CONTEXT;
	}

}
