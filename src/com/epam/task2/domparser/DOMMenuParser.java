package com.epam.task2.domparser;

import com.epam.task2.entity.Food;
import com.epam.task2.entity.Price;
import com.epam.task2.tool.Printer;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 25.09.2016.
 */
public class DOMMenuParser {

    private final static String XML_PATH = "src/resourse/restaurant.xml";

    public static void main(String[] args) throws SAXException, IOException {
        DOMParser parser = new DOMParser();
        parser.parse(XML_PATH);
        Document document = parser.getDocument();

        Element root = document.getDocumentElement();
        List<Food> menu = new ArrayList<Food>();

        NodeList foodNodes = root.getElementsByTagName("food");
        Food food = null;

        for (int i = 0; i < foodNodes.getLength(); i++) {
            food = new Food();
            Element foodElement = (Element) foodNodes.item(i);
            food.setType(foodElement.getAttribute("type").trim());
            food.setName(getChild(foodElement, "name").getTextContent().trim());
            food.setImage(getChild(foodElement, "image").getTextContent().trim());
            food.getPortion().setValue(getChild(foodElement, "portion").getTextContent().trim());
            food.getPortion().setMeasure(getChild(foodElement, "portion").getAttribute("measure").trim());
            for(Element child : getAllChilds(foodElement, "description-price")){
                food.addDescription(getChild(child, "description").getTextContent().trim());
                Price price = new Price();
                price.setValue(Integer.parseInt(getChild(foodElement, "price").getTextContent()));
                price.setUnit(getChild(foodElement, "price").getAttribute("unit").trim());
                food.addPrice(price);
            }

            menu.add(food);
        }

        Printer.printFood(menu);

    }

    private static Element getChild(Element element, String childName){
        NodeList nodeList = element.getElementsByTagName(childName);
        Element child = (Element) nodeList.item(0);
        return child;
    }

    private static Element[] getAllChilds(Element element, String childName){
        NodeList nodeList = element.getElementsByTagName(childName);
        Element[] childs = new Element[nodeList.getLength()];
        for(int i=0; i<nodeList.getLength(); i++){
            childs[i] = (Element) nodeList.item(i);
        }
        return childs;
    }
}