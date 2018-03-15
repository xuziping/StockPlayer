package com.xuzp.stockplayer.common;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author XuZiPing
 * @Date 2018/1/7
 * @Time 2:10
 */
public interface Constants {

    List<String> TIMES = Lists.newArrayList(
            "09:35", "09:40", "09:45", "09:50", "09:55"
            , "10:00", "10:05", "10:10", "10:15", "10:20", "10:25", "10:30", "10:35", "10:40", "10:45", "10:50", "10:55"
            , "11:00", "11:05", "11:10", "11:15", "11:20", "11:25", "11:30"
            , "13:05", "13:10", "13:15", "13:20", "13:25", "13:30", "13:35", "13:40", "13:45", "13:50", "13:55"
            , "14:00", "14:05", "14:10", "14:15", "14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50", "14:55"
            , "15:00"
    );

    SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm");
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    BigDecimal HUNDRED = BigDecimal.valueOf(100);

    String ERROR_CODE_GLOBAL_EXCEPTION = "GLOBAL_EXCEPTION";
    String ERROR_CODE_NO_CASH = "NO_CASH";
    String ERROR_CODE_HAS_NOT_STOCK = "HAS_NOT_STOCK";
    String ERROR_CODE_INVALID_DATA_PATH = "INVALID_DATA_PATH";
    String ERROR_CODE_INVALID_DATA_SOURCE = "INVALID_DATA_SOURCE";
    String ERROR_CODE_QUERY_STOCK_PRICE = "ERROR_QUERY_STOCK_PRICE";
    String ERROR_CODE_UPDATE_STOCK_PRICE = "ERROR_CODE_UPDATE_STOCK_PRICE";
    String ERROR_CODE_EXCEED_TOP_LIMIT = "EXCEED_TOP_LIMIT";
    String ERROR_CODE_NO_STOCK_DATA = "NO_STOCK_DATA";
    String ERROR_CODE_NO_MATCHED_DATA = "NO_MATCHED_DATA";
    String ERROR_CODE_INVALID_SIMULATEDATA_PARAM = "INVALID_SIMULATEDATA_PARAM";
    String ERROR_CODE_DUPLICATED_FAVOR_STOCK = "DUPLICATED_FAVOR_STOCK";
    String ERROR_CODE_NOT_EXIST_FAVOR_STOCK = "NOT_EXIST_FAVOR_STOCK";

    String SUCCESS_CODE_BUY_STOCK = "BUY_STOCK";
    String SUCCESS_CODE_SELL_STOCK = "SELL_STOCK";
    String SUCCESS_CODE_FINISH_TASK = "FINISH_TASK";
    String SUCCESS_CODE_QUERY_STOCK_PRICE = "QUERY_STOCK_PRICE";
    String SUCCESS_CODE_UPDATE_STOCK_PRICE = "UPDATE_STOCK_PRICE";
    String SUCCESS_CODE_QUERY_PLAYER = "QUERY_PLAYER";
    String SUCCESS_CODE_VALID_SIMULATEDATA_PARAM = "VALID_SIMULATEDATA_PARAM";
    String SUCCESS_CODE_MARK_FAVOR_STOCK = "MARK_FAVOR_STOCK";
    String SUCCESS_CODE_MARK_UNFAVOR_STOCK = "MARK_UNFAVOR_STOCK";

    List<String> BUY_ALIAS = Lists.newArrayList("买", "buy", "购");
    List<String> SELL_ALIAS = Lists.newArrayList("卖", "sell", "售");
    List<String> MARK_FAVOR_ALIAS = Lists.newArrayList("加", "add", "添", "标");
    List<String> MARK_UNFAVOR_ALIAS = Lists.newArrayList("取消", "delete", "删除");


    int WHOLE_STOCK_CODE_LENGTH = 8;

    int MAIN_STOCK_CODE_LENGTH = 6;

    String SH = "SH";
    String SZ = "SZ";

    List<String> OBJECTS = Lists.newArrayList("NLPStock");
    List<String> OPERATES = Lists.newArrayList("buyStockOperateImpl", "sellStockOperateImpl",
            "markFavorOperateImpl", "markUnfavorOperateImpl");
}
