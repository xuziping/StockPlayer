package com.xuzp.stockplayer.web.dto.request;

import java.io.Serializable;

/**
 * @author za-xuzhiping
 * @Date 2018/1/19
 * @Time 18:49
 */
public class QueryPlayerRequestDTO implements Serializable{

    private static final long serialVersionUID = -758265132815567209L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
