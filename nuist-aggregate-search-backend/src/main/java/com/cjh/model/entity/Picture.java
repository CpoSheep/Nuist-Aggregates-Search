package com.cjh.model.entity;

import java.io.Serializable;

/**
 * 图片pojo
 */
public class Picture implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
