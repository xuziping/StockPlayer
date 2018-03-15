package com.xuzp.stockplayer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author za-xuzhiping
 * @Date 2018/1/9
 * @Time 21:32
 */
@Component
@ConfigurationProperties(prefix="simulate-data")
public class SimulateDataProperties implements Serializable{

    private static final long serialVersionUID = -7441534891419100537L;

    /**
     * 股票5分钟历史信息文件夹
     * 必填
     */
    private String path;

    /**
     * 模拟的开始历史时间，格式如 20170601
     * 必填
     */
    private String startDate;

    /**
     * 模拟的结束历史时间，格式如 20171220
     * 必填
     */
    private String endDate;

    /**
     * 自动刷新价格
     * 必填
     */
    private Boolean autoRefresh;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(Boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }
}
