package com.xuzp.stockplayer.operate.impl;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.StockPlayerApplicationTests;
import com.xuzp.stockplayer.common.StockUtils;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.*;
import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.rule.StockRule;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author za-xuzhiping
 * @Date 2018/1/12
 * @Time 22:06
 */
public class BuyStockOperateImplTest extends StockPlayerApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(BuyStockOperateImplTest.class);

    @Autowired
    private BuyStockOperateImpl buyStockOperate;

    @Test
    public void process() throws Exception {
        Stock ruleStock = new Stock("SH600000",BigDecimal.valueOf(19.9), new Date());
        StockRule<IStock,IStockOperate> rule = new StockRule(ruleStock, buyStockOperate);
        Player player = new Player("", BigDecimal.valueOf(100000), Lists.newArrayList("SH600000"),
                Lists.newArrayList(), Sets.newHashSet(), Boolean.TRUE, Lists.newArrayList(rule),null);
        Stock stock = new Stock("SH600000", BigDecimal.valueOf(19.812), new Date());
        ResultBase<BusinessDeal>  deal = buyStockOperate.process(player, stock, ruleStock);
        log.info(JSON.toJSONString(deal));
        log.info(JSON.toJSONString(player));
    }

    @Test
    public void maintainInHandStocks() throws Exception {
        Set<InHandStock> hands = Sets.newHashSet();
        hands.add(new InHandStock("SH600000",  500));
        hands.add(new InHandStock("SH600001",  700));
        hands.add(new InHandStock("SH600002",  10000));
        hands.add(new InHandStock("SH600000",  300));
        BusinessDeal deal = new BusinessDeal("SH600000",  Long.valueOf(100), BigDecimal.valueOf(13.8), new Date());
        Set<InHandStock> newOne = StockUtils.maintainInHandStocks(hands, deal);
        log.info("OLD={}", JSON.toJSONString(hands));
        log.info("NEW={}", JSON.toJSONString(newOne));

    }

}