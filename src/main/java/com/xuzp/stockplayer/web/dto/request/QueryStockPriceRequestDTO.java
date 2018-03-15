package com.xuzp.stockplayer.web.dto.request;

import java.io.Serializable;

/**
 * @author za-xuzhiping
 * @Date 2018/1/11
 * @Time 21:26
 */
public class QueryStockPriceRequestDTO implements Serializable {

    private static final long serialVersionUID = -8656704683475181354L;

    private String stockCode;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Override
    public String toString(){
        return stockCode;
    }
}
