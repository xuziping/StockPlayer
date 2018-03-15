package com.xuzp.stockplayer.common.exception;

import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author XuZiPing
 * @Date 2018/1/11
 * @Time 0:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.OK) // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBase bizException(Exception e) {
        log.error("发生异常:{}", e);
        return ResultBaseUtils.fail(Constants.ERROR_CODE_GLOBAL_EXCEPTION, e.getMessage());
    }
}
