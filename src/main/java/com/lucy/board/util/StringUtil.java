package com.lucy.board.util;

import java.net.URLEncoder;

public class StringUtil {

    public static String urlEncoder(String str) {
        try {
            return URLEncoder.encode(str);
        } catch (Exception e) {
        }
        return str;
    }

}
