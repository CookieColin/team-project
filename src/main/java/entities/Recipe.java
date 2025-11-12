package entities;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients ;
    private ArrayList<String> steps;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String dietaryRestrictions;

    public Recipe(String name, ArrayList<String> ingredients, ArrayList<String> steps, int prepTime, int cookTime, int servings, String dietaryRestrictions) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getServings() {
        return servings;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setName(String name) {this.name = name;}

    public void setIngredients(ArrayList<String> ingredients) {this.ingredients = ingredients;}

    public void setSteps(ArrayList<String> steps) {this.steps = steps;}

    public void setPrepTime(int prepTime) {this.prepTime = prepTime;}

    public void setCookTime(int cookTime) {this.cookTime = cookTime;}

    public void setServings(int servings) {this.servings = servings;}

    public void setDietaryRestrictions(String dietaryRestrictions) {this.dietaryRestrictions = dietaryRestrictions;}

}
