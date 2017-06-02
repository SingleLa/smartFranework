package org.smart4j.chapter1.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/5/31.
 */
public class ReflectionUtil {

    private  static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);
    /**
     * 创建bean实例
     */
    public static Object newInstancce(Class<?> cls){
        Object instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error("new instance fail"+e);
            e.printStackTrace();
        }
        return  instance;
    }
    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method,Object... args){
        Object result = null;
        method.setAccessible(true);
        try {
            result = method.invoke(obj,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  result;
    }
    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field,Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
