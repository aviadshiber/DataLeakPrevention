package org.example.rules.utils;

import lombok.val;

import java.util.regex.Pattern;

public class RegexHelper {
    public final static String KEY = "regex";
    public static boolean isMatch(String pattern,String data){
        if(pattern== null) return false;
        val p = Pattern.compile(pattern);
        val matcher = p.matcher(data);
        return matcher.find();
    }
}
