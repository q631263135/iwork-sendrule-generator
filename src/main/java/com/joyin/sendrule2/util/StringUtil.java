package com.joyin.sendrule2.util;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/10/14 11:28
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null ? str.equals("") : false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
