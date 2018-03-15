package com.xuzp.stockplayer.web.dto.response;

import java.io.Serializable;

public class NLPResponseDTO implements Serializable {


    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
