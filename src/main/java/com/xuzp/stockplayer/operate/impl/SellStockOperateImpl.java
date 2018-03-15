package com.xuzp.stockplayer.operate.impl;

import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.BusinessDeal;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.InHandStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.nlp.AdaptedLevelEnum;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import com.xuzp.stockplayer.operate.IStockOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 22:12
 */
@Service("sellStockOperateImpl")
public class SellStockOperateImpl implements IStockOperate,Serializable {

    private static final Logger log = LoggerFactory.getLogger(SellStockOperateImpl.class);
    private static final long serialVersionUID = 3340733310334709510L;

    private String name = Constants.SELL_ALIAS.get(0);

    @Override
    public synchronized ResultBase<BusinessDeal> process(Player originalPlayer, IStock stock, IStock ruleStock) {
        if (!CollectionUtils.isEmpty(originalPlayer.getInHandStocks())) {
            Long amount = getValidToSellAmount(originalPlayer, stock);
            if (amount > 0) {
                Player workingPlayer = new Player(originalPlayer);

                BusinessDeal deal = new BusinessDeal(stock, -amount);

                List<BusinessDeal> businessDealList = workingPlayer.getBusinessDeals();
                businessDealList.add(deal);

                BigDecimal addCash = new BigDecimal(String.valueOf(amount)).multiply(stock.getCurrentPrice());
                workingPlayer.setCash(workingPlayer.getCash().add(addCash));

                InHandStock inHandStock = workingPlayer.getInHandStock(stock.getCode()).get();
                if (inHandStock.getAmount() > amount.longValue()) {
                    inHandStock.setAmount(inHandStock.getAmount() - amount.longValue());
                } else {
                    workingPlayer.getInHandStocks().remove(inHandStock);
                }
                originalPlayer.copy(workingPlayer);

                log.info("{}卖出{}成功", originalPlayer.getUserName(), stock.getCode());
                return ResultBaseUtils.success(Constants.SUCCESS_CODE_SELL_STOCK, deal);
            }
        }
        return ResultBaseUtils.fail(Constants.ERROR_CODE_HAS_NOT_STOCK);
    }

    public Long getValidToSellAmount(Player player, IStock stock) {
        Optional<BusinessDeal> deal = player.getBusinessDeals().stream().filter(x -> x.getStockCode().equalsIgnoreCase(stock.getCode())
                && !isSameDate(stock.getTimestmap(), x.getTimestamp())).reduce((x, y) ->
                new BusinessDeal(x.getStockCode(),
                       x.getAmount() + y.getAmount(), stock.getCurrentPrice(),
                        x.getTimestamp())
        );
        if (deal.isPresent()) {
            return deal.get().getAmount();
        }
        return Long.valueOf(0);
    }

    public boolean isSameDate(Date a, Date b) {
        if (a != null && b != null) {
            return Constants.DATE_FORMAT.format(a).equalsIgnoreCase(Constants.DATE_FORMAT.format(b));
        }
        return false;

    }

    @Override
    public NLPAdapterResult nlpAdapte(List<Term> termList) {
        boolean isMatch = termList.parallelStream().anyMatch(term->
                Constants.SELL_ALIAS.parallelStream().anyMatch(alias->term.word.contains(alias))
        );
        if(isMatch) {
            return new NLPAdapterResult(this, AdaptedLevelEnum.HIGH);
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
