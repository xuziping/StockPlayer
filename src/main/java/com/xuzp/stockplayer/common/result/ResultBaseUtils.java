package com.xuzp.stockplayer.common.result;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 22:17
 */
public class ResultBaseUtils {

    public static <R> ResultBase<R> fail(String code, String... msg) {
        ResultBase<R> biz = new ResultBase<>();
        biz.setSuccess(false);
        biz.setCode(code);
        if (msg != null && msg.length > 0) {
            biz.setMessage(msg[0]);
        }
        return biz;
    }

    public static <R> ResultBase<R> success(){
        return success(null);
    }

    public static <R> ResultBase<R> success(String code, String... msg) {
        return success(code, null, msg);
    }

    public static <R> ResultBase<R> success(String code, R value, String... msg) {
        ResultBase<R> biz = new ResultBase<>();
        biz.setSuccess(true);
        biz.setCode(code);
        biz.setValue(value);
        if (msg != null && msg.length > 0) {
            biz.setMessage(msg[0]);
        }
        return biz;
    }
}
