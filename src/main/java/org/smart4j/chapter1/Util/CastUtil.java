package org.smart4j.chapter1.Util;

import org.apache.commons.lang3.StringUtils;

/**
 * 类型转换工具类
 */
public final class CastUtil {
    /**
     * 转为String
     */
    public static String castString(Object object){
        return  castString(object,"");
    }
    public static String castString(Object object,String defaultValue){
        return  object != null ? String.valueOf(object) : defaultValue;
    }


    /**
     * double
     */
    public  static  double castDouble(Object obj,double defaultValue){
        double value = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isEmpty(strValue)){
                try {
                    value = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    value = defaultValue;
                }

            }
        }
        return  value;
    }
    /**
     * long
     */
    public  static  long castLong(Object obj,long defaulrValue){
        long value = defaulrValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isEmpty(strValue)){
                try {
                    value = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    value = defaulrValue;
                }

            }
        }
        return  value;
    }
    /**
     * int
     */
    public  static  int castInt(Object obj,int defaulrValue){
        int value = defaulrValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isEmpty(strValue)){
                try {
                    value = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    value = defaulrValue;
                }

            }
        }
        return  value;
    }
}
