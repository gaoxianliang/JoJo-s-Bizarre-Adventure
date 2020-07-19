package com.phantomblood.jojo.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastjson
 * */
//@Configuration
public class JsonUtils {

    /**
     * 返回带null键值的json字符串
     *
     * @author liujingui
     * @param o
     * @return String
     */
    public static String getJSON(Object o){
        return JSONObject.toJSONString(o, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 返回带日期,并格式化的json字符串
     *
     * @author liujingui
     * @param o
     * @return String
     */
    public static String getJSONWithDates(Object o){
        return JSONObject.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 返回带日期,并格式化的json字符串
     *
     * @author liujingui
     * @param o
     * @return String
     */
    public static String getJSONWithDate(Object o){
        return JSONObject.toJSONStringWithDateFormat(o, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 返回son字符串
     *
     * @author liujingui
     * @param o
     * @return String
     */
    public static String toJson(Object o){
        return JSONObject.toJSONString(o);
    }
}
