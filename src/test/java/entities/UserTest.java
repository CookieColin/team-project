package entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    // all user parameters are initialized properly
    void testDefaultConstructor_initializesFields() {
        Chef chef = new Chef();
        assertNull(chef.getName());
        assertNotNull(chef.getRecipes());
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
    // if user has all fields filled, then each field should equal its parameter
    void testFullConstructor_setsFieldsCorrectly() {
        Recipe r = new Recipe("Salad", List.of("Lettuce"), List.of("Mix"), 2, 1, 1, "");
        List<Recipe> recipes = List.of(r);
        Chef chef = new Chef("Jamie Oliver", recipes);

        assertEquals("Jamie Oliver", chef.getName());
        assertEquals(1, chef.getRecipes().size());
        assertEquals("Salad", chef.getRecipes().get(0).getName());
    }

    @Test
    // getName and setName methods work for corresponding user Chef
    void testSetNameAndGetName() {
        Chef chef = new Chef();
        chef.setName("Gordon Ramsay");

        assertEquals("Gordon Ramsay", chef.getName());
    }

    @Test
    // setRecipe and getRecipe methods work for corresponding user Chef
    void testSetRecipesAndGetRecipes() {
        Recipe r1 = new Recipe("Toast", List.of("Bread"), List.of("Toast it"), 1, 2, 1, "vegan");
        Recipe r2 = new Recipe("Soup", List.of("Water"), List.of("Boil it"), 3, 10, 2, "");

        Chef chef = new Chef();
        chef.setRecipes(List.of(r1, r2));

        List<Recipe> recipes = chef.getRecipes();
        assertEquals(2, recipes.size());
        assertEquals("Toast", recipes.get(0).getName());
    }

    @Test
    // Creating a new user Chef with zero recipes results in an empty list of recipes for the Chef
    void testSetNullRecipesResultsInEmptyList() {
        Chef chef = new Chef();
        chef.setRecipes(null);

        assertNotNull(chef.getRecipes());
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
    // ensures that recipe is added properly to corresponding user Chef
    void testAddRecipe_addsSuccessfully() {
        Recipe recipe = new Recipe("Omelette", List.of("Eggs"), List.of("Cook"), 2, 3, 1, "");
        Chef chef = new Chef();

        chef.addRecipe(recipe);

        assertEquals(1, chef.getRecipes().size());
        assertEquals("Omelette", chef.getRecipes().get(0).getName());
    }

    @Test
    // ensures that a specific recipe for the corresponding user Chef is removed properly via "removeRecipeByName"
    void testRemoveRecipeByName_successfulRemoval() {
        Recipe r1 = new Recipe("Pasta", List.of("Noodles"), List.of("Boil"), 5, 10, 2, "");
        Recipe r2 = new Recipe("Pizza", List.of("Dough"), List.of("Bake"), 15, 20, 4, "");

        Chef chef = new Chef();
        chef.setRecipes(new ArrayList<>(List.of(r1, r2)));

        boolean removed = chef.removeRecipeByName("pasta");

        assertTrue(removed);
        assertEquals(1, chef.getRecipes().size());
        assertEquals("Pizza", chef.getRecipes().get(0).getName());
    }

    @Test
    // a recipe that was never added to corresponding user Chef cannot be removed
    void testRemoveRecipeByName_nonExistentRecipe() {
        Recipe r1 = new Recipe("Burger", List.of("Bun"), List.of("Grill"), 5, 7, 1, "");
        Chef chef = new Chef();
        chef.addRecipe(r1);

        boolean removed = chef.removeRecipeByName("Taco");

        assertFalse(removed);
        assertEquals(1, chef.getRecipes().size());
    }
    // gerRecipes and setRecipes for corresponding user Chef do not interfere
    @Test
    void testGetRecipesDefensiveInitialization() {
        Chef chef = new Chef();
        chef.setRecipes(null);
        assertNotNull(chef.getRecipes());
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
    void testAddMultipleRecipes() {
        Chef chef = new Chef();
        chef.addRecipe(new Recipe("R1", List.of(), List.of(), 1, 1, 1, ""));
        chef.addRecipe(new Recipe("R2", List.of(), List.of(), 2, 2, 2, ""));
        chef.addRecipe(new Recipe("R3", List.of(), List.of(), 3, 3, 3, ""));
        assertEquals(3, chef.getRecipes().size());
    }

    @Test
    void testChefNameCanBeEmpty() {
        Chef chef = new Chef("", new ArrayList<>());
        assertEquals("", chef.getName());
    }

    @Test
    void testRemoveRecipeByNameCaseInsensitive() {
        Chef chef = new Chef();
        chef.addRecipe(new Recipe("UPPERCASE", List.of(), List.of(), 1, 1, 1, ""));
        boolean removed = chef.removeRecipeByName("uppercase");
        assertTrue(removed);
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
    void testChefWithEmptyRecipeList() {
        Chef chef = new Chef("Empty Chef", new ArrayList<>());
        assertEquals("Empty Chef", chef.getName());
        assertTrue(chef.getRecipes().isEmpty());
    }
}
