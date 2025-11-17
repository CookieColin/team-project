package frameworks.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeView extends JPanel {

    private final DefaultListModel<String> recipeListModel = new DefaultListModel<>();
    private final JList<String> recipeList = new JList<>(recipeListModel);
    private final JButton addRecipeButton = new JButton("Add Recipe");
    private final JButton populateButton = new JButton("Populate");
    private final JButton filterButton = new JButton("Filter");

    public HomeView() {
        setLayout(new BorderLayout(16, 16));
        setBorder(new EmptyBorder(24, 24, 24, 24));
        setBackground(Color.WHITE);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildRecipeSection(), BorderLayout.CENTER);
        add(buildActionSection(), BorderLayout.SOUTH);
    }

    private JComponent buildHeader() {
        JLabel title = new JLabel("Your Recipes");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel subtitle = new JLabel("Select a recipe to view, or use the buttons below to manage your list.");
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 14f));
        subtitle.setForeground(Color.DARK_GRAY);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);
        return header;
    }

    private JComponent buildRecipeSection() {
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipeList.setVisibleRowCount(8);
        recipeList.setFont(recipeList.getFont().deriveFont(14f));
        recipeList.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(recipeList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recipe List"));
        return scrollPane;
    }

    private JComponent buildActionSection() {
        JPanel buttonRow = new JPanel(new GridLayout(1, 3, 12, 0));
        buttonRow.setOpaque(false);

        addRecipeButton.setFocusable(false);
        populateButton.setFocusable(false);
        filterButton.setFocusable(false);

        buttonRow.add(addRecipeButton);
        buttonRow.add(populateButton);
        buttonRow.add(filterButton);

        return buttonRow;
    }

    public void setRecipes(List<String> recipes) {
        recipeListModel.clear();
        recipes.forEach(recipeListModel::addElement);
    }

    public void setAddRecipeAction(ActionListener listener) {
        addRecipeButton.addActionListener(listener);
    }

    public void setPopulateAction(ActionListener listener) {
        populateButton.addActionListener(listener);
    }

    public void setFilterAction(ActionListener listener) {
        filterButton.addActionListener(listener);
    }

    public void setRecipeSelectionListener(ListSelectionListener listener) {
        recipeList.addListSelectionListener(listener);
    }

    public String getSelectedRecipe() {
        return recipeList.getSelectedValue();
    }

    public void clearSelection() {
        recipeList.clearSelection();
    }
}
