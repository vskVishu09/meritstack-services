package com.example.meritstack.util;

public class StringUtils {

    public static String[] splitBySpace(String strInput) {
        String result [] = null;
        if(strInput != null) {
            result = strInput.trim().split("\\s+");
        }
        return  result;
    }
}
