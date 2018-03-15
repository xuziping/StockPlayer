package com.xuzp.stockplayer.model;

import com.xuzp.stockplayer.nlp.INLPAdapter;
import com.xuzp.stockplayer.nlp.INLPTarget;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author XuZiPing
 * @Date 2018/1/7
 * @Time 16:28
 */
public interface IStock  {

    void setName(String name);

    String getName();

    void setCode(String code);

    String getCode();

    BigDecimal getCurrentPrice();

    void setCurrentPrice(BigDecimal currentPrice);

    BigDecimal getOpenPrice();

    void setOpenPrice(BigDecimal openPrice);

    BigDecimal getClosePrice();

    void setClosePrice(BigDecimal closePrice);

    BigDecimal getLowPrice();

    void setLowPrice(BigDecimal lowPrice);

    BigDecimal getHighPrice();

    void setHighPrice(BigDecimal highPrice);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    Date getTimestmap();

    void setTimestamp(Date ts);
}
