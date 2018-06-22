package com.splb.main.item;

/**
 * Created by Administrator on 2018-3-29.
 */

public class InfoBean {
    private int id;
    private String text;

    public InfoBean(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
