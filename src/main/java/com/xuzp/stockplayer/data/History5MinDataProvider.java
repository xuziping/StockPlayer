package com.xuzp.stockplayer.data;

import com.google.common.collect.Maps;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.ValidateUtils;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Stock;
import com.xuzp.stockplayer.properties.SimulateDataProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 0:05
 */
@Service
public class History5MinDataProvider implements IMarketDataProvider {

    private static final Logger log = LoggerFactory.getLogger(History5MinDataProvider.class);

    @Autowired
    private SimulateDataProperties simulateDataProperties;

    private static Map<String, LineIterator> lineIteratorMap = Maps.newConcurrentMap();

    private static Map<String, IStock> currentPriceMap = Maps.newHashMap();

    @Override
    @PostConstruct
    public <T> ResultBase<T> checkProperties() {
        if (simulateDataProperties == null) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "缺失 simulate-data 设置");
        }
        if (org.springframework.util.StringUtils.isEmpty(simulateDataProperties.getPath())) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "缺失 simulate-data.path 设置");
        }
        File folder = new File(simulateDataProperties.getPath());
        if (!folder.exists() || !folder.isDirectory()) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "错误 simulate-data.path 设置");
        }
        if (org.springframework.util.StringUtils.isEmpty(simulateDataProperties.getStartDate())) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "缺失 simulate-data.startDate 设置");
        }
        if (ValidateUtils.isValidInputDateFormat(simulateDataProperties.getStartDate())) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "错误 simulate-data.startDate 设置");
        }
        if (org.springframework.util.StringUtils.isEmpty(simulateDataProperties.getEndDate())) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "缺失 simulate-data.endDate 设置");
        }
        if (ValidateUtils.isValidInputDateFormat(simulateDataProperties.getEndDate())) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_SIMULATEDATA_PARAM, "错误 simulate-data.endDate 设置");
        }
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_VALID_SIMULATEDATA_PARAM);
    }

    @Override
    public ResultBase<IStock> query(String stockCode) {
        IStock stock = currentPriceMap.get(stockCode);
        if (stock==null){
            return ResultBaseUtils.fail(Constants.ERROR_CODE_QUERY_STOCK_PRICE, stockCode);
        }
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_QUERY_STOCK_PRICE, stock);
    }

    @Override
    public ResultBase<IStock> forceRefresh(String stockCode) {
        File path = getDataFolder();
        if (path == null) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_INVALID_DATA_PATH);
        }
        File stockDataFile = getStockDataFile(path, stockCode);
        if (stockDataFile == null) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_NO_STOCK_DATA);
        }
        LineIterator iterator = lineIteratorMap.get(stockCode);
        String content = null;
        if (iterator == null) {
            try {
                iterator = FileUtils.lineIterator(stockDataFile, "UTF-8");
                content = firstLocate(iterator);
                lineIteratorMap.put(stockCode, iterator);
            } catch (Exception e) {
                log.error("获取迭代器失败，异常={}", e.getMessage());
                return ResultBaseUtils.fail(Constants.ERROR_CODE_UPDATE_STOCK_PRICE);
            }
        } else {
            try {
                content = walk(iterator);
            } catch (Exception e) {
                log.error("迭代查询失败，异常={}", e.getMessage());
                return ResultBaseUtils.fail(Constants.ERROR_CODE_UPDATE_STOCK_PRICE);
            }
        }

        if (StringUtils.isEmpty(content)) {
            LineIterator.closeQuietly(iterator);
            return ResultBaseUtils.fail(Constants.ERROR_CODE_NO_MATCHED_DATA);
        } else {
            try {
                IStock stock = parse(content);
                currentPriceMap.put(stockCode, stock);
                return ResultBaseUtils.success(Constants.SUCCESS_CODE_UPDATE_STOCK_PRICE, stock);
            } catch (Exception e) {
                log.error("解析股票数据失败，异常={}", e.getMessage());
                return ResultBaseUtils.fail(Constants.ERROR_CODE_UPDATE_STOCK_PRICE);
            }
        }
    }

    private IStock parse(String content) throws Exception {
        IStock stock = new Stock();
        String[] data = content.split(",");
        stock.setCode(data[0]);
        Date ts = Constants.DATE_TIME_FORMAT.parse(data[1] + " " + data[2]);
        stock.setTimestamp(ts);
        stock.setOpenPrice(new BigDecimal(data[3]));
        stock.setLowPrice(new BigDecimal(data[4]));
        stock.setHighPrice(new BigDecimal(data[5]));
        stock.setClosePrice(new BigDecimal(data[6]));
        stock.setCurrentPrice(stock.getClosePrice());
        stock.setAmount(new BigDecimal(data[7]));
        return stock;
    }


    private File getDataFolder() {
        File path = new File(simulateDataProperties.getPath());
        if (!path.exists() || !path.isDirectory()) {
            return null;
        }
        return path;
    }

    private File getStockDataFile(File root, String stockCode) {
        File file = null;
        if (stockCode.length() == Constants.WHOLE_STOCK_CODE_LENGTH) {
            String market = stockCode.substring(0, 2);
            file = getStockDataFileByMarket(new File(root, market), stockCode);
        } else if (stockCode.length() == Constants.MAIN_STOCK_CODE_LENGTH) {
            file = getStockDataFileByMarket(new File(root, Constants.SH), Constants.SH + stockCode);
            if (file == null) {
                file = getStockDataFileByMarket(new File(root, Constants.SZ), Constants.SZ + stockCode);
            }
        }
        return file;
    }

    private File getStockDataFileByMarket(File marketFolder, String stockCode) {
        File file = new File(marketFolder, stockCode + ".txt");
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }

    private String firstLocate(LineIterator it) throws Exception {
        long start = Long.parseLong(simulateDataProperties.getStartDate());
        while (it.hasNext()) {
            String line = it.nextLine();

            // line is like:SH000300,20050408,09:35,984.665,983.651,985.671,985.556,377648
            if (StringUtils.isBlank(line) || line.length() < 17) {
                continue;
            }
            String date = line.substring(9, 17);
            if (!ValidateUtils.isPostiveNumber(date)) {
                continue;
            }
            if (start <= Long.parseLong(date)) {
                return line;
            }
        }
        return null;
    }

    private String walk(LineIterator it) throws Exception {
        long end = Long.parseLong(simulateDataProperties.getEndDate());
        while (it.hasNext()) {
            String line = it.nextLine();
            if (StringUtils.isBlank(line)) {
                continue;
            }
            if (end >= Long.parseLong(line.substring(9, 17))) {
                return line;
            } else {
                return null;
            }
        }
        return null;
    }
}