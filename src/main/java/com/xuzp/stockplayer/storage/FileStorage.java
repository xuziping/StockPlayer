package com.xuzp.stockplayer.storage;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xuzp.stockplayer.model.IStock;
import com.xuzp.stockplayer.model.Player;
import com.xuzp.stockplayer.operate.IStockOperate;
import com.xuzp.stockplayer.properties.PlayerProperties;
import com.xuzp.stockplayer.properties.StorageProperties;
import com.xuzp.stockplayer.rule.RuleProcessor;
import com.xuzp.stockplayer.rule.StockRule;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author XuZiPing
 * @Date 2018/1/6
 * @Time 2:26
 */

@Service
public class FileStorage {

    private static Logger log = LoggerFactory.getLogger(FileStorage.class);

    private static final Map<String, Player> PLAYER_CACHE_MAP = Maps.newConcurrentMap();

    @Autowired
    private PlayerProperties playerProperties;

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private RuleProcessor ruleProcessor;

    private File outputFolder;

    private String suffix;

    @PostConstruct
    public void init() throws Exception {
        this.outputFolder = new File(storageProperties.getPath());
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        if (!outputFolder.isDirectory()) {
            throw new Exception("输出文件夹错误，路径=" + outputFolder.getAbsolutePath());
        }
        suffix = storageProperties.getSaveAsJSON() ? ".json" : ".data";
    }

    private File getFile() {
        return new File(outputFolder, String.format("stock_player_%s%s", playerProperties.getUserName(), suffix));
    }

    public <T> void writeJSONtoFile(T object) {
        try {
//            String jsonData = JSONUtils.toString(object);
            String jsonData = new JSONSerializer().prettyPrint(true)
                    .exclude("rules")
                    .deepSerialize(object);
            FileUtils.writeStringToFile(getFile(), jsonData, "UTF-8", false);
        } catch (Exception e) {
            log.error("保存JSON格式的对象出错，异常={}", e);
        }
    }


    public <T> T readJSONObjectFromFile(Class<T> cls) {
        File file = getFile();
        if (file.exists() && file.isFile()) {
            try {
                String json = FileUtils.readFileToString(file, "UTF-8");
//                T object = JSONUtils.toObject(json, cls);
                T object = new JSONDeserializer<T>().deserialize(json);
                return object;
            } catch (Exception e) {
                log.error("读取JSON格式的对象出错，异常={}", e);
            }
        }
        return null;
    }

    private void writeObjectToFile(Object obj) {
        File file = getFile();
        try (FileOutputStream out = new FileOutputStream(file);
             ObjectOutputStream objOut = new ObjectOutputStream(out);) {
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            log.info("保存成功");
        } catch (Exception e) {
            log.error("保存失败，异常={}", e);
        }
    }

    private Object readObjectFromFile() {
        Object temp = null;
        File file = getFile();
        try (FileInputStream in = new FileInputStream(file);
             ObjectInputStream objIn = new ObjectInputStream(in);) {
            temp = objIn.readObject();
            log.info("读取成功");
        } catch (Exception e) {
            log.error("读取失败，异常={}", e);
        }
        return temp;
    }

    public Player loadPlayer() {
        Player player = PLAYER_CACHE_MAP.get(playerProperties.getUserName());
        if (player != null) {
            return player;
        }
        if (storageProperties.getSaveAsJSON()) {
            player = readJSONObjectFromFile(Player.class);
            if (player != null && CollectionUtils.isNotEmpty(player.getOriginalRules())) {
                List<StockRule<IStock, IStockOperate>> rules = Lists.newArrayList();
                player.getOriginalRules().forEach(rule -> {
                    try {
                        StockRule<IStock, IStockOperate> result = ruleProcessor.parse(rule);
                        rules.add(result);
                        log.info(JSON.toJSONString(result));
                    } catch (Exception e) {
                        log.error("解析规则错误，规则原文={}", rule);
                    }
                });
                player.setRules(rules);
            }
        } else {
            player = (Player) readObjectFromFile();
        }
        if (player == null) {
            List<StockRule<IStock, IStockOperate>> rules = Lists.newArrayList();
            List<String> originalRules = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(playerProperties.getRules())) {
                playerProperties.getRules().forEach(rule -> {
                    try {
                        StockRule<IStock, IStockOperate> result = ruleProcessor.parse(rule);
                        rules.add(result);
                        log.info(JSON.toJSONString(result));
                        originalRules.add(rule);
                    } catch (Exception e) {
                        log.error("解析规则错误，规则原文={}", rule);
                    }
                });
            }
            player = new Player(playerProperties.getUserName(), playerProperties.getCash(),
                    playerProperties.getFavoriteStocks(), Lists.newArrayList()
                    , Sets.newHashSet(), playerProperties.getUseSimulateData(), rules, originalRules);
        }
        PLAYER_CACHE_MAP.put(player.getUserName(), player);
        return player;
    }

    public void savePlayer(Player player) {
        if (storageProperties.getSaveAsJSON()) {
            writeJSONtoFile(player);
        } else {
            writeObjectToFile(player);
        }
    }
}
