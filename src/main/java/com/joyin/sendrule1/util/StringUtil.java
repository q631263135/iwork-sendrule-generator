package com.joyin.sendrule1.util;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

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

}
