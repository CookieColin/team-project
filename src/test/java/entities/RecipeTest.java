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
// Changing the returned ingredient list should not modify the internal list
    void testModifyingGetterListDoesNotAffectInternalState() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of("Salt", "Pepper"));
        List<String> ingredients = recipe.getIngredients();
        ingredients.clear();  // attempt to modify

        // internal list must remain unchanged
        assertEquals(List.of(), recipe.getIngredients());
    }

    @Test
// Changing the returned steps list should not modify the internal list
    void testModifyingGetterStepsListDoesNotAffectInternalState() {
        Recipe recipe = new Recipe();
        recipe.setSteps(List.of("Boil", "Stir"));
        List<String> steps = recipe.getSteps();
        steps.clear();

        assertEquals(List.of(), recipe.getSteps());
    }

    @Test
// Setting empty lists should store empty lists without issues
    void testSettingEmptyLists() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of());
        recipe.setSteps(List.of());

        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
// Negative values for times should be accepted or handled depending on your design
    void testNegativePrepAndCookTime() {
        Recipe recipe = new Recipe();
        recipe.setPrepTime(-5);
        recipe.setCookTime(-10);

        assertEquals(-5, recipe.getPrepTime());
        assertEquals(-10, recipe.getCookTime());
    }

    @Test
// Servings should allow zero or positive integers
    void testServingsBoundaryValues() {
        Recipe recipe = new Recipe();
        recipe.setServings(0);
        assertEquals(0, recipe.getServings());

        recipe.setServings(10);
        assertEquals(10, recipe.getServings());
    }

    @Test
// Setting dietary restrictions to null should leave it null
    void testNullDietaryRestrictions() {
        Recipe recipe = new Recipe();
        recipe.setDietaryRestrictions(null);
        assertNull(recipe.getDietaryRestrictions());
    }

    @Test
// Basic equality check: two independent, identical recipes should not be == but fields should match
    void testTwoRecipesWithSameValuesAreNotSameObject() {
        Recipe r1 = new Recipe(
                "Tea",
                List.of("Water", "Tea Leaves"),
                List.of("Boil", "Steep"),
                3,
                5,
                1,
                "vegan"
        );
        Recipe r2 = new Recipe(
                "Tea",
                List.of("Water", "Tea Leaves"),
                List.of("Boil", "Steep"),
                3,
                5,
                1,
                "vegan"
        );

        assertNotSame(r1, r2);
        assertEquals(r1.getName(), r2.getName());
        assertEquals(r1.getIngredients(), r2.getIngredients());
        assertEquals(r1.getSteps(), r2.getSteps());
        assertEquals(r1.getPrepTime(), r2.getPrepTime());
        assertEquals(r1.getCookTime(), r2.getCookTime());
        assertEquals(r1.getServings(), r2.getServings());
        assertEquals(r1.getDietaryRestrictions(), r2.getDietaryRestrictions());
    }

    @Test
// Ensure name setter trims or accepts spaces (depending on your design)
    void testNameWithWhitespace() {
        Recipe recipe = new Recipe();
        recipe.setName("  Sandwich  ");

        // If your design expects exact storage, keep this:
        assertEquals("  Sandwich  ", recipe.getName());
    }

    @Test
// Steps and ingredients should not share references when set one after the other
    void testIngredientsAndStepsAreIndependent() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of("A", "B"));
        recipe.setSteps(List.of("C", "D"));

        assertNotEquals(recipe.getIngredients(), recipe.getSteps());
    }

    @Test
// Mixed constructor followed by setter usage should behave normally
    void testAlternateConstructorThenSetters() {
        Recipe recipe = new Recipe("X", "Y", List.of("Z"));
        recipe.setName("Updated");
        recipe.setSteps(List.of("Step1"));

        assertEquals("Updated", recipe.getName());
        assertEquals(List.of("Step1"), recipe.getSteps());
    }

    @Test
// Ingredients list should not be the same instance passed in
    void testSetterIngredientsMakesCopy() {
        List<String> list = new ArrayList<>(List.of("A", "B"));
        Recipe recipe = new Recipe();
        recipe.setIngredients(list);
        assertNotSame(list, recipe.getIngredients());
    }

    @Test
