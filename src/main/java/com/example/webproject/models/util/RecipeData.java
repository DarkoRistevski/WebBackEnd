package com.example.webproject.models.util;

import lombok.Data;

@Data
public class RecipeData {

    private String title;
    private String ingredients;
    private String instructions;

    public RecipeData(){};

    public RecipeData(String title, String ingredients, String instructions){
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

}
