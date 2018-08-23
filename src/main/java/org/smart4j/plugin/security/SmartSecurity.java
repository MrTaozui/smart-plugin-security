package org.smart4j.plugin.security;

import java.util.Set;

/**
 * Smart Security 接口
 * 可在应用中实现该接口，或者在smart.properties文件中提供以下基于SQL的配置项：
 * smart.plugin.security.jdbc.authc_query:根据用户名获取密码
 * smart.plugin.security.jdbc.roles_query:根据用户名获取角色集合
 * smart.plugin.security.jdbc.permission_query:根据用户名获取权限名集合
 * @author taojiajun
 *
 */
public interface SmartSecurity {
	/**
	 * 根据用户名获取密码
	 */
	String getPassword(String username);
	/**
	 * 根据用户名获取角色名集合
	 */
	Set<String> getRoleNameSet(String username);
	/**
	 * 根据角色名获取权限名集合
	 */
	Set<String> getPermissionSet(String roleName);

}