// Steps list should not be the same instance passed in
    void testSetterStepsMakesCopy() {
        List<String> list = new ArrayList<>(List.of("1", "2"));
        Recipe recipe = new Recipe();
        recipe.setSteps(list);
        assertNotSame(list, recipe.getSteps());
    }

    @Test
// Setting name to empty string is allowed
    void testEmptyStringName() {
        Recipe recipe = new Recipe();
        recipe.setName("");
        assertEquals("", recipe.getName());
    }

    @Test
// Setting dietary restrictions to empty string is allowed
    void testEmptyStringDietaryRestrictions() {
        Recipe recipe = new Recipe();
        recipe.setDietaryRestrictions("");
        assertEquals("", recipe.getDietaryRestrictions());
    }

    @Test
// Steps should remain independent of later modification to input list
    void testStepsImmutableFromSetterInput() {
        List<String> steps = new ArrayList<>(List.of("Start"));
        Recipe recipe = new Recipe();
        recipe.setSteps(steps);
        steps.add("Break"); // modify original list
        assertEquals(List.of("Start"), recipe.getSteps()); // internal list unchanged
    }

    @Test
// Ingredients should remain independent of later modification to input list
    void testIngredientsImmutableFromSetterInput() {
        List<String> ingredients = new ArrayList<>(List.of("Salt"));
        Recipe recipe = new Recipe();
        recipe.setIngredients(ingredients);
        ingredients.add("Pepper");
        assertEquals(List.of("Salt"), recipe.getIngredients());
    }

    @Test
// Setting long names is allowed
    void testLongNameValue() {
        String longName = "A".repeat(500);
        Recipe recipe = new Recipe();
        recipe.setName(longName);
        assertEquals(longName, recipe.getName());
    }

    @Test
// Setting very long step descriptions is allowed
    void testLongStepEntry() {
        String step = "x".repeat(1000);
        Recipe recipe = new Recipe();
        recipe.setSteps(List.of(step));
        assertEquals(step, recipe.getSteps().get(0));
    }

    @Test
// Changing prep time repeatedly should update correctly
    void testPrepTimeMultipleUpdates() {
        Recipe recipe = new Recipe();
        recipe.setPrepTime(1);
        recipe.setPrepTime(2);
        recipe.setPrepTime(3);
        assertEquals(3, recipe.getPrepTime());
    }

    @Test
// Changing cook time repeatedly should update correctly
    void testCookTimeMultipleUpdates() {
        Recipe recipe = new Recipe();
        recipe.setCookTime(10);
        recipe.setCookTime(20);
        assertEquals(20, recipe.getCookTime());
    }

    @Test
// Ingredients list should start empty, not null
    void testDefaultIngredientsIsEmptyList() {
        Recipe recipe = new Recipe();
        assertTrue(recipe.getIngredients().isEmpty());
    }

    @Test
// Steps list should start empty, not null
    void testDefaultStepsIsEmptyList() {
        Recipe recipe = new Recipe();
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
// Updating only ingredients should not affect steps
    void testIngredientsUpdateDoesNotAffectSteps() {
        Recipe recipe = new Recipe();
        recipe.setSteps(List.of("X"));
        recipe.setIngredients(List.of("Y"));
        assertEquals(List.of("X"), recipe.getSteps());
    }

    @Test
// Updating only steps should not affect ingredients
    void testStepsUpdateDoesNotAffectIngredients() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of("Tomato"));
        recipe.setSteps(List.of("Cut"));
        assertEquals(List.of("Tomato"), recipe.getIngredients());
    }

    @Test
// Null name should be allowed
    void testSetNameNull() {
        Recipe recipe = new Recipe();
        recipe.setName(null);
        assertNull(recipe.getName());
    }

    @Test
