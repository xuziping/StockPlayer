package com.xuzp.stockplayer.common.result;

import java.io.Serializable;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 22:02
 */
public class ResultBase<T> implements Serializable {

    private static final long serialVersionUID = -3198558262509454066L;

    private boolean isSuccess = false;

    private String code;

    private String message;

    private T value;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
