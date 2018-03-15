package com.xuzp.stockplayer.nlp;

/**
 * @author XuZiPing
 * @Date 2018/1/17
 * @Time 22:11
 */
public enum AdaptedLevelEnum {

    /**
     * 高契合
     */
    HIGH(5),

    /**
     * 中度契合
     */
    NORMAL(3),

    /**
     * 低契合
     */
    LOW(1);

    private int level;

    private AdaptedLevelEnum(int level){
        this.level=level;
    }

    public int getLevel(){
        return level;
    }
}
