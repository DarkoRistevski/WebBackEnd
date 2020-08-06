package com.example.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recipes2")
@Data
public class Recipe {

    @Id
    private String id;

    private List<String> ingredients;

    private String title;

    private List<String> instructions;

    private Binary image;

}
