package com.xuzp.stockplayer.operate;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.nlp.INLPAdapter;

import java.util.List;

public interface IRuleOperate extends INLPAdapter {


    /**
     * 添加删除规则的操作
     * @param player
     * @param ruleStock
     * @return
     */
    ResultBase<List<String>> process(Player player, IStock ruleStock);
}
