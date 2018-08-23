package org.smart4j.plugin.security;

/**
 * 提供相关配置项常量
 * @author tjj .
 */
public interface ConfigConstant {
   /* 接口只是对一类事物的属性和行为更高层次的抽象。对修改关闭,对扩展开放
   你认为是要变化的东西，就放在你自己的实现中，不能放在接口中去，
    接口只是对一类事物的属性和行为更高层次的抽象。对修改关闭，对扩展
    （不同的实现implements）开放，接口是对开闭原则的一种体现。 所以成员都是public static*/
    String CONFIG_FILE="smart.properties";

    String JDBC_DRIVER="smart.framework.jdbc.driver";
    String JDBC_URL="smart.framework.jdbc.url";
    String JDBC_USERNAME="smart.framework.jdbc.username";
    String JDBC_PASSWORD="smart.framework.jdbc.password";

    String APP_BASE_PACKAGE="org.smart4j.chapter3";
    String APP_JSP_PATH="/WEB-INF/view/";
    String APP_ASSET_PATH="/asset/";

    String APP_UPLOAD_LIMIT="smart.framework.app.upload_limit";
}
