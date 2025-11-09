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

}
