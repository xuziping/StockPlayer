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
import java.util.List;

@Service("markUnfavorOperateImpl")
public class MarkUnfavorOperateImpl  implements IRuleOperate,Serializable{

    @Autowired
    private FileStorage fileStorage;

    @Override
    public NLPAdapterResult nlpAdapte(List<Term> termList) {
        boolean isMatch = termList.parallelStream().anyMatch(term->
                Constants.MARK_UNFAVOR_ALIAS.parallelStream().anyMatch(alias->term.word.contains(alias))
        );
        if(isMatch) {
            return new NLPAdapterResult(this, AdaptedLevelEnum.HIGH);
        }

        return null;
    }

    @Override
    public ResultBase<List<String>> process(Player player, IStock ruleStock) {
        List<String> favStocks = player.getFavoriteStocks();
        if(favStocks==null || !favStocks.contains(ruleStock.getCode())) {
            return ResultBaseUtils.fail(Constants.ERROR_CODE_NOT_EXIST_FAVOR_STOCK, ruleStock.getCode());
        }
        favStocks.parallelStream().filter(code->code.equalsIgnoreCase(ruleStock.getCode())).findAny().ifPresent(x->{
            favStocks.remove(x);
            fileStorage.savePlayer(player);
        });
        return ResultBaseUtils.success(Constants.SUCCESS_CODE_MARK_UNFAVOR_STOCK, favStocks);
    }
}
