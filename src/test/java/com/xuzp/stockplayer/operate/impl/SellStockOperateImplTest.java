package com.xuzp.stockplayer.operate.impl;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.StockPlayerApplicationTests;
import com.xuzp.stockplayer.common.Constants;
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
import java.util.List;
import java.util.Set;

/**
 * @author za-xuzhiping
 * @Date 2018/1/12
 * @Time 22:07
 */
public class SellStockOperateImplTest extends StockPlayerApplicationTests{

    private static final Logger log = LoggerFactory.getLogger(SellStockOperateImpl.class);

    @Autowired
    private SellStockOperateImpl sellStockOperate;

    @Test
    public void process() throws Exception {

        List<BusinessDeal> deals = Lists.newArrayList();
        deals.add(new BusinessDeal("SH600000",  100L, BigDecimal.valueOf(17.5),
                Constants.DATE_FORMAT.parse("20180111")));
        deals.add(new BusinessDeal("SH600000",  100L, BigDecimal.valueOf(19.5),
                Constants.DATE_FORMAT.parse("20180101")));
        deals.add(new BusinessDeal("SH600000",  100L, BigDecimal.valueOf(14.5),
                Constants.DATE_FORMAT.parse("20180112")));

        Set<InHandStock> inHandStocks = Sets.newHashSet();
        for(BusinessDeal deal: deals) {
            inHandStocks = StockUtils.maintainInHandStocks(inHandStocks, deal);
        }
        Stock ruleStock = new Stock("SH600000",BigDecimal.valueOf(19.8), new Date());
        StockRule<IStock,IStockOperate> rule = new StockRule(ruleStock, sellStockOperate);
        Player player = new Player("", BigDecimal.valueOf(100000), Lists.newArrayList("SH600000"),
                deals, inHandStocks, Boolean.TRUE, Lists.newArrayList(rule),null);
        Stock stock = new Stock("SH600000", BigDecimal.valueOf(19.812), new Date());
        ResultBase<BusinessDeal> deal = sellStockOperate.process(player, stock, ruleStock);
        log.info(JSON.toJSONString(deal));
        log.info(JSON.toJSONString(player));
    }

}