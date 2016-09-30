package com.epam.task2.saxparser;

import com.epam.task2.entity.Food;
import com.epam.task2.entity.Price;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27.09.2016.
 */
public class MenuSaxHandler extends DefaultHandler   {
    private List<Food> foodList;
    private Food food;
    private Price price;
    private StringBuilder text;
    private final static String MSG_START = "Parsing started.";
    private final static String MSG_END = "Parsing ended.";

    public List<Food> getFoodList() {
        return foodList;
    }

    public void startDocument() throws SAXException {
        System.out.println(MSG_START);
        foodList = new ArrayList<Food>();
    }

    public void endDocument() throws SAXException {
        System.out.println(MSG_END);
    }

    public void characters(char[] buffer, int start, int length) {
        text.append(buffer, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        text = new StringBuilder();
        if (qName.equals("food")) {
            food = new Food();
            food.setType(attributes.getValue("type"));
        }
        if (qName.equals("portion")){
            food.getPortion().setMeasure(attributes.getValue("measure"));
        }
        if (qName.equals("price")){
            price = new Price();
            price.setUnit(attributes.getValue("unit"));
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        MenuTagName tagName = MenuTagName.valueOf(qName.toUpperCase().replace("-", "_"));

        switch (tagName) {
            case NAME:
                food.setName(text.toString());
                break;
            case IMAGE:
                food.setImage(text.toString());
                break;
            case PORTION:
                food.getPortion().setValue(text.toString());
                break;
            case PRICE:
                price.setValue(Integer.parseInt(text.toString()));
                food.addPrice(price);
                break;
            case DESCRIPTION:
                food.addDescription(text.toString());
                break;
            case FOOD:
                foodList.add(food);
                food = null;
                break;
        }
    }
}
