package entities;

import java.util.ArrayList;

public class recipeBuilder {
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String dietaryRestrictions;

    public recipeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public recipeBuilder setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public recipeBuilder setSteps(ArrayList<String> steps) {
        this.steps = steps;
        return this;
    }

    public recipeBuilder setPrepTime(int prepTime) {
        this.prepTime = prepTime;
        return this;
    }

    public recipeBuilder setCookTime(int cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public recipeBuilder setServings(int servings) {
        this.servings = servings;
        return this;
    }

    public recipeBuilder setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
        return this;
    }

    public Recipe createRecipe() {
        return new Recipe(name, ingredients, steps, prepTime, cookTime, servings, dietaryRestrictions);
    }
}