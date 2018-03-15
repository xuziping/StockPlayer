package com.xuzp.stockplayer.web.dto.request;

import java.io.Serializable;

public class SaveRuleRequestDTO implements Serializable{

    private static final long serialVersionUID = -3194092344597153627L;

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
