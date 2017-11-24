package cn.lhzs.web.controller;

import io.searchbox.annotations.JestId;

/**
 * Created by IBA-EDV on 2017/11/23.
 */
public class News {

    @JestId
    private int id;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}