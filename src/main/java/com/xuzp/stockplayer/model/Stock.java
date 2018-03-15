package com.xuzp.stockplayer.model;

import com.google.gson.InstanceCreator;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.ValidateUtils;
import com.xuzp.stockplayer.nlp.AdaptedLevelEnum;
import com.xuzp.stockplayer.nlp.INLPTarget;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author XuZiPing
 * @Date 2018/1/5
 * @Time 22:59
 */
//@Component
//@Scope("prototype")
public class Stock implements IStock, Serializable, INLPTarget {

    private static final long serialVersionUID = 4604046847261677908L;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 股票码
     */
    private String code;

    /**
     * 时间区间内最高价
     */
    private BigDecimal highPrice;

    /**
     * 时间区间内最低价
     */
    private BigDecimal lowPrice;

    /**
     * 时间区间内开始价格
     */
    private BigDecimal openPrice;

    /**
     * 时间区间内结束价格
     */
    private BigDecimal closePrice;

    /**
     * 昨日价格
     */
    private BigDecimal yesterday;

    /**
     * 当前价格
     */
    private BigDecimal currentPrice;


    private BigDecimal number;

    /**
     * 成交量
     */
    private BigDecimal amount;

    /**
     * 更新时间
     */
    private Date timestamp;

    public Stock() {

    }

    public Stock(String code, BigDecimal currentPrice, Date date) {
        this.code = code;
        this.timestamp = date;
        this.currentPrice = currentPrice;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getYesterday() {
        return yesterday;
    }

    public void setYesterday(BigDecimal yesterday) {
        this.yesterday = yesterday;
    }

    @Override
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    @Override
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    @Override
    public BigDecimal getClosePrice() {
        return closePrice;
    }

    @Override
    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    @Override
    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    @Override
    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public BigDecimal getHighPrice() {
        return highPrice;
    }

    @Override
    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public Date getTimestmap() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Date ts) {
        this.timestamp = ts;
    }


    @Override
    public boolean equals(Object stock){
        if(StringUtils.isEmpty(code)){
            return false;
        }
        if(!(stock instanceof Stock)) {
            return false;
        }
        if(this==stock){
            return true;
        }
        if (code.equalsIgnoreCase(((Stock) stock).getCode())){
            return true;
        }
        return false;
    }
}
