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
        // default constructor should create a Chef with an empty recipe list that can accept new recipes
    void testDefaultConstructor_allowsAddingRecipes() {
        Chef chef = new Chef();
        Recipe recipe = new Recipe("Porridge",
                List.of("Oats"), List.of("Boil"), 3, 5, 1, "");

        assertTrue(chef.getRecipes().isEmpty());

        chef.addRecipe(recipe);

        assertEquals(1, chef.getRecipes().size());
        assertEquals("Porridge", chef.getRecipes().get(0).getName());
    }

    @Test
        // full constructor should correctly store multiple recipes
    void testFullConstructor_storesMultipleRecipes() {
        Recipe r1 = new Recipe("Salad", List.of("Lettuce"), List.of("Mix"), 2, 1, 1, "");
        Recipe r2 = new Recipe("Sandwich", List.of("Bread"), List.of("Assemble"), 3, 0, 1, "");

        List<Recipe> recipes = List.of(r1, r2);
        Chef chef = new Chef("Nigella", recipes);

        assertEquals("Nigella", chef.getName());
        assertEquals(2, chef.getRecipes().size());
        assertEquals("Salad", chef.getRecipes().get(0).getName());
        assertEquals("Sandwich", chef.getRecipes().get(1).getName());
    }

    @Test
        // updating recipes should replace the old list contents
    void testSetRecipes_replacesPreviousRecipes() {
        Chef chef = new Chef();

        Recipe r1 = new Recipe("Tea", List.of("Water"), List.of("Boil"), 1, 5, 1, "");
        chef.setRecipes(List.of(r1));
        assertEquals(1, chef.getRecipes().size());

        Recipe r2 = new Recipe("Coffee", List.of("Beans"), List.of("Brew"), 2, 4, 1, "");
        Recipe r3 = new Recipe("Juice", List.of("Fruit"), List.of("Squeeze"), 3, 0, 1, "");
        chef.setRecipes(List.of(r2, r3));

        assertEquals(2, chef.getRecipes().size());
        assertEquals("Coffee", chef.getRecipes().get(0).getName());
        assertEquals("Juice", chef.getRecipes().get(1).getName());
    }

    @Test
        // adding a recipe when some are already present should append to the list
    void testAddRecipe_appendsAfterExistingOnes() {
        Chef chef = new Chef();
        Recipe r1 = new Recipe("Toast", List.of("Bread"), List.of("Toast it"), 1, 2, 1, "");
        Recipe r2 = new Recipe("Jam Toast", List.of("Bread", "Jam"), List.of("Toast and spread"), 2, 3, 1, "");

        chef.setRecipes(new ArrayList<>(List.of(r1)));
        chef.addRecipe(r2);

        assertEquals(2, chef.getRecipes().size());
        assertEquals("Toast", chef.getRecipes().get(0).getName());
        assertEquals("Jam Toast", chef.getRecipes().get(1).getName());
    }

    @Test
        // removing a recipe should ignore case in the name comparison
    void testRemoveRecipeByName_ignoresCaseInName() {
        Recipe r1 = new Recipe("Curry", List.of("Spices"), List.of("Cook"), 15, 30, 4, "");
        Recipe r2 = new Recipe("Rice", List.of("Rice"), List.of("Boil"), 5, 15, 4, "");

        Chef chef = new Chef();
        chef.setRecipes(new ArrayList<>(List.of(r1, r2)));

        boolean removed = chef.removeRecipeByName("CURRY");

        assertTrue(removed);
        assertEquals(1, chef.getRecipes().size());
        assertEquals("Rice", chef.getRecipes().get(0).getName());
    }

    @Test
        // removing from an empty list should return false and keep list empty
    void testRemoveRecipeByName_emptyListReturnsFalse() {
        Chef chef = new Chef();

        boolean removed = chef.removeRecipeByName("Anything");

        assertFalse(removed);
        assertNotNull(chef.getRecipes());
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
        // setting recipes with an empty list should keep the internal list empty but non-null
    void testSetEmptyRecipesResultsInEmptyNonNullList() {
        Chef chef = new Chef();
        chef.setRecipes(new ArrayList<>());

        assertNotNull(chef.getRecipes());
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
        // name can be changed multiple times and always reflect the last assignment
    void testSetName_canBeUpdatedMultipleTimes() {
        Chef chef = new Chef();

        chef.setName("First Name");
        chef.setName("Second Name");

        assertEquals("Second Name", chef.getName());
    }
}
