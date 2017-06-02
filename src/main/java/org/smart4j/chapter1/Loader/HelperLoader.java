package org.smart4j.chapter1.Loader;

import org.smart4j.chapter1.Helper.BeanHelper;
import org.smart4j.chapter1.Helper.ClassHelper;
import org.smart4j.chapter1.Helper.ControllerHelper;
import org.smart4j.chapter1.Helper.IocHelper;
import org.smart4j.chapter1.Util.ClassUtil;

/**
 * Created by Administrator on 2017/6/1.
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls:classList
             ) {
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}
