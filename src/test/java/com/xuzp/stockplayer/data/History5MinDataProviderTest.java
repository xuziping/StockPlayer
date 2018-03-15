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
 * @Time 22:40
 */
public class History5MinDataProviderTest extends StockPlayerApplicationTests{

    private static final Logger log = LoggerFactory.getLogger(History5MinDataProviderTest.class);

    @Autowired
    private History5MinDataProvider history5MinDataProvider;

    @Test
    public void query() throws Exception {

        while(true) {
            ResultBase<IStock> stock =   history5MinDataProvider.query("SH601318");
            log.info(JSON.toJSONString(stock));
            Thread.sleep(1000);
        }
    }

}