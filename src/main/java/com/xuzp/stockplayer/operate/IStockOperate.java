package com.xuzp.stockplayer.operate;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.BusinessDeal;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.nlp.INLPAdapter;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 21:54
 */
public interface IStockOperate extends INLPAdapter {

    /**
     * 买卖股票的操作
     * @param player
     * @param stock
     * @param ruleStock
     * @return
     */
    ResultBase<BusinessDeal> process(Player player, IStock stock, IStock ruleStock);

}
