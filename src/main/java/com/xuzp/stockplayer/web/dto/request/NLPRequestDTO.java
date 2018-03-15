package com.xuzp.stockplayer.web.dto.request;

import java.io.Serializable;

public class NLPRequestDTO implements Serializable {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
