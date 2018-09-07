package com.futengwl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title: DateUtils
 * @package: com.futengwl.util
 * @description:
 * @author: hanbin
 * @date: 2018-06-07  12:08
 */
public class DateUtils {
    public static Date str2Date(String time, String formate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formate);
            return formatter.parse(time);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
