package app;

import app.storage.ChefRepository;
import entities.Chef;
import entities.Recipe;
import frameworks.swing.HomeView;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private JFrame frame;
    private HomeView homeView;
    private RecipeViewController recipeViewController;
    private ChefRepository chefRepository;
    private Chef chef;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowUI());
    }

    private void createAndShowUI() {
        frame = new JFrame("Recipe Book");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chefRepository = new ChefRepository(resolveStoragePath());
        chef = loadChef();

        homeView = new HomeView();
        refreshRecipeList();

        recipeViewController = new RecipeViewController(frame, homeView, chef, chefRepository, this::refreshRecipeList);
        recipeViewController.showHome();

        homeView.setAddRecipeAction(e -> recipeViewController.showAddRecipeForm());

        homeView.setRecipeSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selection = homeView.getSelectedRecipe();
                if (selection != null) {
                    recipeViewController.showRecipeDetail(selection);
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Chef loadChef() {
        try {
            return chefRepository.loadChef();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to load recipes: " + e.getMessage(),
                    "Startup Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Failed to load chef data", e);
        }
    }

    private void refreshRecipeList() {
        if (homeView != null && chef != null) {
            homeView.setRecipes(extractRecipeNames());
        }
    }

    private List<String> extractRecipeNames() {
        return chef.getRecipes()
                .stream()
                .map(Recipe::getName)
                .collect(Collectors.toList());
    }

    private Path resolveStoragePath() {
        String userHome = System.getProperty("user.home");
        return Path.of(userHome, ".recipe-book", "chef.json");
    }
}
