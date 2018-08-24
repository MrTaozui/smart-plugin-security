package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类 通过类来实例化对象
 * Java反射机制主要提供了以下功能： 在运行时判断任意一个对象所属的类；
 * 在运行时构造任意一个类的对象；在运行时判断任意一个类所具有的成员变量和方法；在运行时调用任意一个对象的方法；生成动态代理。
 * @author tjj .
 */
public final class ReflectionUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(ReflectionUtil.class);
    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance=cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }
    /**
     * 创建实例
     */
    public static Object newInstance(String className){
        Object instance;
        try {
        	Class cls=Class.forName(className);//调用者的类加载器   谁加载这个类Class 就是使用哪个类加载器
            // 是sun.misc.Launcher$AppClassLoader 应用类加载器
            instance=cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }
    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method,Object...args){//args 传入的 是Param 对象 
        Object result;
        method.setAccessible(true);//要获访问私有的变量方法名private,故必须进行此操作
        try {
            result=method.invoke(obj,args);//第一个参数为类的实例，第二个参数为相应函数中的参数
        } catch (Exception e) {
            LOGGER.error("invoke method failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }

}
