package com.epam.task2.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 25.09.2016.
 */
public class Food {
    private String type;
    private String name;
    private String image;
    private ArrayList<String> descriptions;
    private Portion portion;
    private ArrayList<Price> prices;

    public Food(){
        descriptions = new ArrayList<String>();
        portion = new Portion();
        prices = new ArrayList<Price>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

    public void addDescription(String description){
        this.descriptions.add(description);
    }

    public Portion getPortion() {
        return portion;
    }

    public void setPortion(Portion portion) {
        this.portion = portion;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Price> prices) {
        this.prices = prices;
    }

    public void addPrice(Price price){
        this.prices.add(price);
    }
}
