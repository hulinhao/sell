package com.imooc.utils;

import com.google.gson.Gson;

public class JsonUtil {

    public static String tiJson(Object obj){
        Gson gson  = new Gson();
        return gson.toJson(obj);
    }
}
