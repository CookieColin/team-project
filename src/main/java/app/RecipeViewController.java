package app;

import app.storage.ChefRepository;
import entities.Chef;
import entities.Recipe;
import frameworks.swing.HomeView;
import frameworks.swing.RecipeDetailView;
import frameworks.swing.RecipeFormView;

import javax.swing.*;
import java.io.IOException;
import java.util.Comparator;
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
    private String dietaryFilter = "Any";
    private SortOption sortOption = SortOption.NONE;

    public RecipeViewController(JFrame frame,
                                HomeView homeView,
                                Chef chef,
                                ChefRepository chefRepository,
                                Runnable onRecipesChanged) {
        this.frame = frame;
        this.homeView = homeView;
        this.chef = chef;
        this.chefRepository = chefRepository;
        Runnable refresh = this::refreshHomeList;
        if (onRecipesChanged == null) {
            this.onRecipesChanged = refresh;
        } else {
            this.onRecipesChanged = () -> {
                refresh.run();
                onRecipesChanged.run();
            };
        }
        this.homeView.setFilterAction(e -> showFilterDialog());
        refreshHomeList();
    }

    public void showHome() {
        refreshHomeList();
        frame.setContentPane(homeView);
        homeView.clearSelection();
        frame.revalidate();
        frame.repaint();
    }

    public void showRecipeDetail(String recipeName) {
        Optional<Recipe> maybeRecipe = findRecipeByName(recipeName);

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
                recipe.getPrepTime(),
                recipe.getCookTime(),
                recipe.getServings());

        detailView.setBackAction(e -> showHome());
        detailView.setEditAction(e -> showEditRecipeForm(recipe));
        detailView.setDeleteAction(e -> handleMutation(r -> r.removeRecipeByName(recipeName), null));

        setContent(detailView);
    }

    public void showAddRecipeForm() {
        RecipeFormView formView = buildRecipeForm("Add Recipe", null);
        formView.setSaveAction(e -> saveNewRecipe(formView));
        formView.setCancelAction(e -> showHome());
        setContent(formView);
    }

    public void populateSampleRecipes() {
        List<Recipe> samples = sampleRecipes();
        handleMutation(c -> samples.forEach(sample -> {
            boolean exists = c.getRecipes().stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase(sample.getName()));
            if (!exists) {
                c.addRecipe(sample);
            }
        }), this::showHome);
    }

    private void showEditRecipeForm(Recipe existing) {
        RecipeFormView formView = buildRecipeForm("Edit Recipe", existing);
        formView.setSaveAction(e -> saveEditedRecipe(existing, formView));
        formView.setCancelAction(e -> showRecipeDetail(existing.getName()));
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

    private void saveEditedRecipe(Recipe existing, RecipeFormView formView) {
        Recipe updated = buildRecipeFromForm(formView);
        if (updated == null) {
            return;
        }

        boolean duplicate = chef.getRecipes()
                .stream()
                .anyMatch(r -> r != existing && r.getName().equalsIgnoreCase(updated.getName()));
        if (duplicate) {
            JOptionPane.showMessageDialog(frame,
                    "A recipe with that name already exists. Choose a different name.",
                    "Duplicate Recipe",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        existing.setName(updated.getName());
        existing.setIngredients(updated.getIngredients());
        existing.setSteps(updated.getSteps());
        existing.setDietaryRestrictions(updated.getDietaryRestrictions());
        existing.setPrepTime(updated.getPrepTime());
        existing.setCookTime(updated.getCookTime());
        existing.setServings(updated.getServings());

        String targetName = existing.getName();
        handleMutation(c -> { }, () -> showRecipeDetail(targetName));
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

    private List<Recipe> sampleRecipes() {
        return List.of(
                createRecipe("Margherita Pizza", "Vegetarian", 20, 15, 2,
                        List.of("Pizza dough", "Tomato sauce", "Fresh mozzarella", "Fresh basil", "Olive oil", "Salt"),
                        List.of("Preheat oven to 475F.", "Stretch dough and add sauce.", "Top with mozzarella and basil.", "Bake until crust is golden.")),
                createRecipe("Chicken Stir Fry", "None", 15, 12, 3,
                        List.of("Chicken breast", "Broccoli", "Bell pepper", "Soy sauce", "Garlic", "Ginger", "Sesame oil"),
                        List.of("Slice chicken and veggies.", "Stir-fry chicken then veggies.", "Add sauce and toss to coat.")),
                createRecipe("Veggie Curry", "Vegan", 15, 25, 4,
                        List.of("Chickpeas", "Coconut milk", "Curry paste", "Spinach", "Onion", "Garlic", "Ginger"),
                        List.of("Sauté aromatics.", "Add curry paste then coconut milk.", "Simmer with chickpeas.", "Stir in spinach to wilt.")),
                createRecipe("Beef Tacos", "None", 10, 15, 4,
                        List.of("Ground beef", "Taco seasoning", "Tortillas", "Lettuce", "Tomato", "Cheddar"),
                        List.of("Brown beef with seasoning.", "Warm tortillas.", "Assemble with toppings.")),
                createRecipe("Lentil Soup", "Vegan", 10, 30, 4,
                        List.of("Lentils", "Carrots", "Celery", "Onion", "Vegetable broth", "Tomato paste"),
                        List.of("Sauté veggies.", "Add lentils, broth, tomato paste.", "Simmer until lentils are tender.")),
                createRecipe("Greek Salad", "Vegetarian", 10, 0, 2,
                        List.of("Cucumber", "Tomatoes", "Red onion", "Feta", "Olives", "Olive oil", "Oregano"),
                        List.of("Chop vegetables.", "Toss with olives and feta.", "Dress with oil and oregano.")),
                createRecipe("Pancakes", "Vegetarian", 10, 10, 4,
                        List.of("Flour", "Milk", "Egg", "Baking powder", "Sugar", "Butter"),
                        List.of("Mix dry ingredients.", "Add wet ingredients.", "Cook on griddle until golden.")),
                createRecipe("Shrimp Scampi", "Pescatarian", 10, 12, 3,
                        List.of("Shrimp", "Garlic", "Butter", "Lemon", "Parsley", "Pasta"),
                        List.of("Cook pasta.", "Sauté garlic in butter.", "Add shrimp, then lemon and parsley.", "Toss with pasta.")),
                createRecipe("Quinoa Bowl", "Gluten-free", 10, 15, 2,
                        List.of("Quinoa", "Black beans", "Corn", "Avocado", "Lime", "Cilantro"),
                        List.of("Cook quinoa.", "Warm beans and corn.", "Assemble bowl with avocado and lime.")),
                createRecipe("Stuffed Peppers", "None", 15, 35, 4,
                        List.of("Bell peppers", "Ground turkey", "Rice", "Tomato sauce", "Cheese"),
                        List.of("Par-cook peppers.", "Cook turkey with rice and sauce.", "Stuff peppers, top with cheese, bake.")),
                createRecipe("Caprese Sandwich", "Vegetarian", 8, 5, 2,
                        List.of("Ciabatta", "Tomato", "Fresh mozzarella", "Basil", "Balsamic glaze"),
                        List.of("Slice bread and ingredients.", "Layer tomato, mozzarella, basil.", "Drizzle balsamic and toast lightly.")),
                createRecipe("Salmon Teriyaki", "Pescatarian", 10, 14, 2,
                        List.of("Salmon fillets", "Teriyaki sauce", "Sesame seeds", "Green onions"),
                        List.of("Sear salmon.", "Add teriyaki sauce to glaze.", "Garnish with sesame and onions.")),
                createRecipe("Chana Masala", "Vegan", 15, 25, 4,
                        List.of("Chickpeas", "Onion", "Tomatoes", "Garam masala", "Garlic", "Ginger"),
                        List.of("Sauté aromatics.", "Add spices, tomatoes, and chickpeas.", "Simmer until thickened.")),
                createRecipe("Tofu Stir Fry", "Vegan", 15, 12, 3,
                        List.of("Firm tofu", "Mixed vegetables", "Soy sauce", "Garlic", "Ginger"),
                        List.of("Press and cube tofu.", "Sear tofu, set aside.", "Stir-fry veggies, add tofu and sauce.")),
                createRecipe("Chicken Caesar Wrap", "None", 12, 8, 2,
                        List.of("Tortillas", "Cooked chicken", "Romaine", "Caesar dressing", "Parmesan"),
                        List.of("Toss chicken with dressing.", "Layer with romaine and parmesan.", "Wrap tightly.")),
                createRecipe("Mushroom Risotto", "Vegetarian", 15, 25, 3,
                        List.of("Arborio rice", "Mushrooms", "Onion", "Vegetable broth", "Parmesan", "Butter"),
                        List.of("Sauté mushrooms and onion.", "Add rice and toast.", "Ladle broth while stirring until creamy.", "Stir in parmesan.")),
                createRecipe("BBQ Pulled Pork Sliders", "None", 20, 240, 6,
                        List.of("Pork shoulder", "BBQ sauce", "Slider buns", "Coleslaw"),
                        List.of("Slow-cook pork until tender.", "Shred and mix with BBQ sauce.", "Serve on buns with coleslaw.")),
                createRecipe("Avocado Toast", "Vegetarian", 5, 2, 1,
                        List.of("Bread", "Avocado", "Lemon", "Chili flakes", "Salt"),
                        List.of("Toast bread.", "Mash avocado with lemon and salt.", "Spread and sprinkle chili flakes.")),
                createRecipe("Falafel Wrap", "Vegan", 15, 12, 3,
                        List.of("Falafel", "Pita", "Lettuce", "Tomato", "Tahini sauce"),
                        List.of("Warm falafel and pita.", "Assemble with veggies and tahini.")),
                createRecipe("Baked Ziti", "Vegetarian", 20, 30, 6,
                        List.of("Ziti pasta", "Marinara sauce", "Ricotta", "Mozzarella", "Parmesan"),
                        List.of("Cook pasta.", "Layer with sauces and cheeses.", "Bake until bubbly."))
        );
    }

    private Recipe createRecipe(String name,
                                String dietary,
                                int prep,
                                int cook,
                                int servings,
                                List<String> ingredients,
                                List<String> steps) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDietaryRestrictions(dietary);
        recipe.setPrepTime(prep);
        recipe.setCookTime(cook);
        recipe.setServings(servings);
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        return recipe;
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
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

    private Optional<Recipe> findRecipeByName(String recipeName) {
        return chef.getRecipes()
                .stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .findFirst();
    }

    private void showFilterDialog() {
        String[] dietaryOptions = new String[]{
                "Any", "None", "Vegetarian", "Vegan", "Halal", "Kosher",
                "Gluten-free", "Dairy-free", "Nut-free", "Pescatarian", "Keto", "Other"
        };
        JComboBox<String> dietaryCombo = new JComboBox<>(dietaryOptions);
        dietaryCombo.setSelectedItem(dietaryFilter);

        JComboBox<SortOption> sortCombo = new JComboBox<>(SortOption.values());
        sortCombo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getLabel());
            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }
            return label;
        });
        sortCombo.setSelectedItem(sortOption);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Dietary restriction"));
        panel.add(dietaryCombo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JLabel("Sort by"));
        panel.add(sortCombo);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Filter & Sort",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            dietaryFilter = dietaryCombo.getSelectedItem().toString();
            sortOption = (SortOption) sortCombo.getSelectedItem();
            refreshHomeList();
        }
    }

    public void refreshHomeList() {
        List<Recipe> filtered = chef.getRecipes().stream()
                .filter(this::matchesDietary)
                .sorted(buildComparator())
                .collect(Collectors.toList());
        homeView.setRecipes(filtered);
    }

    private boolean matchesDietary(Recipe recipe) {
        if (dietaryFilter == null || dietaryFilter.equalsIgnoreCase("Any")) {
            return true;
        }
        String dietary = recipe.getDietaryRestrictions();
        String target = dietaryFilter.trim();
        if (target.equalsIgnoreCase("None")) {
            return dietary == null || dietary.isBlank() || dietary.equalsIgnoreCase("None");
        }
        return dietary != null && dietary.equalsIgnoreCase(target);
    }

    private Comparator<Recipe> buildComparator() {
        switch (sortOption) {
            case PREP_ASC:
                return Comparator.comparingInt(Recipe::getPrepTime);
            case PREP_DESC:
                return Comparator.comparingInt(Recipe::getPrepTime).reversed();
            case COOK_ASC:
                return Comparator.comparingInt(Recipe::getCookTime);
            case COOK_DESC:
                return Comparator.comparingInt(Recipe::getCookTime).reversed();
            case SERVES_ASC:
                return Comparator.comparingInt(Recipe::getServings);
            case SERVES_DESC:
                return Comparator.comparingInt(Recipe::getServings).reversed();
            case NAME_ASC:
                return Comparator.comparing(Recipe::getName, String.CASE_INSENSITIVE_ORDER);
            case NONE:
            default:
                return (a, b) -> 0;
        }
    }

    private enum SortOption {
        NONE("Default order"),
        NAME_ASC("Name A-Z"),
        PREP_ASC("Prep time (low-high)"),
        PREP_DESC("Prep time (high-low)"),
        COOK_ASC("Cook time (low-high)"),
        COOK_DESC("Cook time (high-low)"),
        SERVES_ASC("Servings (low-high)"),
        SERVES_DESC("Servings (high-low)");

        private final String label;

        SortOption(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