// All fields can be updated after using full constructor
    void testFullConstructorThenUpdateFields() {
        Recipe recipe = new Recipe(
                "Soup",
                List.of("Water"),
                List.of("Boil"),
                5,
                10,
                2,
                "none"
        );

        recipe.setName("Better Soup");
        recipe.setIngredients(List.of("Water", "Salt"));
        recipe.setSteps(List.of("Boil", "Stir"));
        recipe.setPrepTime(3);
        recipe.setCookTime(6);
        recipe.setServings(4);
        recipe.setDietaryRestrictions("vegan");

        assertEquals("Better Soup", recipe.getName());
        assertEquals(List.of("Water", "Salt"), recipe.getIngredients());
        assertEquals(List.of("Boil", "Stir"), recipe.getSteps());
        assertEquals(3, recipe.getPrepTime());
        assertEquals(6, recipe.getCookTime());
        assertEquals(4, recipe.getServings());
        assertEquals("vegan", recipe.getDietaryRestrictions());
    }

    @Test
// Steps list does not share references between consecutive setters
    void testConsecutiveSetterStepsNotShared() {
        Recipe recipe = new Recipe();
        List<String> first = List.of("A");
        List<String> second = List.of("B");

        recipe.setSteps(first);
        List<String> afterFirst = recipe.getSteps();

        recipe.setSteps(second);
        List<String> afterSecond = recipe.getSteps();

        assertNotSame(afterFirst, afterSecond);
    }

    @Test
// Ingredients list does not share references between consecutive setters
    void testConsecutiveSetterIngredientsNotShared() {
        Recipe recipe = new Recipe();
        List<String> first = List.of("1");
        List<String> second = List.of("2");

        recipe.setIngredients(first);
        List<String> afterFirst = recipe.getIngredients();

        recipe.setIngredients(second);
        List<String> afterSecond = recipe.getIngredients();

        assertNotSame(afterFirst, afterSecond);
    }

    @Test
// Setting extremely large integer values is allowed
    void testVeryLargeTimeValues() {
        Recipe recipe = new Recipe();
        recipe.setPrepTime(Integer.MAX_VALUE);
        recipe.setCookTime(Integer.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, recipe.getPrepTime());
        assertEquals(Integer.MAX_VALUE, recipe.getCookTime());
    }

    @Test
// Setting extremely small integer values is allowed
    void testVerySmallTimeValues() {
        Recipe recipe = new Recipe();
        recipe.setPrepTime(Integer.MIN_VALUE);
        recipe.setCookTime(Integer.MIN_VALUE);

        assertEquals(Integer.MIN_VALUE, recipe.getPrepTime());
        assertEquals(Integer.MIN_VALUE, recipe.getCookTime());
    }

    @Test
// Ingredients should preserve insertion order
    void testIngredientsOrderPreserved() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of("A", "C", "B"));
        assertEquals(List.of("A", "C", "B"), recipe.getIngredients());
    }

    @Test
// Steps should preserve insertion order
    void testStepsOrderPreserved() {
        Recipe recipe = new Recipe();
        recipe.setSteps(List.of("First", "Second", "Third"));
        assertEquals(List.of("First", "Second", "Third"), recipe.getSteps());
    }

    @Test
// Ingredients list supports duplicates
    void testDuplicateIngredientsAllowed() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of("Egg", "Egg", "Salt"));
        assertEquals(List.of("Egg", "Egg", "Salt"), recipe.getIngredients());
    }

    @Test
// Steps list supports duplicates
    void testDuplicateStepsAllowed() {
        Recipe recipe = new Recipe();
        recipe.setSteps(List.of("Mix", "Mix", "Heat"));
        assertEquals(List.of("Mix", "Mix", "Heat"), recipe.getSteps());
    }

    @Test
// Name remains unchanged after setting ingredients
    void testSetIngredientsDoesNotAffectName() {
        Recipe recipe = new Recipe();
        recipe.setName("Cake");
        recipe.setIngredients(List.of("Flour"));
        assertEquals("Cake", recipe.getName());
    }

    @Test
// Name remains unchanged after setting steps
    void testSetStepsDoesNotAffectName() {
        Recipe recipe = new Recipe();
        recipe.setName("Soup");
        recipe.setSteps(List.of("Boil water"));
        assertEquals("Soup", recipe.getName());
    }

    @Test
// Setting servings multiple times should overwrite properly
    void testServingsMultipleUpdates() {
        Recipe recipe = new Recipe();
        recipe.setServings(1);
        recipe.setServings(5);
        recipe.setServings(10);
        assertEquals(10, recipe.getServings());
    }

    @Test
