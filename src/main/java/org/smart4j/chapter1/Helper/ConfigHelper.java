package org.smart4j.chapter1.Helper;

import org.smart4j.chapter1.Contant.ConfigContant;
import org.smart4j.chapter1.Util.ProsUtil;

import java.util.Properties;

/**
 * Created by Administrator on 2017/5/31.
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = ProsUtil.loadProps(ConfigContant.CONFIG_FILE);

    public  static  String getJdbcDriver(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.JDBC_DRIVER,"");
    }
    public  static  String getJdbcuRL(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.JDBC_URL,"");
    }
    public  static  String getJdbcUserName(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.JDBC_USERNAME,"");
    }
    public  static  String getJdbcPassword(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.JDBC_PASSWORD,"");
    }
    public  static  String getAppBasePackage(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.APP_BASE_PACKAGE,"");
    }
    public  static  String getAppJspPath(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.APP_JSP_PATH,"/WEB-INF/view/");
    }
    public  static  String getAppAssetPath(){
        return ProsUtil.getString(CONFIG_PROPS,ConfigContant.APP_ASSET_PATH,"/asset/");
    }
}
