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
// Setting recipes should store a defensive copy and not the original list reference
    void testSetRecipesStoresDefensiveCopy() {
        Recipe r = new Recipe("A", List.of(), List.of(), 1, 1, 1, "");
        List<Recipe> original = new ArrayList<>(List.of(r));

        Chef chef = new Chef();
        chef.setRecipes(original);

        original.clear();  // mutate original

        assertEquals(1, chef.getRecipes().size());
    }



    @Test
// removeRecipeByName should be case-insensitive (already implied but test deeper)
    void testRemoveRecipeCaseInsensitivity() {
        Recipe r = new Recipe("Cake", List.of(), List.of(), 1, 1, 1, "");
        Chef chef = new Chef();
        chef.addRecipe(r);

        assertTrue(chef.removeRecipeByName("cAkE"));
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
// Removing recipe from empty list should return false
    void testRemoveFromEmptyList() {
        Chef chef = new Chef();
        assertFalse(chef.removeRecipeByName("Anything"));
    }

    @Test
// Removing one of many recipes should preserve the others
    void testRemoveOneOfMany() {
        Recipe a = new Recipe("A", List.of(), List.of(), 1, 1, 1, "");
        Recipe b = new Recipe("B", List.of(), List.of(), 1, 1, 1, "");
        Recipe c = new Recipe("C", List.of(), List.of(), 1, 1, 1, "");

        Chef chef = new Chef();
        chef.setRecipes(new ArrayList<>(List.of(a, b, c)));

        chef.removeRecipeByName("b");

        assertEquals(List.of(a, c), chef.getRecipes());
    }

    @Test
// Multiple consecutive removes should work correctly
    void testMultipleRemovals() {
        Recipe a = new Recipe("A", List.of(), List.of(), 1, 1, 1, "");
        Recipe b = new Recipe("B", List.of(), List.of(), 1, 1, 1, "");

        Chef chef = new Chef();
        chef.setRecipes(new ArrayList<>(List.of(a, b)));

        chef.removeRecipeByName("A");
        chef.removeRecipeByName("B");

        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
// Chef name should support an empty string
    void testSetEmptyName() {
        Chef chef = new Chef();
        chef.setName("");
        assertEquals("", chef.getName());
    }

    @Test
// Chef name should support whitespace
    void testWhitespaceName() {
        Chef chef = new Chef();
        chef.setName("   ");
        assertEquals("   ", chef.getName());
    }

    @Test
// Setting recipes multiple times should overwrite previous recipes
    void testOverwriteRecipes() {
        Recipe a = new Recipe("1", List.of(), List.of(), 1, 1, 1, "");
        Recipe b = new Recipe("2", List.of(), List.of(), 1, 1, 1, "");

        Chef chef = new Chef();
        chef.setRecipes(List.of(a));
        chef.setRecipes(List.of(b));

        assertEquals("2", chef.getRecipes().get(0).getName());
    }

    @Test
// Add multiple recipes sequentially
    void testAddRecipeMultipleTimes() {
        Chef chef = new Chef();

        chef.addRecipe(new Recipe("A", List.of(), List.of(), 1, 1, 1, ""));
        chef.addRecipe(new Recipe("B", List.of(), List.of(), 1, 1, 1, ""));

        assertEquals(2, chef.getRecipes().size());
    }

    @Test
// Chef should maintain the order recipes were added
    void testOrderPreserved() {
        Chef chef = new Chef();

        chef.addRecipe(new Recipe("A", List.of(), List.of(), 1, 1, 1, ""));
        chef.addRecipe(new Recipe("B", List.of(), List.of(), 1, 1, 1, ""));
        chef.addRecipe(new Recipe("C", List.of(), List.of(), 1, 1, 1, ""));

        assertEquals("A", chef.getRecipes().get(0).getName());
        assertEquals("C", chef.getRecipes().get(2).getName());
    }

    @Test
// Setting recipes to an empty list results in empty list
    void testSetEmptyRecipesList() {
        Chef chef = new Chef();
        chef.setRecipes(List.of());
        assertTrue(chef.getRecipes().isEmpty());
    }

    @Test
// Recipes list supports duplicates
    void testAllowDuplicateRecipes() {
        Recipe r = new Recipe("A", List.of(), List.of(), 1, 1, 1, "");

        Chef chef = new Chef();
        chef.setRecipes(List.of(r, r));

        assertEquals(2, chef.getRecipes().size());
    }



    @Test
// Recipes list remains independent from external list after multiple mutations
    void testListIndependenceAfterMutations() {
        List<Recipe> external = new ArrayList<>();
        external.add(new Recipe("A", List.of(), List.of(), 1, 1, 1, ""));

        Chef chef = new Chef();
        chef.setRecipes(external);

        external.clear();

        assertEquals(1, chef.getRecipes().size());
    }

    @Test
// Chef with long name is allowed
    void testLongChefName() {
        String longName = "A".repeat(200);
        Chef chef = new Chef(longName, List.of());
        assertEquals(longName, chef.getName());
    }

    @Test
// Removing recipe should not modify objects outside
    void testRemoveDoesNotModifyOtherRecipeObjects() {
        Recipe a = new Recipe("A", List.of(), List.of(), 1, 1, 1, "");
        Recipe b = new Recipe("B", List.of(), List.of(), 1, 1, 1, "");

        Chef chef = new Chef("Test", new ArrayList<>(List.of(a, b)));
        chef.removeRecipeByName("A");

        assertEquals("A", a.getName());
    }

    @Test
// Setting recipes should deep copy list but not clone objects
    void testSetRecipesDoesNotCloneRecipeObjects() {
        Recipe a = new Recipe("A", List.of(), List.of(), 1, 1, 1, "");
        List<Recipe> list = new ArrayList<>(List.of(a));

        Chef chef = new Chef();
        chef.setRecipes(list);

        assertSame(a, chef.getRecipes().get(0));  // same object reference expected
    }

    @Test
// AddRecipe should not allow mutation of internal list from outside
    void testAddRecipeListIndependence() {
        List<Recipe> external = new ArrayList<>();
        Chef chef = new Chef("Name", external);

        chef.addRecipe(new Recipe("X", List.of(), List.of(), 1, 1, 1, ""));
        assertEquals(1, chef.getRecipes().size());
    }

    @Test
// Removing a recipe that differs by trailing spaces should fail
    void testRemoveRecipeWithTrailingSpacesFails() {
        Recipe r = new Recipe("Soup", List.of(), List.of(), 1, 1, 1, "");
        Chef chef = new Chef();
        chef.addRecipe(r);

        assertFalse(chef.removeRecipeByName("Soup   "));
    }

    @Test
// Name retrieval after multiple sets
    void testMultipleNameUpdates() {
        Chef chef = new Chef();
        chef.setName("A");
        chef.setName("B");
        chef.setName("C");

        assertEquals("C", chef.getName());
    }

    @Test
// Adding many recipes should work
    void testAddManyRecipes() {
        Chef chef = new Chef();

        for (int i = 0; i < 100; i++) {
            chef.addRecipe(new Recipe("R" + i, List.of(), List.of(), 1, 1, 1, ""));
        }

        assertEquals(100, chef.getRecipes().size());
    }

    @Test
// Remove last recipe among many
    void testRemoveLastOfMany() {
        Chef chef = new Chef();
        for (int i = 0; i < 10; i++) {
            chef.addRecipe(new Recipe("R" + i, List.of(), List.of(), 1, 1, 1, ""));
        }

        chef.removeRecipeByName("R9");

        assertEquals(9, chef.getRecipes().size());
    }

    @Test
// Removing the first recipe among many
    void testRemoveFirstOfMany() {
        Chef chef = new Chef();
        for (int i = 0; i < 5; i++) {
            chef.addRecipe(new Recipe("R" + i, List.of(), List.of(), 1, 1, 1, ""));
        }

        chef.removeRecipeByName("R0");

        assertEquals("R1", chef.getRecipes().get(0).getName());
    }

    @Test
// Removing a recipe name with different spacing should not match
    void testRemoveRecipeWithExtraSpacesShouldNotMatch() {
        Recipe r = new Recipe("Cake", List.of(), List.of(), 1, 1, 1, "");
        Chef chef = new Chef();
        chef.addRecipe(r);

        assertFalse(chef.removeRecipeByName("  Cake"));
    }

    @Test
// getRecipes should never return null under any circumstances
    void testGetRecipesNeverNull() {
        Chef chef = new Chef("X", null);
        assertNotNull(chef.getRecipes());
    }

    @Test
// full constructor with null name should allow name to remain null
    void testFullConstructorNullNameAllowed() {
        Chef chef = new Chef(null, List.of());
        assertNull(chef.getName());
    }

}
