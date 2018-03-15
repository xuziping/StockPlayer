package com.xuzp.stockplayer.task;

import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.data.DataProviderFactory;
import com.xuzp.stockplayer.model.BusinessDeal;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.rule.StockRule;
import com.xuzp.stockplayer.storage.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XuZiPing
 * @Date 2018/1/10
 * @Time 23:24
 */
@Component
public abstract class BaseTask implements ITask {

    private static final Logger log = LoggerFactory.getLogger(BaseTask.class);

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private DataProviderFactory dataProviderFactory;

    @Override
    public ResultBase<Player> executeTask() {

        Player player = fileStorage.loadPlayer();
        for (String stockCode : player.getFavoriteStocks()) {
            ResultBase<IStock> stockResult = dataProviderFactory.getMarketDataProvider(player).query(stockCode);
            if (stockResult.isSuccess()) {
                IStock stock = stockResult.getValue();
                for (StockRule<IStock, IStockOperate> rule : player.getRules()) {
                    if (rule.getStock().equals(stock)) {
                        log.info("处理{} - {}", stock.getCode(), stock.getTimestmap().toString());
                        ResultBase<BusinessDeal> result = rule.getOperate().process(player, stock, rule.getStock());
                        if (result.isSuccess()) {
                            fileStorage.savePlayer(player);
                        }
                    }
                }
            }
        }

        return ResultBaseUtils.success(Constants.SUCCESS_CODE_FINISH_TASK, player);
    }
}
