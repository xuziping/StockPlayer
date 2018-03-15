package com.xuzp.stockplayer.rule;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Stock;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.operate.impl.BuyStockOperateImpl;
import com.xuzp.stockplayer.operate.impl.SellStockOperateImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/1/15
 * @Time 21:52
 */
@Service
public class RuleProcessor {

    private static final Logger log = LoggerFactory.getLogger(RuleProcessor.class);

    @Autowired
    private BuyStockOperateImpl buyStockOperate;

    @Autowired
    private SellStockOperateImpl sellStockOperate;

    public StockRule parse(String input) throws Exception {
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        List<Term> termList = segment.seg(input);

        NLPAdapterResult<IStock> stockResult = new Stock().nlpAdapte(termList);
        if (stockResult != null) {

            NLPAdapterResult <IStockOperate> operateResult = buyStockOperate.nlpAdapte(termList);
            if (operateResult == null) {
                operateResult = sellStockOperate.nlpAdapte(termList);
            }

            if (operateResult != null) {
                return new StockRule<IStock, IStockOperate>(stockResult.getValue(), operateResult.getValue());
            }

        }
        return null;
    }
}
