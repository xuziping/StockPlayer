package com.xuzp.stockplayer.data;

import com.xuzp.stockplayer.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author za-xuzhiping
 * @Date 2018/1/11
 * @Time 23:46
 */
@Component
public class DataProviderFactory {

    @Autowired
    private History5MinDataProvider history5MinDataProvider;

    @Autowired
    private  SinaRealTimeDataProvider sinaRealTimeDataProvider;

    public IMarketDataProvider getMarketDataProvider(Player player){
        if (player.getUseSimulateData()) {
            return history5MinDataProvider;
        } else {
            return sinaRealTimeDataProvider;
        }
    }
}
