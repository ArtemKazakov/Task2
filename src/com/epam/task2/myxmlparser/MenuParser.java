package com.epam.task2.myxmlparser;

import com.epam.task2.entity.Food;
import com.epam.task2.entity.Price;
import com.epam.myxmlparser.entity.ElementNode;
import com.epam.myxmlparser.entity.Node;
import com.epam.task2.tool.Printer;
import com.epam.myxmlparser.XmlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ASUS on 28.09.2016.
 */
public class MenuParser {
    private final static String XML_PATH = "src/resourse/restaurant.xml";

    public static void main(String[] args) throws IOException{
        XmlParser parser = new XmlParser();
        ElementNode root = parser.parse(XML_PATH);
        LinkedList<Node> foods = root.getSubElements("food");
        List<Food> menu = new ArrayList<Food>();
        Food food = null;
        for (int i = 0; i < foods.size(); i++) {
            food = new Food();
            ElementNode foodElement = (ElementNode) foods.get(i);
            food.setType(foodElement.getAttribute("type").trim());
            food.setName(getSingleChild(foodElement, "name").getText().trim());
            menu.add(food);
        }

        for (int i = 0; i < foods.size(); i++) {
            food = new Food();
            ElementNode foodElement = (ElementNode) foods.get(i);
            food.setType(foodElement.getAttribute("type").trim());
            food.setName(getSingleChild(foodElement, "name").getText().trim());
            food.setImage(getSingleChild(foodElement, "image").getText().trim());
            food.getPortion().setValue(getSingleChild(foodElement, "portion").getText().trim());
            food.getPortion().setMeasure(getSingleChild(foodElement, "portion").getAttribute("measure").trim());
            for(ElementNode child : getAllChilds(foodElement, "description-price")){
                food.addDescription(getSingleChild(child, "description").getText().trim());
                Price price = new Price();
                price.setValue(Integer.parseInt(getSingleChild(foodElement, "price").getText()));
                price.setUnit(getSingleChild(foodElement, "price").getAttribute("unit").trim());
                food.addPrice(price);
            }

            menu.add(food);
        }

        Printer.printFood(menu);
    }

    private static ElementNode getSingleChild(ElementNode element, String childName){
        LinkedList<Node> foods = element.getSubElements(childName);
        ElementNode child = (ElementNode) foods.get(0);
        return child;
    }

    private static ElementNode[] getAllChilds(ElementNode element, String childName){
        LinkedList<Node> foods = element.getSubElements(childName);
        ElementNode[] childs = new ElementNode[foods.size()];
        for(int i=0; i<foods.size(); i++){
            childs[i] = (ElementNode) foods.get(i);
        }
        return childs;
    }
}
