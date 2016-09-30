package com.epam.task2.myxmlparser;

import com.epam.task2.myxmlparser.entity.ElementNode;
import com.epam.task2.myxmlparser.entity.TextNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ASUS on 28.09.2016.
 */
public class XmlParser {
    private StringBuilder xml;
    private BufferedReader reader;

    public void readFromFile(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(new File(filePath)));
        xml = new StringBuilder();

        readCharsFromFile(100);
    }

    public void readCharsFromFile(int number){
        try {
            int c;
            while (((number > 0) && (c = reader.read()) != -1)) {
                if (((char) c != '\r') && ((char) c != '\n')) {
                    xml.append((char) c);
                    number--;
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public ElementNode parse(String filePath) throws IOException{
        readFromFile(filePath);

        if (xml == null) {
            throw new InvalidXmlDocumentException("The xml String buffer was not initialized.");
        }

        skipSpaces();

        if(xml.charAt(1) == '?') {
            while (xml.charAt(0) != '>') {
                deleteFirstChar();
            }
            deleteFirstChar();
        }

        skipSpaces();

        ElementNode root;
        if(xml.charAt(0) == '<' ){
            root = parseElementNode();
        }
        else{
            throw new InvalidXmlDocumentException("This xml document is invalid");
        }
        reader.close();
        return root;
    }

    private ElementNode parseElementNode(){

        ElementNode node = new ElementNode();

        node.setName(readName());

        readAttributes(node);

        if(xml.charAt(0) == '>'){
            deleteFirstChar();
            String closeTag = "</"+node.getName();

            while (xml.indexOf(closeTag) !=0 ){
                int spaces = skipSpaces();

                if((xml.charAt(0) == '<') && (xml.charAt(1) != '/')){
                    ElementNode subNode = parseElementNode();
                    node.add(subNode);
                }else{
                    TextNode subNode = parseTextNode(spaces);
                    node.add(subNode);
                }
            }

            deleteCharsFromInterval(0, closeTag.length()+1);

        }else if( xml.charAt(0) != '/' && xml.charAt(1) != '>'){
            throw new InvalidXmlDocumentException("All xml elements must have a closing tag");
        }

        return node;
    }

    private TextNode parseTextNode(int paddingFront){
        StringBuffer text = new StringBuffer();
        TextNode node = new TextNode();

        for(int i=0;i<paddingFront;i++){
            text.append(' ');
        }

        int positionOfStartTag = xml.indexOf("<");

        if(positionOfStartTag == -1){
            throw new InvalidXmlDocumentException("The xml document is not valid.");
        }

        text.append(xml.substring(0, positionOfStartTag));
        deleteCharsFromInterval(0, positionOfStartTag);

        node.setText(text.toString());
        return node;
    }


    private void readAttributes(ElementNode node) {
        skipSpaces();

        while (xml.charAt(0) != '<' && xml.charAt(0) != '>' && !(xml.charAt(0) == '/' && xml.charAt(1) == '>')) {

            String name = xml.substring(0, xml.indexOf("="));
            deleteCharsFromInterval(0, xml.indexOf("=") + 1);

            char quote;
            if(xml.charAt(0) == '\"' || xml.charAt(0)== '\'' )
                quote = xml.charAt(0);
            else
                throw new InvalidXmlDocumentException("Attribute value must start with \" or \'");
            deleteFirstChar();


            boolean escape = true;

            StringBuilder buffer = new StringBuilder();

            char next = xml.charAt(0);
            do{

                escape = (next == '\\');

                buffer.append(next);

                deleteFirstChar();
                next = xml.charAt(0);

            }while(next != quote && !escape);
            deleteFirstChar();
            node.addAttribute(name, buffer.toString());

            skipSpaces();

        }
    }

    private String readName() {
        int endOfNameIndex = xml.indexOf(" ");
        int temp = xml.indexOf(">");

        if (temp < endOfNameIndex) {
            endOfNameIndex = temp;
        }
        if(endOfNameIndex == -1){
            endOfNameIndex=temp;
        }
        String name = xml.substring(1, endOfNameIndex);
        deleteCharsFromInterval(0, endOfNameIndex);
        return name;
    }

    private int skipSpaces() {
        int counter = 0;
        while (xml.charAt(0) == ' ') {
            deleteFirstChar();
            counter++;
        }
        return counter;
    }

    private void deleteFirstChar(){
        xml.deleteCharAt(0);
        readCharsFromFile(1);
    }

    private void deleteCharsFromInterval(int leftBound, int rightBound){
        xml.delete(leftBound, rightBound);
        readCharsFromFile(rightBound-leftBound+1);
    }

    private class InvalidXmlDocumentException extends RuntimeException {

        public InvalidXmlDocumentException(String msg) {
            super(msg);
        }
    }
}
