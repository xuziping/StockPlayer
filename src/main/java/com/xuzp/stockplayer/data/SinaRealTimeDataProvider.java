package com.xuzp.stockplayer.data;

import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author XuZiPing
 * @Date 2018/1/6
 * @Time 1:53
 */
@Component
public class SinaRealTimeDataProvider implements IMarketDataProvider {

    private static final Logger log = LoggerFactory.getLogger(SinaRealTimeDataProvider.class);
    private static final String URL = "http://hq.sinajs.cn/list=%s";

    @Override
    @PostConstruct
    public <T> ResultBase<T> checkProperties() {
        return ResultBaseUtils.success();
    }

    @Override
    public ResultBase<IStock> forceRefresh(String stockCode) {
        return query(stockCode);
    }

    @Override
    public ResultBase<IStock> query(String stockCode) {
        URL ur = null;
        HttpURLConnection uc = null;
        String queryStockCode = stockCode;
        try {
            if (stockCode.length() == Constants.WHOLE_STOCK_CODE_LENGTH) {
                queryStockCode = stockCode.toLowerCase();
            } else if (stockCode.length() == Constants.MAIN_STOCK_CODE_LENGTH) {
                queryStockCode = stockCode.startsWith("60") ? "sh" + stockCode : "sz" + stockCode;
            }

            ur = new URL(String.format(URL, queryStockCode));
        } catch (Exception e) {
            log.error("市场数据源连接错误，异常={}", e.getMessage());
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_DATA_SOURCE);
        }

        try (InputStreamReader isr = new InputStreamReader(ur.openStream(), "GBK");
             BufferedReader reader = new BufferedReader(isr);) {

            String line = reader.readLine();
            IStock stock = parse(line);
            stock.setCode(queryStockCode.toUpperCase());
            return ResultBaseUtils.success(Constants.SUCCESS_CODE_QUERY_STOCK_PRICE, stock);
        } catch (Exception e) {
            log.error("查询股票{}出错，异常={}", stockCode, e.getMessage());
            return ResultBaseUtils.fail(Constants.ERROR_CODE_QUERY_STOCK_PRICE);
        }
    }

    private IStock parse(String val) throws Exception {
        Stock stock = new Stock();
        String value = val.split("\"")[1];
        String[] data = value.split(",");
        stock.setName(data[0]);
        stock.setOpenPrice(new BigDecimal(data[1]));
        stock.setYesterday(new BigDecimal(data[2]));
        stock.setCurrentPrice(new BigDecimal(data[3]));
        stock.setHighPrice(new BigDecimal(data[4]));
        stock.setLowPrice(new BigDecimal(data[5]));
        stock.setNumber(new BigDecimal(data[8]));
        stock.setAmount(new BigDecimal(data[9]));
        return stock;
    }

}
