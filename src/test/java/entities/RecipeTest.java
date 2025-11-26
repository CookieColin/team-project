package entities;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
//    Checks that all text fields are filled
    void testFullConstructorSetsAllFields() {
        Recipe recipe = new Recipe(
                "Pancakes",
                List.of("Flour", "Eggs", "Milk"),
                List.of("Mix", "Cook", "Serve"),
                5,
                10,
                4,
                "vegetarian"
        );
        assertEquals("Pancakes", recipe.getName());
        assertEquals(List.of("Flour", "Eggs", "Milk"), recipe.getIngredients());
        assertEquals(List.of("Mix", "Cook", "Serve"), recipe.getSteps());
        assertEquals(5, recipe.getPrepTime());
        assertEquals(10, recipe.getCookTime());
        assertEquals(4, recipe.getServings());
        assertEquals("vegetarian", recipe.getDietaryRestrictions());
    }

    @Test
    // Constructing a recipe with no entries should be empty
    void testNoArgsConstructorHasDefaults() {
        Recipe recipe = new Recipe();
        assertNull(recipe.getName());
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getSteps());
        assertEquals(0, recipe.getPrepTime());
        assertEquals(0, recipe.getCookTime());
        assertEquals(0, recipe.getServings());
        assertNull(recipe.getDietaryRestrictions());
    }

    @Test
    // getting recipe and setting recipe tests
    void testSettersAndGetters() {
        Recipe recipe = new Recipe();
        recipe.setName("Omelette");
        recipe.setIngredients(List.of("Eggs", "Salt"));
        recipe.setSteps(List.of("Beat eggs", "Cook in pan"));
        recipe.setPrepTime(3);
        recipe.setCookTime(5);
        recipe.setServings(1);
        recipe.setDietaryRestrictions("keto");
        assertEquals("Omelette", recipe.getName());
        assertEquals(List.of("Eggs", "Salt"), recipe.getIngredients());
        assertEquals(List.of("Beat eggs", "Cook in pan"), recipe.getSteps());
        assertEquals(3, recipe.getPrepTime());
        assertEquals(5, recipe.getCookTime());
        assertEquals(1, recipe.getServings());
        assertEquals("keto", recipe.getDietaryRestrictions());
    }

    @Test
    // setting a recipe should not interfere with getting a recipe
    void testNullSafetyInSetters() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(null);
        recipe.setSteps(null);
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getSteps());
        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
        // getting recipes does not interfere with setting recipes
    void testNullProtectionInGetters() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(null);
        recipe.setSteps(null);
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getSteps());
    }

    @Test
    // test to ensure a copy is made when setting and getting recipe ingredients
    void testIngredientListIsDefensiveCopy() {
        List<String> original = List.of("Water", "Sugar");
        Recipe recipe = new Recipe();
        recipe.setIngredients(original);
        List<String> fromGetter = recipe.getIngredients();
        assertEquals(original, fromGetter);
        assertNotSame(original, fromGetter);
    }

    @Test
    // test to ensure copy of step number of a recipe is made
    void testStepsListIsDefensiveCopy() {
        List<String> steps = List.of("Step 1", "Step 2");
        Recipe recipe = new Recipe();
        recipe.setSteps(steps);
        List<String> fromGetter = recipe.getSteps();
        assertEquals(steps, fromGetter);
        assertNotSame(steps, fromGetter);
    }

    @Test
    // making a new recipe constructor makes the entries of recipe non-empty
    void testAlternateConstructorDoesNothing() {
        Recipe recipe = new Recipe("Test", "Step", List.of());
        assertNotNull(recipe);
    }


    @Test
    void testAlternativeConstructorDoesAlternative() {
        Recipe recipe = new Recipe("Test", "Step", List.of());
        assertEquals(null, recipe.getName());
        assertNotNull(recipe);
    }

    @Test
        // default ingredients and steps should start as empty lists
    void testDefaultIngredientsAndStepsAreEmpty() {
        Recipe recipe = new Recipe();

        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
        // dietary restrictions can be updated multiple times and last value is kept
    void testDietaryRestrictionsCanBeUpdated() {
        Recipe recipe = new Recipe();

        recipe.setDietaryRestrictions("vegan");
        recipe.setDietaryRestrictions("gluten-free");

        assertEquals("gluten-free", recipe.getDietaryRestrictions());
    }

    @Test
        // calling setIngredients again should replace previous contents
    void testSetIngredientsReplacesExistingList() {
        Recipe recipe = new Recipe();

        recipe.setIngredients(List.of("Flour"));
        recipe.setIngredients(List.of("Flour", "Sugar"));

        assertEquals(List.of("Flour", "Sugar"), recipe.getIngredients());
    }

    @Test
        // calling setSteps again should replace previous contents
    void testSetStepsReplacesExistingList() {
        Recipe recipe = new Recipe();

        recipe.setSteps(List.of("Step 1"));
        recipe.setSteps(List.of("Step 1", "Step 2"));

        assertEquals(List.of("Step 1", "Step 2"), recipe.getSteps());
    }

    @Test
        // prep and cook time can be set independently of each other
    void testPrepAndCookTimeSetIndependently() {
        Recipe recipe = new Recipe();

        recipe.setPrepTime(10);
        recipe.setCookTime(25);

        assertEquals(10, recipe.getPrepTime());
        assertEquals(25, recipe.getCookTime());
    }

    @Test
        // servings can be changed and last value is preserved
    void testServingsCanBeChanged() {
        Recipe recipe = new Recipe();

        recipe.setServings(2);
        recipe.setServings(5);

        assertEquals(5, recipe.getServings());
    }

    @Test
        // setting an empty ingredients list keeps list non-null and empty
    void testSetEmptyIngredientsList() {
        Recipe recipe = new Recipe();

        recipe.setIngredients(List.of());

        assertNotNull(recipe.getIngredients());
        assertTrue(recipe.getIngredients().isEmpty());
    }

    @Test
        // setting an empty steps list keeps list non-null and empty
    void testSetEmptyStepsList() {
        Recipe recipe = new Recipe();

        recipe.setSteps(List.of());

        assertNotNull(recipe.getSteps());
        assertTrue(recipe.getSteps().isEmpty());
    }

}
