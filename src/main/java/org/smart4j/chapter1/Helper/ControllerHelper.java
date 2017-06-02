package org.smart4j.chapter1.Helper;

import org.smart4j.chapter1.Annotation.Action;
import org.smart4j.chapter1.Annotation.Controller;
import org.smart4j.chapter1.Model.Handler;
import org.smart4j.chapter1.Model.Request;
import org.smart4j.chapter1.Util.ArrayUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 加载controller注解 标识 的类 控制器助手类
 */
public class ControllerHelper {

    /**
     * 用于存放请求与处理器的映射关系（简称Action Map）
     *
     */
    private  static final Map<Request,Handler> ACTION_MAP = new HashMap<>();
    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(controllerClassSet.size() != 0){
            //遍历这些congtroller类  获取类中定义的方法
            for (Class<?> controllerClass:controllerClassSet
                 ) {
                //获取定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(methods)){
                    //遍历当前controller类
                    for (Method method:methods
                         ) {
                        //判断当前方法是否带有action注解
                        if(method.isAnnotationPresent(Action.class)){
                            //从action中获取url映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array)&&array.length == 2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(Controller.class,method);
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 获取handler
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(requestMethod);
    }
}
