package entities;

import frameworks.SpoonacularAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class APITest {
    @Test
    // Checks that random recipe data can be reused
    public void testRandomRecipe() throws IOException {
        Recipe recipe1 = SpoonacularAPI.createRecipeFromJson();
        Recipe recipe2 = new Recipe();
        recipe2.setName(recipe1.getName());
        recipe2.setIngredients(recipe1.getIngredients());
        recipe2.setSteps(recipe1.getSteps());
        recipe2.setServings(recipe1.getServings());
        recipe2.setPrepTime(recipe1.getPrepTime());
        recipe2.setCookTime(recipe1.getCookTime());
        recipe2.setDietaryRestrictions(recipe1.getDietaryRestrictions());
        assert recipe1.getName().equals(recipe2.getName());
        assert recipe1.getIngredients().equals(recipe2.getIngredients());
        assert recipe1.getSteps().equals(recipe2.getSteps());
        assert recipe1.getServings() == recipe2.getServings();
        assert recipe1.getPrepTime() == recipe2.getPrepTime();
        assert recipe1.getCookTime() == recipe2.getCookTime();
        assert recipe1.getDietaryRestrictions().equals(recipe2.getDietaryRestrictions());


    }



}
