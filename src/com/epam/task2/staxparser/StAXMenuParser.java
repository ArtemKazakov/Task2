package com.epam.task2.staxparser;

import com.epam.task2.entity.Food;
import com.epam.task2.entity.Price;
import com.epam.task2.tool.Printer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 28.09.2016.
 */
public class StAXMenuParser {

    private final static String XML_PATH = "src/resourse/restaurant.xml";

    public static void main(String[] args) throws FileNotFoundException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            InputStream input = new FileInputStream(XML_PATH);

            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);

            List<Food> menu = process(reader);

            Printer.printFood(menu);

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static List<Food> process(XMLStreamReader reader) throws XMLStreamException {
        List<Food> menu = new ArrayList<Food>();
        Food food = null;
        Price price = null;
        MenuTagName elementName = null;

        while (reader.hasNext()) {

            int type = reader.next();

            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    elementName = MenuTagName.getElementTagName(reader.getLocalName());
                    switch (elementName) {
                        case FOOD:
                            food = new Food();
                            String foodType = reader.getAttributeValue(null, "type");
                            food.setType(foodType);
                            break;
                        case PORTION:
                            String measure = reader.getAttributeValue(null, "measure");
                            food.getPortion().setMeasure(measure);
                            break;
                        case PRICE:
                            price = new Price();
                            String unit = reader.getAttributeValue(null, "unit");
                            price.setUnit(unit);
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty()) {
                        break;
                    }
                    switch (elementName) {
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
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    elementName = MenuTagName.getElementTagName(reader.getLocalName());
                    switch (elementName) {
                        case FOOD:
                            menu.add(food);
                            food = null;
                    }
            }
        }
        return menu;
    }
}
