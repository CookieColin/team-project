package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private String name;
    private List<String> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private int prepTime;
    private int cookTime;
    private int servings;
    private String dietaryRestrictions;

    public Recipe() {
    }

    public Recipe(String name,
                  List<String> ingredients,
                  List<String> steps,
                  int prepTime,
                  int cookTime,
                  int servings,
                  String dietaryRestrictions) {
        this.name = name;
        this.ingredients = new ArrayList<>(Objects.requireNonNullElse(ingredients, List.of()));
        this.steps = new ArrayList<>(Objects.requireNonNullElse(steps, List.of()));
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = new ArrayList<>(Objects.requireNonNullElse(ingredients, List.of()));
    }

    public List<String> getSteps() {
        if (steps == null) {
            steps = new ArrayList<>();
        }
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = new ArrayList<>(Objects.requireNonNullElse(steps, List.of()));
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }
}
