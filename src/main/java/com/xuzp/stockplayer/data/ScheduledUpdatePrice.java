package com.xuzp.stockplayer.data;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.properties.SimulateDataProperties;
import com.xuzp.stockplayer.storage.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author XuZiPing
 * @Date 2018/1/13
 * @Time 0:56
 */
@Component
public class ScheduledUpdatePrice {

    private static final Logger log = LoggerFactory.getLogger(ScheduledUpdatePrice.class);

    @Autowired
    private DataProviderFactory dataProviderFactory;

    @Autowired
    private SimulateDataProperties simulateDataProperties;

    @Autowired
    private FileStorage fileStorage;

    @Scheduled(cron = "0/1 * * * * ?")
    public ResultBase<List<IStock>> executeTask() {
        if (simulateDataProperties.getAutoRefresh()) {
            Player player = fileStorage.loadPlayer();
            for (String stockCode : player.getFavoriteStocks()) {
                ResultBase<IStock> stockResult = dataProviderFactory.getMarketDataProvider(player).forceRefresh(stockCode);
                if (stockResult.isSuccess()) {
                    log.info("{}最新报价: {}", stockCode, JSON.toJSONString(stockResult.getValue()));
                } else {
                    log.error("更新{}价格出错, {}", stockCode, JSON.toJSON(stockResult));
                }
            }
        }
        return ResultBaseUtils.success();
    }
}
