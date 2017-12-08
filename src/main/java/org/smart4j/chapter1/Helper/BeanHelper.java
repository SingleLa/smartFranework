package org.smart4j.chapter1.Helper;

import org.smart4j.chapter1.Util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/1.
 */
public class BeanHelper {
    /**
     * 定义bean映射（用于存放bean类与bean实例的映射关系）
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanrClassSet();
        for (Class<?> beanClass:beanClassSet
             ) {
            Object obj = ReflectionUtil.newInstancce(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }
    /**
     * 获取bean的映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }
    /**
     * 获取bean实例
     */
    public static <T>T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean "+ cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
