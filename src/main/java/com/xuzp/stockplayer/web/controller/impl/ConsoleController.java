package com.xuzp.stockplayer.web.controller.impl;

import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.data.DataProviderFactory;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.storage.FileStorage;
import com.xuzp.stockplayer.web.dto.request.QueryStockPriceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author za-xuzhiping
 * @Date 2018/1/10
 * @Time 21:56
 */
@RestController
public class ConsoleController {

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private DataProviderFactory dataProviderFactory;

    @RequestMapping("/")
    public String hello() {
        return "Hello!";
    }

    @RequestMapping("/price/fav")
    public ResultBase<List<IStock>> marketPrice() {
        Player player = fileStorage.loadPlayer();
        List<IStock> stocks = player.getFavoriteStocks().stream().map(stockCode -> {
            ResultBase<IStock> stock = dataProviderFactory.getMarketDataProvider(player)
                    .query(stockCode);
            if (stock.isSuccess()) {
                return stock.getValue();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_QUERY_STOCK_PRICE, stocks);
    }

    @RequestMapping("/price/single")
    public ResultBase<IStock> singlePrice(QueryStockPriceRequestDTO requestDTO){
        Player player = fileStorage.loadPlayer();
        ResultBase<IStock> stock = dataProviderFactory.getMarketDataProvider(player)
                .query(requestDTO.getStockCode());
        if (stock.isSuccess()) {
            return ResultBaseUtils.success(Constants.SUCCESS_CODE_QUERY_STOCK_PRICE, stock.getValue());
        }
        return ResultBaseUtils.fail(Constants.ERROR_CODE_QUERY_STOCK_PRICE);
    }

    @RequestMapping("/player")
    public ResultBase<Player> playerInfo() {
        Player player = fileStorage.loadPlayer();
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_QUERY_PLAYER, player);
    }
}
