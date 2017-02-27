package com.me.arabin.synoped;

import java.io.Serializable;

/**
 * Created by arabin on 2/26/17.
 */

public class Imgur implements Serializable {

    private String link;
    private String description;
    public Imgur(String link, String description){
        this.link = link;
        this.description = description;
    }

    public String getLink(){
        return  link;
    }
    public String getDescription(){return description;}

}
