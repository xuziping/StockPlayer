package com.xuzp.stockplayer.task;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author XuZiPing
 * @Date 2018/1/9
 * @Time 23:39
 */
@Service
public class ManualTask extends BaseTask {

    private static final Logger log = LoggerFactory.getLogger(ManualTask.class);

    @Override
    public ResultBase<Player> executeTask() {
        log.info("执行手动任务");
        return super.executeTask();
    }
}
