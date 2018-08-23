package org.smart4j.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.smart4j.framework.helper.DatabaseHelper;

public class SmartJdbcRealm extends JdbcRealm{
	public SmartJdbcRealm(){
		super.setDataSource(DatabaseHelper.getDataSource());
		//TODO
	}

}
