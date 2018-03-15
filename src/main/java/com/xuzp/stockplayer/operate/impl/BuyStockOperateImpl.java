package com.xuzp.stockplayer.operate.impl;

import com.google.common.collect.Lists;
import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.StockUtils;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.*;
import com.xuzp.stockplayer.nlp.AdaptedLevelEnum;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import com.xuzp.stockplayer.operate.IStockOperate;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 22:10
 */
@Service("buyStockOperateImpl")
public class BuyStockOperateImpl implements IStockOperate,Serializable {

    private static final Logger log = LoggerFactory.getLogger(BuyStockOperateImpl.class);
    private static final long serialVersionUID = -4936890238252239258L;

    private String name = Constants.BUY_ALIAS.get(0);

    @Override
    public ResultBase<BusinessDeal> process(Player originalPlayer, IStock stock, IStock ruleStock) {

        if (originalPlayer.getCash().compareTo(BigDecimal.TEN) == 1
                && stock.getCurrentPrice().compareTo(BigDecimal.ONE) == 1
                ) {
            BigDecimal limit = originalPlayer.getCash();
            if (ruleStock.getAmount() != null && ruleStock.getAmount().compareTo(BigDecimal.ONE) == 1) {
                if (getSpendCashByStock(originalPlayer, stock).compareTo(ruleStock.getAmount()) > 0) {
                    return ResultBaseUtils.fail(Constants.ERROR_CODE_EXCEED_TOP_LIMIT);
                }
                if (limit.compareTo(ruleStock.getAmount()) > 0) {
                    limit = ruleStock.getAmount();
                }
            }

            BigDecimal count = limit.divide(stock.getCurrentPrice(), 3).divide(
                    Constants.HUNDRED, BigDecimal.ROUND_DOWN);
            BigDecimal cash = originalPlayer.getCash().subtract(stock.getCurrentPrice().multiply(count).multiply(Constants.HUNDRED));
            if (count.compareTo(BigDecimal.ONE) == 1 && cash.compareTo(BigDecimal.ZERO) >= 0) {

                Player workingPlayer = new Player(originalPlayer);
                BusinessDeal deal = new BusinessDeal(stock, count.longValue() * 100);

                List<BusinessDeal> businessDealList = workingPlayer.getBusinessDeals();
                if (businessDealList == null) {
                    businessDealList = Lists.newArrayList();
                    workingPlayer.setBusinessDeals(businessDealList);
                }
                businessDealList.add(deal);

                workingPlayer.setCash(cash);

                Set<InHandStock> inHandStocks = StockUtils.maintainInHandStocks(workingPlayer.getInHandStocks(), deal);
                workingPlayer.setInHandStocks(inHandStocks);

                originalPlayer.copy(workingPlayer);
                log.info("{}买入{}成功", workingPlayer.getUserName(), stock.getCode());
                return ResultBaseUtils.success(Constants.SUCCESS_CODE_BUY_STOCK, deal);
            }
        }

        return ResultBaseUtils.fail(Constants.ERROR_CODE_NO_CASH);
    }

    public BigDecimal getSpendCashByStock(Player player, IStock stock) {
        if (CollectionUtils.isNotEmpty(player.getInHandStocks())) {
            Optional<InHandStock> stockRecord = player.getInHandStock(stock.getCode());
            if (stockRecord.isPresent()) {
                return BigDecimal.valueOf(stockRecord.get().getAmount())
                        .multiply(stock.getCurrentPrice());
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public NLPAdapterResult nlpAdapte(List<Term> termList) {
        boolean isMatch = termList.parallelStream().anyMatch(term->
                Constants.BUY_ALIAS.parallelStream().anyMatch(alias->term.word.contains(alias))
        );
        if(isMatch) {
            return new NLPAdapterResult(this, AdaptedLevelEnum.HIGH, new Stock() );
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
