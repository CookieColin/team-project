package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chef {
    private String name;
    private List<Recipe> recipes = new ArrayList<>();

    public Chef() {
    }

    public Chef(String name, List<Recipe> recipes) {
        this.name = name;
        this.recipes = new ArrayList<>(Objects.requireNonNullElse(recipes, List.of()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        if (recipes == null) {
            recipes = new ArrayList<>();
        }
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = new ArrayList<>(Objects.requireNonNullElse(recipes, List.of()));
    }

    public void addRecipe(Recipe recipe) {
        getRecipes().add(recipe);
    }

    public boolean removeRecipeByName(String recipeName) {
        return getRecipes().removeIf(recipe -> recipe.getName().equalsIgnoreCase(recipeName));
    }
}
