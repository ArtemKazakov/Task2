package com.epam.task2.tool;

import com.epam.task2.entity.Food;

import java.util.List;

/**
 * Created by ASUS on 30.09.2016.
 */
public class Printer {

    public static void printFood(List<Food> menu){
        for (Food food : menu) {
            System.out.println("----------");
            System.out.print("Name: ");
            System.out.println(food.getName());
            System.out.print("Type: ");
            System.out.println(food.getType());
            System.out.print("Image: ");
            System.out.println(food.getImage());
            System.out.print("Portion: ");
            System.out.println(food.getPortion().getValue() + food.getPortion().getMeasure());
            for(int i=0; i<food.getDescriptions().size(); i++){
                System.out.print("Description: ");
                System.out.print(food.getDescriptions().get(i));
                System.out.print(" Price: ");
                System.out.println(food.getPrices().get(i).getValue() + food.getPrices().get(i).getUnit());
            }
        }
    }
}
