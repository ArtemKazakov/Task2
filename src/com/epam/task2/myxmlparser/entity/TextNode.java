package com.epam.task2.myxmlparser.entity;

import com.epam.task2.myxmlparser.entity.Node;

/**
 * Created by ASUS on 28.09.2016.
 */
public class TextNode extends Node {
    private String text;

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String toString(){
        return text;
    }
}
