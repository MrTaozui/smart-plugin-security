package org.smart4j.plugin.security;
/**
 * 常量接口
 * @author taojiajun
 *
 */
public interface SecurityConstant {
	String REALMS="smart.plugin.security.realms";
	String REALMS_JDBC="jdbc";
	String REALMS_CUSTOM="custom";
	
	String SMART_SECURITY="smart.plugin.security.cutom.class";
	
	String JDBC_ANTHC_QUERY="smart.plugin.security.jdbc.authc_query";
	String JDBC_ROLES_QUERY="smart.plugin.security.jdbc.roles_query";
	String JDBC_PERMISSIONS_QUERY="smart.plugin.security.jdbc.permissions_query";
	
	String CACHE="smart.plugin.security.cache";
	String CACHEABLE="true";
	
	String CONFIG_FILE="smart.properties";
	

}
