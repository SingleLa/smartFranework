package org.smart4j.chapter1.Helper;




import org.apache.commons.collections4.CollectionUtils;
import org.smart4j.chapter1.Annotation.Inject;
import org.smart4j.chapter1.Util.ArrayUtil;
import org.smart4j.chapter1.Util.CollectionUtil;
import org.smart4j.chapter1.Util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
public class IocHelper {
    static {
        //获取所有的bean类与bean实例件的映射关系（简称bean map）
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(beanMap != null){
            for (Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()
                 ) {
                //从beanMap中获取bean类与bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取bean定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(beanFields)){
                    for (Field beanFile:beanFields
                         ) {
                        //判断当前bean filed是否带有Inject注解
                        if(beanFile.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = beanFile.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if(beanFieldInstance != null){
                                //通过反射初始化beanField的值
                                // ---------------------类--------对象-成员 属性 变量 ------值
                                ReflectionUtil.setField(beanInstance,beanFile,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
