package com.me.arabin.synoped;

import java.io.Serializable;

/**
 * Created by arabin on 2/27/17.
 */

public class Memes implements Serializable {

    private String id;
    private String title;

    public Memes(String id, String title){
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
