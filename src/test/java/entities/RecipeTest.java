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
    void testAlternativeConstructorDoesAlternative() {
        Recipe recipe = new Recipe("Test", "Step", List.of());
        assertEquals(null, recipe.getName());
        assertNotNull(recipe);
    }
}
