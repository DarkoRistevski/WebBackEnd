package com.example.webproject.web.controllers;

import com.example.webproject.models.Recipe;
import com.example.webproject.service.RecipeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/recipes")
//@Profile("recipe-controller")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping("/upload")
    public ModelAndView starter(){
        ModelAndView modelAndView = new ModelAndView("uploadPhoto");
        return modelAndView;
    }
//
//    @PostMapping("/recipe/add")
//    public String addPhoto(@RequestParam("title") String title,
//                           @RequestParam("ingredients") String ingredients,
//                           @RequestParam("instructions") List<String> instructions,
//                           @RequestParam("image") MultipartFile image,
//                           Model model) throws IOException {
//        List<String> finalIngredients = Arrays.asList(ingredients.split(" "));
//
//        String id = recipeService.addRecipe(title, finalIngredients,instructions, image);
//        return "redirect:/recipes/recipe/" + id;
//    }

//    @GetMapping("/recipe/{id}")
//    public String getPhoto(@PathVariable String id, Model model) {
//        Recipe recipe = recipeService.getRecipe(id);
//        model.addAttribute("title", recipe.getTitle());
////        model.addAttribute("image",
////                Base64.getEncoder().encodeToString(recipe.getImage().getData()));
//        model.addAttribute("ingredients", recipe.getIngredients());
//        model.addAttribute("instructions", recipe.getInstructions().get(0));
//        return "photos";
//    }

}