package com.example.webproject.web.rest;

import com.example.webproject.Security.CustomAuthenticationManager;
import com.example.webproject.models.Recipe;
import com.example.webproject.models.exceptions.InvalidRecipeException;
import com.example.webproject.models.security.AuthenticationRequest;
import com.example.webproject.models.security.AuthenticationResponse;
import com.example.webproject.models.util.RecipeData;
import com.example.webproject.models.vm.Page;
import com.example.webproject.service.RecipeService;
import com.example.webproject.service.security.MyUserDetailsService;
import com.example.webproject.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeApi {

    private final RecipeService recipeService;

    @Autowired
    private CustomAuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    public RecipeApi(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @GetMapping()
    public Page<Recipe> getAllRecipes(@RequestHeader(name = "page", defaultValue = "0") int page,
                                      @RequestHeader(name = "page-size", defaultValue = "10") int size){

        return recipeService.getAllRecipes(page, size);

    }

    @GetMapping("/{recipeId}")
    public Optional<Recipe> getRecipe(@PathVariable String recipeId){
        return recipeService.getRecipe(recipeId);
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestHeader String ingredient,
                                      @RequestHeader int page,
                                      @RequestHeader int size){
        return recipeService.searchRecipes(ingredient, page, size);
    }

    @GetMapping("/title")
    public Page<Recipe> searchRecipeByTitle(@RequestHeader String title,
                                            @RequestHeader int page,
                                            @RequestHeader int size){
        List<String> ing = new ArrayList<>();
        ing.add(title);
        return recipeService.findRecipeByTitle(title, ing, page, size);
    }

    @DeleteMapping("/delete/{recipeId}")
    public void deleteRecipe(@PathVariable String recipeId) {
        recipeService.deleteById(recipeId);
    }

    @GetMapping("/image/{recipeId}")
    public byte[] getImage(@PathVariable String recipeId){
        Recipe recipe = recipeService.getRecipe(recipeId).orElseThrow(InvalidRecipeException::new);
        return recipe.getImage().getData();
    }

    @PostMapping("/add")
    public Recipe addPhoto(@RequestParam("recipeData") String recipeData,
                         @RequestParam("image") MultipartFile image) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RecipeData recipe = mapper.readValue(recipeData, RecipeData.class);

        String title = recipe.getTitle();
        String ingredients = recipe.getIngredients();
        String instructions = recipe.getInstructions();

        List<String> semiFinalIngredients = Arrays.asList(ingredients.split(";"));
        semiFinalIngredients = semiFinalIngredients.stream().map(i -> i.trim()).collect(Collectors.toList());

        List<String> semiFinalInstructions = Arrays.asList(instructions.split(";"));
        semiFinalInstructions = semiFinalInstructions.stream().map(i -> i.trim()).collect(Collectors.toList());

        List<String> finalIngredients = semiFinalIngredients.stream().filter(i -> !i.equals("")).collect(Collectors.toList());
        List<String> finalInstructions = semiFinalInstructions.stream().filter(i -> !i.equals("")).collect(Collectors.toList());

        return recipeService.addRecipe(title, finalIngredients, finalInstructions, image);

    }

    @PatchMapping("/update")
    public Recipe updateRecipe(@RequestParam("id") String id,
                               @RequestParam("title") String title,
                               @RequestParam("ingredients") String ingredients,
                               @RequestParam("instructions") String instructions){

        ingredients = ingredients.trim();
        instructions = instructions.trim();

        List<String> finalIngredients = Arrays.asList(ingredients.split(";"));
        List<String> finalInstructions = Arrays.asList(instructions.split("\\."));
        finalInstructions.stream().forEach(s -> s.trim());

        return recipeService.updateRecipe(id, title, finalIngredients, finalInstructions);

    }


}
