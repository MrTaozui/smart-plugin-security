package org.smart4j.plugin.security;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
/**
 * 安全过滤器
 * @author taojiajun
 *
 */
public class SmartSecurityFilter extends ShiroFilter{

	@Override
	public void init() throws Exception {
		super.init();
		WebSecurityManager webSecurityManager=super.getSecurityManager();
		//设置Realm 可以同时支持多个Realm 并按照先后顺序用逗号分离
	
	}
	private void setRealms(WebSecurityManager webSecurityManager){
		//读取smart.plugin.security.realms配置项
		String securityRealms=SecurityConfig.getRealms();
		if(securityRealms!=null){
			//根据逗号进行拆分
			String[] securityRealmArray=securityRealms.split(",");
			if(securityRealmArray.length>0){
				//使Realm具备唯一性与顺序性
				//TODO page246
			}
		}
	}
}
