package com.example.webproject.repository;

import com.example.webproject.models.Recipe;
import com.example.webproject.models.vm.Page;

import java.util.List;
import java.util.Optional;

public interface RecipeRepositoryInterface {

    Recipe save(Recipe recipe);

    Page<Recipe> getAllRecipes(int page, int size);

    List<Recipe> searchRecipes(String term, int page, int size);

    Page<Recipe> findByTitle(String title, List<String> ing, int page, int size);

    Optional<Recipe> findById(String recipeId);

    void deleteById(String recipeId);

}
