package com.example.webproject.service;

import com.example.webproject.models.Recipe;
import com.example.webproject.models.exceptions.InvalidRecipeException;
import com.example.webproject.models.vm.Page;
import com.example.webproject.repository.RecipeRepositoryInterface;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepositoryInterface recipeRepository;

    public RecipeService(RecipeRepositoryInterface recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe addRecipe(String title, List<String> ingredients, List<String> instructions, MultipartFile file) throws IOException {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipe(String id) {
        return recipeRepository.findById(id);
    }

    public Page<Recipe> getAllRecipes(int page, int size){
        return recipeRepository.getAllRecipes(page, size);
    }

    public List<Recipe> searchRecipes(String ingredient, int page, int size){
        return recipeRepository.searchRecipes(ingredient, page, size);
    }

    public Page<Recipe> findRecipeByTitle(String title, List<String> ing, int page, int size){
        return recipeRepository.findByTitle(title, ing,  page, size);
    }

    public Recipe updateRecipe(String recipeId, String title, List<String> ingredients, List<String> instructions, Binary file) throws IOException {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(InvalidRecipeException::new);
        recipe.setTitle(title);
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        recipe.setImage(file);
        return recipeRepository.save(recipe);
    }

    public void deleteById(String recipeId){
        recipeRepository.deleteById(recipeId);
    }

}