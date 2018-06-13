package com.imooc.utils;

import java.util.Random;

public class KeyUtil {

    public synchronized static String genUniqueKey(){
        Random random = new Random();

        Integer a = random.nextInt(900000)+100000;
        return System.currentTimeMillis() + String.valueOf(a);
    }
}
