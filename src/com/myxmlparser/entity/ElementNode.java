package com.myxmlparser.entity;

import java.util.LinkedList;

/**
 * Created by ASUS on 28.09.2016.
 */
public class ElementNode extends Node {

    private LinkedList<Node> subNodes;

    private LinkedList<Attribute> attributes;

    public ElementNode(){
        subNodes = new LinkedList<>();
        attributes = new LinkedList<>();
    }

    public void addAttribute(String name, String value){
        Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setValue(value);
        attributes.add(attribute);
    }

    public void setAttribute(String name, String value){
        for(int i=0; i<attributes.size(); i++){
            if(attributes.get(i).getName().equals(name))
                attributes.get(i).setValue(value);
            return;
        }
        throw new IllegalArgumentException("There is no attribute with name "+name);
    }

    public String getAttribute(String name){

        for(int i=0; i < attributes.size() ; i ++ ){
            if( attributes.get(i).getName().equals(name))
                return attributes.get(i).getValue();
        }
        throw new IllegalArgumentException("There is no attribute with name "+name);
    }

    public void add(Node node){
        subNodes.add(node);
    }

    public Node get(int index){
        return subNodes.get(index);
    }

    public Node delete(int index){
        return subNodes.remove(index);
    }

    public LinkedList<Node> getSubElements(String name){
        LinkedList<Node> result = new LinkedList<>();

        for(int i=0 ; i<subNodes.size() ; i++){
            Node currentNode = subNodes.get(i);

            if(currentNode.getName().equals(name)){
                result.add(currentNode);
            }
            if(currentNode instanceof ElementNode){
                LinkedList<Node> targetResult = ((ElementNode)currentNode).getSubElements(name);

                result.addAll(targetResult);
            }
        }

        return result;
    }

    public String getText(){
        StringBuffer buffer = new StringBuffer();

        for(int i=0; i< subNodes.size();  i++){
            if(subNodes.get(i) instanceof TextNode)
                buffer.append(subNodes.get(i));
        }

        return buffer.toString();


    }


    public static class Attribute{
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
