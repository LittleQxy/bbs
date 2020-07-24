package com.qxy.bbs.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.util.locale.provider.DateFormatProviderImpl;

import javax.xml.crypto.Data;
import java.text.*;
import java.text.spi.DateFormatProvider;
import java.util.Date;

/**
 * @author qixiangyang5
 * @create 2020/7/24 10:35
 */
@Slf4j
public class DateUtils {

    public static String changeDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        String dateString = simpleDateFormat.format(date);

        return dateString;
    }
}
