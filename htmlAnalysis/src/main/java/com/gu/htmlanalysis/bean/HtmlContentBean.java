package com.gu.htmlanalysis.bean;

import android.text.TextUtils;

/**
 * Created by 顾佳佳 on 2020/12/27.
 */
public class HtmlContentBean {
    public String title;
    public String url;
    private String image;
    private String description;
    private String author;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl(){
        if(image == null){
            return "";
        }
        return image;
    }
}
