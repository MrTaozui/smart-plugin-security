package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author tjj .
 * 字符串工具类
 */
public final class StringUtil {
    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str){
        if (str!=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    /**
     * 字符串分隔符
     */
    public static final String SEPARATOR=String.valueOf((char)29);
    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    /**
     * 切割字符串
     */
    public static String[] splitString(String str,String regex){
    	return str.split(regex);
    }
}
