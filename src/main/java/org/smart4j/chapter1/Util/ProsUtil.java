package org.smart4j.chapter1.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by Administrator on 2017/5/31.
 */
public final class ProsUtil {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ProsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            try {
                if (is == null)
                    throw  new FileNotFoundException(fileName+"file is not find");
                props = new Properties();
                props.load(is);
                return  props;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return  props;
    }
    /**
     * 获取字符型属性（默认值为空字符串）
     */
    public  static  String geString(Properties props,String key){
        return getString(props,key,"");
    }

    /**
     * 获取字符型属性
     */
    public static String getString(Properties props,String key,String defaultValue){
        String value = defaultValue;
        if(props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }
    /**
     * 获取数值型
     */
    public  static  int getInt(Properties props,String key,int defaultValue){
        int value = defaultValue;
        if(props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key),0);
        }
        return value;
    }
}
