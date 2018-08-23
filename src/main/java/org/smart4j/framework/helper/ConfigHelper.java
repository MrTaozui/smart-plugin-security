package org.smart4j.framework.helper;



import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.PropsUtil;
import org.smart4j.plugin.security.ConfigConstant;
import org.smart4j.plugin.security.SecurityConstant;

import java.util.Properties;

/**
 * 属性文件助手类
 * @author tjj .
 */
public final class ConfigHelper {
    private static final Properties SHIRO_CONFIG_PROPS= PropsUtil.loadProps(SecurityConstant.CONFIG_FILE);
    
    public static String getString(String key){
    	return PropsUtil.getString(SHIRO_CONFIG_PROPS, key);
    }
    
    public static boolean getBoolean(String key){
    	return CastUtil.castBoolean(PropsUtil.getString(CONFIG_PROPS, key));
    }
    private static final Properties CONFIG_PROPS= PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取url
     */
    public static String getJdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }
    /**
     * 获取JDBC用户名
     */
    public static String getJdbcUsername(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }
    /**
     * 获取JDBC密码
     */
    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }
    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE);
    }
    /**
     * 获取应用JSP路径
     */
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }
    /**
     * 获取应用静态资源路径
     */
    public static String getAppAssertPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH,"/asset/");

    }
    /**
     * 获取驱动
     * @return
     */
    public static String getJDBCDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);

    }
    /**
     * 获取应用文件上传限制
     */
    public static int getAppUploadLimit(){
        return PropsUtil.getInt(CONFIG_PROPS,ConfigConstant.APP_UPLOAD_LIMIT,10);
    }
    
}
