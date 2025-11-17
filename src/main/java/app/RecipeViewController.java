package app;

import app.storage.ChefRepository;
import entities.Chef;
import entities.Recipe;
import frameworks.swing.HomeView;
import frameworks.swing.RecipeDetailView;
import frameworks.swing.RecipeFormView;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Coordinates navigation between the home screen and the detail view.
 */
public class RecipeViewController {

    private final JFrame frame;
    private final HomeView homeView;
    private final Chef chef;
    private final ChefRepository chefRepository;
    private final Runnable onRecipesChanged;

    public RecipeViewController(JFrame frame,
                                HomeView homeView,
                                Chef chef,
                                ChefRepository chefRepository,
                                Runnable onRecipesChanged) {
        this.frame = frame;
        this.homeView = homeView;
        this.chef = chef;
        this.chefRepository = chefRepository;
        this.onRecipesChanged = onRecipesChanged == null ? () -> { } : onRecipesChanged;
    }

    public void showHome() {
        frame.setContentPane(homeView);
        homeView.clearSelection();
        frame.revalidate();
        frame.repaint();
    }

    public void showRecipeDetail(String recipeName) {
        Optional<Recipe> maybeRecipe = chef.getRecipes()
                .stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .findFirst();

        if (maybeRecipe.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Recipe recipe = maybeRecipe.get();
        RecipeDetailView detailView = new RecipeDetailView();
        detailView.setRecipeDetails(recipe.getName(),
                recipe.getIngredients(),
                recipe.getSteps(),
                recipe.getDietaryRestrictions(),
                formatTime(recipe));

        detailView.setBackAction(e -> showHome());
        detailView.setEditAction(e -> JOptionPane.showMessageDialog(frame, "Edit action for " + recipeName));
        detailView.setDeleteAction(e -> handleMutation(r -> r.removeRecipeByName(recipeName), null));

        setContent(detailView);
    }

    public void showAddRecipeForm() {
        RecipeFormView formView = buildRecipeForm("Add Recipe", null);
        formView.setSaveAction(e -> saveNewRecipe(formView));
        formView.setCancelAction(e -> showHome());
        setContent(formView);
    }

    private RecipeFormView buildRecipeForm(String title, Recipe existing) {
        RecipeFormView formView = new RecipeFormView();
        formView.setFormTitle(title);
        if (existing != null) {
            formView.setRecipeName(existing.getName());
            formView.setIngredients(String.join("\n", existing.getIngredients()));
            formView.setInstructions(String.join("\n", existing.getSteps()));
            formView.setDietaryRestrictions(existing.getDietaryRestrictions());
            formView.setPrepTime(String.valueOf(existing.getPrepTime()));
            formView.setCookTime(String.valueOf(existing.getCookTime()));
            formView.setServings(String.valueOf(existing.getServings()));
        }
        return formView;
    }

    private void saveNewRecipe(RecipeFormView formView) {
        Recipe recipe = buildRecipeFromForm(formView);
        if (recipe == null) {
            return;
        }
        boolean exists = chef.getRecipes()
                .stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(recipe.getName()));
        if (exists) {
            JOptionPane.showMessageDialog(frame,
                    "A recipe with that name already exists. Choose a different name.",
                    "Duplicate Recipe",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        handleMutation(c -> c.addRecipe(recipe), () -> showRecipeDetail(recipe.getName()));
    }

    private Recipe buildRecipeFromForm(RecipeFormView formView) {
        String name = formView.getRecipeName().trim();
        if (name.isEmpty()) {
            showValidationError("Recipe name is required.");
            return null;
        }

        Integer prep = parseInteger(formView.getPrepTime(), "Prep time");
        Integer cook = parseInteger(formView.getCookTime(), "Cook time");
        Integer servings = parseInteger(formView.getServings(), "Servings");
        if (prep == null || cook == null || servings == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setIngredients(splitLines(formView.getIngredients()));
        recipe.setSteps(splitLines(formView.getInstructions()));
        recipe.setDietaryRestrictions(formView.getDietaryRestrictions().trim());
        recipe.setPrepTime(prep);
        recipe.setCookTime(cook);
        recipe.setServings(servings);
        return recipe;
    }

    private Integer parseInteger(String value, String label) {
        String trimmed = value == null ? "" : value.trim();
        if (trimmed.isEmpty()) {
            showValidationError(label + " is required.");
            return null;
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException ex) {
            showValidationError(label + " must be a number.");
            return null;
        }
    }

    private List<String> splitLines(String text) {
        return text == null ? List.of() :
                text.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toList());
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
    }

    private String formatTime(Recipe recipe) {
        return String.format("Prep %d min · Cook %d min · Serves %d",
                recipe.getPrepTime(),
                recipe.getCookTime(),
                recipe.getServings());
    }

    private void handleMutation(Consumer<Chef> mutation, Runnable afterMutation) {
        mutation.accept(chef);
        persistChanges();
        onRecipesChanged.run();
        if (afterMutation != null) {
            afterMutation.run();
        } else {
            showHome();
        }
    }

    private void persistChanges() {
        try {
            chefRepository.saveChef(chef);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Failed to save recipes: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setContent(JComponent component) {
        frame.setContentPane(component);
        frame.revalidate();
        frame.repaint();
    }
}
