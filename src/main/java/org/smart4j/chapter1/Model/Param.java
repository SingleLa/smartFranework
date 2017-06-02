package org.smart4j.chapter1.Model;

import org.smart4j.chapter1.Util.CastUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    /**
     * 根据参数名获取long型参数
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name),0);
    }
    /**
     * 获取所有字段信息
     */
    public Map<String,Object> getMap(){
        return  paramMap;
    }

}
