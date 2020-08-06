package com.example.webproject.repository.jpa;

import com.example.webproject.models.Recipe;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    List<Recipe> findRecipeByIngredientsContaining(String ingredient, Pageable pageable);

    Page<Recipe> findRecipeByTitleOrIngredientsContaining(String title, List<String> ingredients, Pageable pageable);

}
