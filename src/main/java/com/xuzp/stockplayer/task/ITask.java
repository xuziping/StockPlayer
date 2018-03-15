package com.xuzp.stockplayer.task;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.Player;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 23:40
 */
public interface ITask {

    /**
     * 按照策略执行自动买进卖出任务
     * @return
     */
    ResultBase<Player> executeTask();
}
