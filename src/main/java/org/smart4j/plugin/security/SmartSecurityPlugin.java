package org.smart4j.plugin.security;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

public class SmartSecurityPlugin implements ServletContainerInitializer{
	//通过ServletContext 注册Listener与Filter
	public void onStartup(Set<Class<?>> handlesTypes, ServletContext servletContext) throws ServletException {
		//设置初始化参数
		servletContext.setInitParameter("shiroConfigLocations", "classpath:smart-security.ini");
		//注册 Listener
		servletContext.addListener(EnvironmentLoaderListener.class);
		//注册Filter
		FilterRegistration.Dynamic smartSecurityFilter=servletContext.addFilter("SmartSecurityFilter",SmartSecurityFilter.class);
		smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
		
	}

}
