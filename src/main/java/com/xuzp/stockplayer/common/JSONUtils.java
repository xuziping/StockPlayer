package com.xuzp.stockplayer.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author za-xuzhiping
 * @Date 2018/1/10
 * @Time 1:02
 */
public class JSONUtils {

    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    public static <T> String toString(T object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return beautify(json);
    }

    public static <T> T toObject(String json, Class<T> cls) {
        Gson gson = new Gson();
        T obj = gson.fromJson(json, cls);
        return obj;
    }

    public static String beautify(String content) {
        if (StringUtils.isNoneEmpty(content)) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jsonPar = new JsonParser();
                JsonElement jsonEl = jsonPar.parse(content);
                return gson.toJson(jsonEl);
            } catch (Exception e) {
                log.warn("格式化Json失败, 内容={}, 异常={}", content, e);
                return content;
            }
        }
        return "";
    }
}
