package com.xuzp.stockplayer.data;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.IStock;

/**
 * @author XuZiPing
 * @Date 2018/1/5
 * @Time 23:19
 */
public interface IMarketDataProvider {

    /**
     * 查询当前股价
     *
     * @param stockCode
     * @return
     */
    ResultBase<IStock> query(String stockCode);

    /**
     * 检查数据源属性配置
     *
     * @param <T>
     * @return
     */
    <T> ResultBase<T> checkProperties();

    /**
     * 刷新价格
     *
     * @param stockCode
     * @return
     */
    ResultBase<IStock> forceRefresh(String stockCode);

}
