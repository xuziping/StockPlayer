package com.xuzp.stockplayer.operate.impl;

import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.nlp.AdaptedLevelEnum;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import com.xuzp.stockplayer.operate.IRuleOperate;
import com.xuzp.stockplayer.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("markFavorOperateImpl")
public class MarkFavorOperateImpl implements IRuleOperate,Serializable {

    @Autowired
    private FileStorage fileStorage;

    @Override
    public ResultBase<List<String>> process(Player player, IStock ruleStock) {
        List<String> favStocks = player.getFavoriteStocks();
        if(favStocks==null) {
            favStocks = new ArrayList<>();
            player.setFavoriteStocks(favStocks);
        }
        Optional<String> existed = favStocks.parallelStream().filter(code->code.equalsIgnoreCase(ruleStock.getCode())).findAny();
        if(existed.isPresent()) {
           return ResultBaseUtils.fail(Constants.ERROR_CODE_DUPLICATED_FAVOR_STOCK, ruleStock.getCode());
        }
        favStocks.add(ruleStock.getCode());
        fileStorage.savePlayer(player);
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_MARK_FAVOR_STOCK, favStocks);
    }

    @Override
    public NLPAdapterResult nlpAdapte(List<Term> termList) {
        boolean isMatch = termList.parallelStream().anyMatch(term->
                Constants.MARK_FAVOR_ALIAS.parallelStream().anyMatch(alias->term.word.contains(alias))
        );
        if(isMatch) {
            return new NLPAdapterResult(this, AdaptedLevelEnum.HIGH);
        }

        return null;
    }
}
