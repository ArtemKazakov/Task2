package com.epam.task2.staxparser;

/**
 * Created by ASUS on 28.09.2016.
 */
public enum MenuTagName {
    NAME, IMAGE, PORTION, PRICE, DESCRIPTION, FOOD, MENU, DESCRIPTION_PRICE;

    public static MenuTagName getElementTagName(String element) {
        switch (element) {
            case "food":
                return FOOD;
            case "price":
                return PRICE;
            case "image":
                return IMAGE;
            case "description":
                return DESCRIPTION;
            case "portion":
                return PORTION;
            case "description-price":
                return DESCRIPTION_PRICE;
            case "name":
                return NAME;
            case "menu":
                return MENU;
            default:
                throw new EnumConstantNotPresentException(MenuTagName.class, element);
        }
    }

}