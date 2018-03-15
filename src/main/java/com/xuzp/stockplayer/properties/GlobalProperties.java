package com.xuzp.stockplayer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author za-xuzhiping
 * @Date 2018/1/11
 * @Time 22:29
 */
@Component
@ConfigurationProperties
public class GlobalProperties implements Serializable {

    private static final long serialVersionUID = 8668644296773060079L;

    /**
     * 自动买卖股票
     */
    private Boolean isAutoTransact;

    public Boolean getIsAutoTransact() {
        return isAutoTransact;
    }

    public void setIsAutoTransact(Boolean autoTransact) {
        isAutoTransact = autoTransact;
    }
}
