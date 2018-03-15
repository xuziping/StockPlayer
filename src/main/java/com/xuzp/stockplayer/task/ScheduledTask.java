package com.xuzp.stockplayer.task;

import com.xuzp.stockplayer.common.result.ResultBase;
import com.xuzp.stockplayer.common.result.ResultBaseUtils;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.properties.GlobalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author XuZiPing
 * @Date 2018/1/5
 * @Time 21:52
 */
@Component
public class ScheduledTask extends BaseTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private GlobalProperties globalProperties;

    @Override
    @Scheduled(cron = "0/1 * * * * ?")
    public ResultBase<Player> executeTask() {
        if (globalProperties.getIsAutoTransact()) {
            log.info("定时执行任务");
            return super.executeTask();
        }
        return ResultBaseUtils.success();
    }
}
