package app;

import frameworks.swing.HomeView;
import frameworks.swing.RecipeDetailView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private final Map<String, RecipeData> recipes = seedRecipes();
    private JFrame frame;
    private HomeView homeView;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowUI());
    }

    private void createAndShowUI() {
        frame = new JFrame("Recipe Book");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        homeView = new HomeView();
        homeView.setRecipes(new ArrayList<>(recipes.keySet()));
        homeView.setRecipeSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selection = homeView.getSelectedRecipe();
                if (selection != null) {
                    showRecipeDetail(selection);
                }
            }
        });

        frame.setContentPane(homeView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showRecipeDetail(String recipeName) {
        RecipeData data = recipes.get(recipeName);
        if (data == null) {
            JOptionPane.showMessageDialog(frame, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RecipeDetailView detailView = new RecipeDetailView();
        detailView.setRecipeDetails(recipeName, data.ingredients, data.instructions, data.dietaryRestrictions, data.timeToPrepare);
        detailView.setBackAction(e -> showHome());
        detailView.setEditAction(e -> JOptionPane.showMessageDialog(frame, "Edit action for " + recipeName));
        detailView.setDeleteAction(e -> {
            recipes.remove(recipeName);
            showHome();
            homeView.setRecipes(new ArrayList<>(recipes.keySet()));
        });

        frame.setContentPane(detailView);
        frame.revalidate();
        frame.repaint();
    }

    private void showHome() {
        frame.setContentPane(homeView);
        homeView.clearSelection();
        frame.revalidate();
        frame.repaint();
    }

    private Map<String, RecipeData> seedRecipes() {
        Map<String, RecipeData> data = new LinkedHashMap<>();
        data.put("Roasted Veggie Bowl", new RecipeData(
                List.of(
                        "one medium sweet potato, peeled and cubed",
                        "one chopped red bell pepper",
                        "one zucchini, sliced into half-moons",
                        "one small red onion cut into wedges",
                        "one cup broccoli florets",
                        "two tbsp olive oil",
                        "one tsp salt",
                        "one tsp paprika (optional: smoked)",
                        "half a tsp of black pepper",
                        "one cup cooked quinoa or brown rice (base)"
                ),
                List.of(
                        "Preheat oven to 400°F (200°C).",
                        "Toss chopped vegetables with olive oil, salt, pepper, and paprika.",
                        "Spread evenly on a baking sheet lined with parchment paper.",
                        "Roast for 25–30 minutes, stirring halfway through.",
                        "Serve over quinoa or rice and top with tahini or your favorite dressing."
                ),
                "Vegan, Vegetarian, Gluten-free (if using certified GF grains), Dairy-free, No nuts",
                "45 minutes"
        ));
        data.put("Classic Chocolate Cake", new RecipeData(
                List.of("2 cups flour", "2 cups sugar", "3/4 cup cocoa powder"),
                List.of("Whisk dry ingredients.", "Bake at 350°F for 30 minutes."),
                "Contains gluten, dairy",
                "1 hour"
        ));
        data.put("Sheet-Pan Fajitas", new RecipeData(
                List.of("Chicken breast", "Peppers", "Onions", "Fajita seasoning"),
                List.of("Slice ingredients.", "Toss with seasoning.", "Bake for 20 minutes."),
                "High protein",
                "35 minutes"
        ));
        return data;
    }

    private static class RecipeData {
        private final List<String> ingredients;
        private final List<String> instructions;
        private final String dietaryRestrictions;
        private final String timeToPrepare;

        private RecipeData(List<String> ingredients,
                           List<String> instructions,
                           String dietaryRestrictions,
                           String timeToPrepare) {
            this.ingredients = ingredients;
            this.instructions = instructions;
            this.dietaryRestrictions = dietaryRestrictions;
            this.timeToPrepare = timeToPrepare;
        }
    }
}
