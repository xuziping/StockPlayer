package com.xuzp.stockplayer.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XuZiPing
 * @Date 2018/1/11
 * @Time 0:44
 */
public class ValidateUtils {

    private static final Logger log = LoggerFactory.getLogger(ValidateUtils.class);

    /** 正整数 */
    private static final String V_Z_INDEX           = "^[1-9]\\d*$";

    /** 正数 */
    private static final String V_POSITIVE_NUMBER   = "^[1-9]\\d*|0$";

    /** 负数 */
    private static final String V_NEGATINE_NUMBER   = "^-[1-9]\\d*|0$";

    /** 浮点数 */
    private static final String V_FLOAT             = "^([+-]?)\\d*\\.\\d+$";

    /** 带0结尾的浮点数 */
    private static final String V_FLOAT_ZERO_TAIL   = "^([+-]?)\\d*\\.0+$";

    /** 正浮点数 */
    private static final String V_POSTTIVE_FLOAT    = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";

    /** 负浮点数 */
    private static final String V_NEGATIVE_FLOAT    = "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$";

    /** 非负浮点数（正浮点数 + 0） */
    private static final String V_UNPOSITIVE_FLOAT  = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";

    /** 非正浮点数（负浮点数 + 0） */
    private static final String V_UN_NEGATIVE_FLOAT = "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";


    @SuppressWarnings("deprecation")
    public static boolean isValidInputDateFormat(String val) {
        try {
            Date date = Constants.DATE_FORMAT.parse(val);
            return date.before(new Date()) && date.after(new Date(2000,0,1));
        } catch (Exception e) {
            log.error("解析日期格式出错, {}", val != null ? val : "");
            return false;
        }
    }


    /**
     * 验证是不是正整数
     */
    public static boolean isPostiveNumber(String value) {
        return match(V_Z_INDEX, value);
    }

    /**
     * 验证是不是正浮点数
     */
    public static boolean isPostiveFloat(String value) {
        return match(V_POSTTIVE_FLOAT, value);
    }

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