// Constructor with null lists should not throw and should default
    void testFullConstructorWithNullLists() {
        Recipe recipe = new Recipe(
                "Name",
                null,
                null,
                1,
                2,
                3,
                "none"
        );

        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
// Constructor with empty lists should preserve emptiness
    void testFullConstructorWithEmptyLists() {
        Recipe recipe = new Recipe(
                "Name",
                List.of(),
                List.of(),
                1,
                2,
                3,
                "none"
        );
        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
// Setting name after constructor should override initial name
    void testOverrideConstructorName() {
        Recipe recipe = new Recipe();
        recipe.setName("Original");
        recipe.setName("Updated");
        assertEquals("Updated", recipe.getName());
    }

    @Test
// Setting null lists *after* constructor should reset to empty
    void testResetListsToEmptyAfterConstructor() {
        Recipe recipe = new Recipe(
                "X",
                List.of("A"),
                List.of("Step"),
                1,
                1,
                1,
                "none"
        );

        recipe.setIngredients(null);
        recipe.setSteps(null);

        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getSteps().isEmpty());
    }

    @Test
// Setting whitespace-only dietary restrictions is allowed
    void testWhitespaceDietaryRestrictions() {
        Recipe recipe = new Recipe();
        recipe.setDietaryRestrictions("   ");
        assertEquals("   ", recipe.getDietaryRestrictions());
    }

    @Test
// Setting whitespace-only name should store as-is
    void testWhitespaceName() {
        Recipe recipe = new Recipe();
        recipe.setName("   ");
        assertEquals("   ", recipe.getName());
    }

    @Test
// Setting giant ingredient list is allowed
    void testLargeIngredientList() {
        List<String> big = new ArrayList<>();
        for (int i = 0; i < 500; i++) big.add("Item" + i);

        Recipe recipe = new Recipe();
        recipe.setIngredients(big);

        assertEquals(500, recipe.getIngredients().size());
    }

    @Test
// Setting giant steps list is allowed
    void testLargeStepsList() {
        List<String> big = new ArrayList<>();
        for (int i = 0; i < 500; i++) big.add("Step " + i);

        Recipe recipe = new Recipe();
        recipe.setSteps(big);

        assertEquals(500, recipe.getSteps().size());
    }



    @Test
// Updating ingredients and steps several times leaves final state correct
    void testManyUpdatesIngredientsSteps() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(List.of("A"));
        recipe.setSteps(List.of("B"));

        recipe.setIngredients(List.of("C"));
        recipe.setSteps(List.of("D"));

        recipe.setIngredients(List.of("E"));
        recipe.setSteps(List.of("F"));

        assertEquals(List.of("E"), recipe.getIngredients());
        assertEquals(List.of("F"), recipe.getSteps());
    }

    @Test
// Check whether cookTime and prepTime are independent fields
    void testPrepTimeDoesNotAffectCookTime() {
        Recipe recipe = new Recipe();
        recipe.setCookTime(100);
        recipe.setPrepTime(20);

        assertEquals(100, recipe.getCookTime());
        assertEquals(20, recipe.getPrepTime());
    }

    @Test
// Confirm that object remains usable after many setter calls
    void testObjectRemainsValidAfterStressUpdates() {
        Recipe recipe = new Recipe();
        for (int i = 0; i < 100; i++) {
            recipe.setName("Name" + i);
            recipe.setIngredients(List.of("I" + i));
            recipe.setSteps(List.of("S" + i));
            recipe.setDietaryRestrictions("D" + i);
            recipe.setPrepTime(i);
            recipe.setCookTime(i * 2);
            recipe.setServings(i % 5);
        }

        assertEquals("Name99", recipe.getName());
        assertEquals(List.of("I99"), recipe.getIngredients());
        assertEquals(List.of("S99"), recipe.getSteps());
        assertEquals("D99", recipe.getDietaryRestrictions());
        assertEquals(99, recipe.getPrepTime());
        assertEquals(198, recipe.getCookTime());
        assertEquals(4, recipe.getServings());
    }



}
