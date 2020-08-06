package com.example.webproject.repository.jpa;

import com.example.webproject.models.Recipe;
import com.example.webproject.models.vm.Page;
import com.example.webproject.repository.RecipeRepositoryInterface;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryInterface {

    private final RecipeRepository recipeRepository;

    public RecipeRepositoryImpl(@Lazy RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Page<Recipe> getAllRecipes(int page, int size) {
        org.springframework.data.domain.Page<Recipe> result = recipeRepository.findAll(PageRequest.of(page, size));

        return new Page<>(page,
                result.getTotalPages(),
                size,
                result.getContent());
    }

    @Override
    public Page<Recipe> findByTitle(String title, List<String> ing, int page, int size) {
        org.springframework.data.domain.Page<Recipe> result = recipeRepository.findRecipeByTitleOrIngredientsContaining(title, ing, PageRequest.of(page, size));
        return new Page<>(page,
                result.getTotalPages(),
                size,
                result.getContent());
    }

    @Override
    public List<Recipe> searchRecipes(String term, int page, int size) {
        return recipeRepository.findRecipeByIngredientsContaining(term, PageRequest.of(page, size));
    }

    @Override
    public Optional<Recipe> findById(String recipeId) {
        return recipeRepository.findById(recipeId);
    }

    @Override
    public void deleteById(String recipeId) {
        recipeRepository.deleteById(recipeId);
    }

}
