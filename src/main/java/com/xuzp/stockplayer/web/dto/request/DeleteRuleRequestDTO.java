package com.xuzp.stockplayer.web.dto.request;

import java.io.Serializable;

public class DeleteRuleRequestDTO implements Serializable {

    private static final long serialVersionUID = 5887369922284943980L;

    private String user;

    private String text;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
