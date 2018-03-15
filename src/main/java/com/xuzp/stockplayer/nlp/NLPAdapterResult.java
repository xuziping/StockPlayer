package com.xuzp.stockplayer.nlp;

import com.google.common.collect.Lists;
import com.hankcs.hanlp.seg.common.Term;

import java.io.Serializable;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/1/16
 * @Time 22:38
 */
public class NLPAdapterResult <T, F extends INLPTarget> implements Serializable {

    private static final long serialVersionUID = 3939531963037932331L;

    private T value;

    private AdaptedLevelEnum adaptedLevel;

    private List<Term> terms;

    private List<F> targets;

    public NLPAdapterResult(){
    }

    public NLPAdapterResult(T value, AdaptedLevelEnum adaptedLevel, F... targets) {
        this.value = value;
        this.adaptedLevel = adaptedLevel;
        this.targets = Lists.newArrayList(targets);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public AdaptedLevelEnum getAdaptedLevel() {
        return adaptedLevel;
    }

    public void setAdaptedLevel(AdaptedLevelEnum adaptedLevel) {
        this.adaptedLevel = adaptedLevel;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<F> getTargets() {
        return targets;
    }

    public void setTargets(List<F> targets) {
        this.targets = targets;
    }
}
