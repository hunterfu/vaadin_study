package com.vaadin.demo.vspring;

import javax.servlet.ServletContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public class SpringContextHelper {

	private static ApplicationContext context;

	public SpringContextHelper(ServletContext servletContext) {
		context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
	}

	public Object getBean(final String beanRef) {
		return context.getBean(beanRef);
	}

	public static void Inject(Object object) {

		AutowireCapableBeanFactory beanFactory = context
				.getAutowireCapableBeanFactory();
		beanFactory.autowireBeanProperties(object,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

	}
}