package com.me.arabin.synoped;

import java.io.Serializable;

/**
 * Created by arabin on 2/23/17.
 */

public class NewsItems implements Serializable{

    private String description;
    private String url;
    private String title;

    public NewsItems(String title, String description, String url){
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl(){
        return url;
    }

    public String getTitle(){
        return title;
    }
}
