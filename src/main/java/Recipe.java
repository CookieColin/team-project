import java.util.*;

public class Recipe {

    private String name;
    private List<String> ingredients;
    private List<String> steps;
    // in minutes
    private int prepTime;
    // in minutes
    private int cookTime;

    private int servings;
    private String dietaryRestrictions;

    public Recipe(String name, List<String> ingredients, List<String> steps, int prepTime, int cookTime, int servings,
                  String dietaryRestrictions) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.dietaryRestrictions = dietaryRestrictions;
    }

}
