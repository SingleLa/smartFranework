package org.smart4j.chapter1.Helper;

import org.smart4j.chapter1.Annotation.Action;
import org.smart4j.chapter1.Annotation.Controller;
import org.smart4j.chapter1.Annotation.Inject;
import org.smart4j.chapter1.Annotation.Service;
import org.smart4j.chapter1.Util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/31.
 */
public class ClassHelper {
    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包下的所有类
     */
    public  static  Set<Class<?>> getClassSet(){
        return  CLASS_SET;
    }
    /**
     * 获取包下所有service类
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> cls = new HashSet<>();
        for (Class<?> c: CLASS_SET
             ) {
            if(c.isAnnotationPresent(Service.class))
                cls.add(c);
        }
        return  cls;
    }
    /**
     * 获取包下所有Controller类
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> cls = new HashSet<>();
        for (Class<?> c: CLASS_SET
                ) {
            if(c.isAnnotationPresent(Controller.class))
                cls.add(c);
        }
        return  cls;
    }

    /**
     * 获取bean类
     */
    public static Set<Class<?>> getBeanrClassSet(){
        Set<Class<?>> cls = new HashSet<>();
        cls.addAll(getServiceClassSet());
        cls.addAll(getControllerClassSet());
        return  cls;
    }
}
