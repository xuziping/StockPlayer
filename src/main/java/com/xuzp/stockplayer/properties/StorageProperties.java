package com.xuzp.stockplayer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author za-xuzhiping
 * @Date 2018/1/9
 * @Time 21:29
 */
@Component
@ConfigurationProperties(prefix="storage")
public class StorageProperties implements Serializable {

    private static final long serialVersionUID = 6371218028927144598L;

    /**
     * 保存执行结果的输出文件路径
     * 必填
     */
    private String path;

    /**
     * 是否保存结果为便于阅读的JSON格式
     * 必填
     */
    private Boolean saveAsJSON = Boolean.TRUE;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getSaveAsJSON() {
        return saveAsJSON;
    }

    public void setSaveAsJSON(Boolean saveAsJSON) {
        this.saveAsJSON = saveAsJSON;
    }
}
