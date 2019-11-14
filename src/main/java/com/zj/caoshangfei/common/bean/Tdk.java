package com.zj.caoshangfei.common.bean;

import java.io.Serializable;

/**
 * Created by jin.zhang@fuwo.com on 2017/12/1.
 */
public class Tdk implements Serializable{
    private String title;

    private String desc;

    private String keyWords;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
