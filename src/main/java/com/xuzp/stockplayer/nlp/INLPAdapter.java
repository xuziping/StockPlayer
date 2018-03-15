package com.xuzp.stockplayer.nlp;

import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/1/16
 * @Time 22:36
 */
public interface INLPAdapter {

    /**
     * 检测词汇适配对象的匹配度
     * @param termList
     * @return
     */
    NLPAdapterResult nlpAdapte(List<Term> termList);
}
