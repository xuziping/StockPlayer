package com.xuzp.stockplayer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author XuZiPing
 * @Date 2018/1/6
 * @Time 1:44
 */
@Component
@ConfigurationProperties(prefix="player")
public class PlayerProperties implements Serializable {

    private static final long serialVersionUID = -3226079156803876721L;

    /**
     * 用户名
     * 必填
     */
    private String userName;

    /**
     * 账户总现金
     * 必填
     */
    private BigDecimal cash;

    /**
     * 感兴趣的股票列表
     * 必填
     */
    private List<String> favoriteStocks;


    /**
     * 用户定义的自动交易规则
     */
    private List<String> rules;

    /**
     * 是否使用历史模拟数据
     * 必填
     */
    private Boolean useSimulateData;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public List<String> getFavoriteStocks() {
        return favoriteStocks;
    }

    public void setFavoriteStocks(List<String> favoriteStocks) {
        this.favoriteStocks = favoriteStocks;
    }

    public Boolean getUseSimulateData() {
        return useSimulateData;
    }

    public void setUseSimulateData(Boolean useSimulateData) {
        this.useSimulateData = useSimulateData;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
