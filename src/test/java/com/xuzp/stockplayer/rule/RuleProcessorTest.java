package com.xuzp.stockplayer.rule;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.StockPlayerApplicationTests;
import com.xuzp.stockplayer.common.JSONUtils;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.storage.FileStorage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author za-xuzhiping
 * @Date 2018/1/15
 * @Time 21:16
 */
public class RuleProcessorTest extends StockPlayerApplicationTests{

    private static final Logger log = LoggerFactory.getLogger(RuleProcessorTest.class);

    @Autowired
    private RuleProcessor ruleProcessor;

    @Autowired
    private FileStorage storage;

    @Test
    public void parse() throws Exception {

        StockRule<IStock, IStockOperate> result = ruleProcessor.parse("SH601318低于53.6元买入");
        log.info(JSON.toJSONString(result));

        result.getOperate().process(storage.loadPlayer(), result.getStock(),result.getStock());

        String jsonData = JSONUtils.toString(result);
        log.info(jsonData);
    }

}