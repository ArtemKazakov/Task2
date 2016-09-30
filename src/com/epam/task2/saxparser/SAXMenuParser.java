package com.epam.task2.saxparser;

import com.epam.task2.entity.Food;
import com.epam.task2.tool.Printer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 28.09.2016.
 */
public class SAXMenuParser {

    private final static String XML_PATH = "src/resourse/restaurant.xml";

    public static void main(String[] args) throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        MenuSaxHandler handler = new MenuSaxHandler();
        reader.setContentHandler(handler);
        reader.parse(new InputSource(XML_PATH));

        List<Food> menu = handler.getFoodList();

        Printer.printFood(menu);

    }
}
