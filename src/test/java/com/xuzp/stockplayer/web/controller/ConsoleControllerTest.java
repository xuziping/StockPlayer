package com.xuzp.stockplayer.web.controller;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.StockPlayerApplicationTests;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.web.controller.impl.ConsoleController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/1/11
 * @Time 16:36
 */
public class ConsoleControllerTest extends StockPlayerApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ConsoleControllerTest.class);

    @Autowired
    private ConsoleController consoleController;

    @Test
    public void marketPrice() throws Exception {
        ResultBase<List<IStock>> resultBase = consoleController.marketPrice();
        log.info(JSON.toJSONString(resultBase));
    }

    @Test
    public void playerInfo() throws Exception {
        ResultBase<Player> resultBase =   consoleController.playerInfo();
        log.info(JSON.toJSONString(resultBase));
    }

}