package com.xuzp.stockplayer.common;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xuzp.stockplayer.model.BusinessDeal;
import com.xuzp.stockplayer.model.InHandStock;

import java.util.Map;
import java.util.Set;

/**
 * @author XuZiPing
 * @Date 2018/1/12
 * @Time 23:08
 */
public class StockUtils {

    public static Set<InHandStock> maintainInHandStocks(Set<InHandStock> inHandStockList, BusinessDeal deal) {

        Map<String, InHandStock> ret = Maps.newHashMap();

        // 整理存在的数据
        for (InHandStock inHandStock : inHandStockList) {
            InHandStock in = ret.get(inHandStock.getStockCode());
            if (in == null) {
                in = new InHandStock(inHandStock);
                ret.put(in.getStockCode(), in);
            } else {
                in.setAmount(in.getAmount() + inHandStock.getAmount());
            }
        }

        // 插入新数据
        if (deal != null) {
            InHandStock in = ret.get(deal.getStockCode());
            if (in == null) {
                in = new InHandStock(deal);
                ret.put(in.getStockCode(), in);
            } else {
                in.setAmount(in.getAmount() + deal.getAmount());
            }
        }

        return Sets.newHashSet(ret.values());
    }
}
