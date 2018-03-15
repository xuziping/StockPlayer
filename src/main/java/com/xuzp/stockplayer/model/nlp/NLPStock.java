package com.xuzp.stockplayer.model.nlp;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.xuzp.stockplayer.common.Constants;
import com.xuzp.stockplayer.common.ValidateUtils;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Stock;
import com.xuzp.stockplayer.nlp.AdaptedLevelEnum;
import com.xuzp.stockplayer.nlp.INLPAdapter;
import com.xuzp.stockplayer.nlp.INLPTarget;
import com.xuzp.stockplayer.nlp.NLPAdapterResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author XuZiPing
 * @Date 2018/1/21
 * @Time 0:32
 */
@Component("NLPStock")
public class NLPStock implements INLPAdapter, Serializable {

    @Override
    public NLPAdapterResult nlpAdapte(List<Term> termList) {

        String[] value = new String[3];
        termList.parallelStream().forEach(term -> {
            if (Nature.nx.equals(term.nature) &&
                    (Constants.SH.equalsIgnoreCase(term.word) || Constants.SZ.equalsIgnoreCase(term.word))) {
                value[0] = term.word.toUpperCase();
            } else if (Nature.m.equals(term.nature)
                    && term.word.length() == 6
                    && ValidateUtils.isPostiveNumber(term.word)) {
                value[1] = term.word;
            } else if (Nature.m.equals(term.nature) && NumberUtils.isNumber(term.word) && new BigDecimal(term.word).compareTo(BigDecimal.valueOf(1000)) == -1) {
                value[2] = term.word;
            }
        });

        if (value[0] == null || value[1] == null || value[2] == null) {
            return null;
        }

        IStock stock = new Stock();
        stock.setCode(value[0].toUpperCase() + value[1]);
        stock.setCurrentPrice(new BigDecimal(value[2]));

        return new NLPAdapterResult<>(stock, AdaptedLevelEnum.HIGH);
    }
}
