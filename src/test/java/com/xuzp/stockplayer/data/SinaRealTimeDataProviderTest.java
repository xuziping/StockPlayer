package com.xuzp.stockplayer.data;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.StockPlayerApplicationTests;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.IStock;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author za-xuzhiping
 * @Date 2018/1/11
 * @Time 22:25
 */
public class SinaRealTimeDataProviderTest extends StockPlayerApplicationTests{

    private static final Logger log = LoggerFactory.getLogger(SinaRealTimeDataProviderTest.class);

    @Autowired
    private SinaRealTimeDataProvider sinaRealTimeDataProvider;

    @Test
    public void query() throws Exception {
        ResultBase<IStock> stock = sinaRealTimeDataProvider.query("SH600000");
        log.info(JSON.toJSONString(stock));
    }

}