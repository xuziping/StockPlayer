package com.xuzp.stockplayer.rule;

import java.io.Serializable;

/**
 * @author za-xuzhiping
 * @Date 2018/1/16
 * @Time 22:39
 */
public class StockRule<T, O> implements Serializable{

    private static final long serialVersionUID = -5607683205951034523L;
    private T stock;

    private O operate;

    public StockRule(T stock, O  operate) {
        this.stock = stock;
        this.operate = operate;
    }

    public T getStock() {
        return stock;
    }

    public void setStock(T stock) {
        this.stock = stock;
    }

    public O getOperate() {
        return operate;
    }

    public void setOperate(O operate) {
        this.operate = operate;
    }
}
