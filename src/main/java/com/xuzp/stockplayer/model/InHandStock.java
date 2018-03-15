package com.xuzp.stockplayer.model;

import java.io.Serializable;

/**
 * @author XuZiPing
 * @Date 2018/1/7
 * @Time 2:15
 */
public class InHandStock implements Serializable {

    private static final long serialVersionUID = -860383399986851662L;

    private String stockCode;

    private long amount;

    public InHandStock() {

    }

    public InHandStock(String stockCode, long amount) {
        this.stockCode = stockCode;
        this.amount = amount;
    }

    public InHandStock(InHandStock old) {
        this.setAmount(old.amount);
        this.setStockCode(old.stockCode);
    }

    public InHandStock(BusinessDeal deal) {
        this.setAmount(deal.getAmount());
        this.setStockCode(deal.getStockCode());
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }


}
