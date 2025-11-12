package entities;

import java.util.ArrayList;

public class RecipeBuilder {
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String dietaryRestrictions;

    public RecipeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeBuilder setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public RecipeBuilder setSteps(ArrayList<String> steps) {
        this.steps = steps;
        return this;
    }

    public RecipeBuilder setPrepTime(int prepTime) {
        this.prepTime = prepTime;
        return this;
    }

    public RecipeBuilder setCookTime(int cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public RecipeBuilder setServings(int servings) {
        this.servings = servings;
        return this;
    }

    public RecipeBuilder setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
        return this;
    }

    public Recipe createRecipe() {
        return new Recipe(name, ingredients, steps, prepTime, cookTime, servings, dietaryRestrictions);
    }
}