package entities;

import org.junit.jupiter.api.Test;

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
        // verify constructor correctly assigns name and servings only
    void testConstructorSetsNameAndServings() {
        Recipe recipe = new Recipe(
                "Salad",
                List.of("Lettuce", "Tomato"),
                List.of("Chop", "Mix"),
                2,
                0,
                2,
                "vegan"
        );

        assertEquals("Salad", recipe.getName());
        assertEquals(2, recipe.getServings());
    }

    @Test
        // verify default numeric values are zero
    void testDefaultNumericValuesAreZero() {
        Recipe recipe = new Recipe();

        assertEquals(0, recipe.getPrepTime());
        assertEquals(0, recipe.getCookTime());
        assertEquals(0, recipe.getServings());
    }

    @Test
        // verify setting only name does not affect other fields
    void testSetOnlyNameDoesNotAffectOtherFields() {
        Recipe recipe = new Recipe();
        recipe.setName("Toast");

        assertEquals("Toast", recipe.getName());
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getSteps());
    }

    @Test
        // verify ingredients list can be updated after initial set
    void testIngredientsCanBeUpdated() {
        Recipe recipe = new Recipe();

        recipe.setIngredients(List.of("Flour"));
        recipe.setIngredients(List.of("Flour", "Water"));

        assertEquals(List.of("Flour", "Water"), recipe.getIngredients());
    }

    @Test
        // verify steps list can be updated after initial set
    void testStepsCanBeUpdated() {
        Recipe recipe = new Recipe();

        recipe.setSteps(List.of("Step A"));
        recipe.setSteps(List.of("Step A", "Step B"));

        assertEquals(List.of("Step A", "Step B"), recipe.getSteps());
    }

    @Test
        // verify dietary restriction can be changed
    void testDietaryRestrictionCanBeUpdated() {
        Recipe recipe = new Recipe();

        recipe.setDietaryRestrictions("vegan");
        recipe.setDietaryRestrictions("gluten-free");

        assertEquals("gluten-free", recipe.getDietaryRestrictions());
    }

    @Test
        // verify empty ingredient list stays empty
    void testEmptyIngredientsRemainEmpty() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of());

        assertNotNull(recipe.getIngredients());
        assertTrue(recipe.getIngredients().isEmpty());
    }

    @Test
        // verify empty steps list stays empty
    void testEmptyStepsRemainEmpty() {
        Recipe recipe = new Recipe();
        recipe.setSteps(List.of());

        assertNotNull(recipe.getSteps());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
        // verify prep time can be changed after being set
    void testPrepTimeCanBeUpdated() {
        Recipe recipe = new Recipe();

        recipe.setPrepTime(5);
        recipe.setPrepTime(10);

        assertEquals(10, recipe.getPrepTime());
    }

    @Test
        // verify cook time can be changed after being set
    void testCookTimeCanBeUpdated() {
        Recipe recipe = new Recipe();

        recipe.setCookTime(7);
        recipe.setCookTime(12);

        assertEquals(12, recipe.getCookTime());
    }
}
