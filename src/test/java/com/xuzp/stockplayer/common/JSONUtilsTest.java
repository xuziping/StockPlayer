package com.xuzp.stockplayer.common;

import com.alibaba.fastjson.JSON;
import com.xuzp.stockplayer.StockPlayerApplicationTests;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.storage.FileStorage;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author za-xuzhiping
 * @Date 2018/1/19
 * @Time 16:15
 */
public class JSONUtilsTest extends StockPlayerApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(JSONUtilsTest.class);

    File file = new File("D:/wstock_metastock/test.json");

    @Autowired
    private FileStorage fileStorage;

    @Test
    public void save(){

        Player player = fileStorage.loadPlayer();
        log.info(JSON.toJSONString(player));
//        fileStorage.savePlayer(player);

        writeJSON(player);
    }

    @Test
    public void load(){
//        Player player = fileStorage.readJSONObjectFromFile(Player.class);


        if (file.exists() && file.isFile()) {
            try {
                String json = FileUtils.readFileToString(file, "UTF-8");
                Player player = new JSONDeserializer<Player>().deserialize(json);
                log.info(JSON.toJSONString(player));
            } catch (Exception e) {
                log.error("读取JSON格式的对象出错，异常={}", e);
            }
        }

    }

    public void writeJSON(Player player) {
        String json=new JSONSerializer().prettyPrint(true)
                .exclude("rules")
                .deepSerialize(player);
        log.info(json);
        try {
            FileUtils.writeStringToFile(file, json, "UTF-8", false);
        } catch (Exception e) {
            log.error("保存JSON格式的对象出错，异常={}", e);
        }
    }

    public Player readJSON(){
        if (file.exists() && file.isFile()) {
            try {
                String json = FileUtils.readFileToString(file, "UTF-8");
                Player object = new JSONDeserializer<Player>().deserialize(json);
                return object;
            } catch (Exception e) {
                log.error("读取JSON格式的对象出错，异常={}", e);
            }
        }
        return null;
    }
}