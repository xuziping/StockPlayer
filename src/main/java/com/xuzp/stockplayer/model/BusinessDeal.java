package com.xuzp.stockplayer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author XuZiPing
 * @Date 2018/1/7
 * @Time 2:06
 */
public class BusinessDeal implements Serializable {

    private static final long serialVersionUID = -8205448741927167978L;

    private String stockCode;

    private Long amount;

    private BigDecimal price;

    private Date timestamp;

    public BusinessDeal() {

    }

    public BusinessDeal(BusinessDeal deal) {
        this.stockCode = deal.stockCode;
        this.amount = new Long(deal.amount);
        this.price = new BigDecimal(deal.price.toString());
        this.timestamp = deal.timestamp;
    }

    public BusinessDeal(String stockCode, Long amount, BigDecimal price, Date timestamp) {
        this.stockCode = stockCode;
        this.amount = amount;
        this.price = price;
        this.timestamp = timestamp;
    }

    public BusinessDeal(IStock stockCode, long amount) {
        this.stockCode = stockCode.getCode();
        this.amount = Long.valueOf(amount);
        this.price = stockCode.getCurrentPrice();
        this.timestamp = stockCode.getTimestmap();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
