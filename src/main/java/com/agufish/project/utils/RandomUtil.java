package com.agufish.project.utils;

import java.util.Random;

public class RandomUtil {
    public static String cardinality(){
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

}
