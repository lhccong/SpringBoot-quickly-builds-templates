package com.cong.springbootinit.utils;

import com.google.gson.Gson;

import java.util.Map;

/**
 * 转换类型工具类
 * @author lhc
 * @date 2022-09-20 08:46
 */
public class JsonToMap<T> {
    /**
     * 对象转成map
     * @param obj
     * @return
     */
    public static Map<String,Object> objectToMap(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        Map map = gson.fromJson(json, Map.class);
        return map;
    }
}
