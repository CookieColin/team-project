package frameworks;

import org.json.JSONArray;
import org.json.JSONObject;
import entities.Recipe;
import entities.RecipeBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SpoonacularAPI {

    public static void main(String[] args) throws IOException {
        HttpURLConnection conn = fetchAPIResponse();
        Recipe recipe = parseApiResponse(conn);
        System.out.println(recipe);

    }

    public static Recipe parseApiResponse(HttpURLConnection connection) throws IOException {
        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray recipes = jsonResponse.getJSONArray("recipes");
        JSONObject recipe = recipes.getJSONObject(0);

        String title = recipe.getString("title");
        JSONArray ingredients = recipe.getJSONArray("extendedIngredients");
        JSONArray instructions = recipe.getJSONArray("analyzedInstructions");

        ArrayList<String> ingredientsList = new ArrayList<>();
        ArrayList<String> stepsList = new ArrayList<>();

        int prepTime = recipe.optInt("preparationMinutes", 0);
        int cookTime = recipe.optInt("readyInMinutes", 0);

        for (int i = 0; i < ingredients.length(); i++) {
            JSONObject ingredient = ingredients.getJSONObject(i);
            ingredientsList.add(ingredient.getString("original"));
        }

        if (!instructions.isEmpty()) {
            JSONArray steps = instructions.getJSONObject(0).getJSONArray("steps");
            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                stepsList.add(step.getString("step"));
            }
        }

        Recipe recipeObj = createRecipeFromJson(title, ingredientsList, stepsList, prepTime, cookTime, recipe);
        return recipeObj;
    }

    private static Recipe createRecipeFromJson(String title, ArrayList<String> ingredientsList, ArrayList<String> stepsList, int prepTime, int cookTime, JSONObject recipe) {
        return new RecipeBuilder()
                .setName(title)
                .setIngredients(ingredientsList)
                .setSteps(stepsList)
                .setPrepTime(prepTime)
                .setCookTime(cookTime)
                .setServings(recipe.optInt("servings", 0))
                .setDietaryRestrictions(recipe.optString("diets", ""))
                .createRecipe();
    }

    private static HttpURLConnection fetchAPIResponse() {
        try {
            URL url = new URL("https://api.spoonacular.com/recipes/random?apiKey=8000479eb88043f08c5f87a9f1b2fb0b");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}