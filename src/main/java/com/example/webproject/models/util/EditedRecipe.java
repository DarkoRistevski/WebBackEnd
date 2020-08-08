package com.example.webproject.models.util;

import lombok.Data;

@Data
public class EditedRecipe {

    private String id;
    private String title;
    private String ingredients;
    private String instructions;

    public EditedRecipe(){};

    public EditedRecipe(String id, String title, String ingredients, String instructions){
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

}
