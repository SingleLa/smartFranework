package org.smart4j.chapter1.Controller;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.smart4j.chapter1.Contant.ConfigContant;
import org.smart4j.chapter1.Helper.BeanHelper;
import org.smart4j.chapter1.Helper.ConfigHelper;
import org.smart4j.chapter1.Helper.ControllerHelper;
import org.smart4j.chapter1.Loader.HelperLoader;
import org.smart4j.chapter1.Model.Data;
import org.smart4j.chapter1.Model.Handler;
import org.smart4j.chapter1.Model.Param;
import org.smart4j.chapter1.Model.View;
import org.smart4j.chapter1.Util.*;

import javax.lang.model.element.NestingKind;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关helper类
        HelperLoader.init();
        //获取ServletContext对象
        ServletContext servletContext = config.getServletContext();
        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler != null){
            //获取Controller类及bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashedMap();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtils.isNotEmpty(body)){
                String[] params = body.split("&");
                if(ArrayUtil.isNotEmpty(params)){
                    for (String param: params
                         ) {
                        String[] array = param.split("=");
                        if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            //处理anction方法返回值
            if(result instanceof View){
                View view  = (View) result;
                String path = view.getPath();
                if(StringUtils.isNotEmpty(path)){
                    resp.sendRedirect(req.getContextPath()+path);
                }else {
                    Map<String,Object> model = view.getModel();
                    for (Map.Entry<String,Object> entry:model.entrySet()
                         ) {
                        req.setAttribute(entry.getKey(),entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                }
            }else if (result instanceof Data){
                Data data = (Data)result;
                Object model =data.getModel();
                if(model!=null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJSON(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }

    }
}
