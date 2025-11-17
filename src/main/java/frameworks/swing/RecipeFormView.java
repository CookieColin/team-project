package frameworks.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Editable version of the recipe detail layout so users can add or update recipes.
 */
public class RecipeFormView extends JPanel {

    private final JLabel headerLabel = new JLabel("Add Recipe");
    private final JTextField titleField = new JTextField();
    private final JTextArea ingredientsArea = buildArea();
    private final JTextArea instructionsArea = buildArea();
    private final JTextField dietaryField = new JTextField();
    private final JTextField prepField = new JTextField();
    private final JTextField cookField = new JTextField();
    private final JTextField servingsField = new JTextField();
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    public RecipeFormView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 24, 20, 24));
        setBackground(Color.WHITE);
        add(buildContent(), BorderLayout.CENTER);
    }

    private static JTextArea buildArea() {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
        return area;
    }

    private JComponent buildContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(16));
        panel.add(buildField("Recipe title", titleField));
        panel.add(Box.createVerticalStrut(12));
        panel.add(buildSection("Ingredients (one per line)", new JScrollPane(ingredientsArea)));
        panel.add(Box.createVerticalStrut(12));
        panel.add(buildSection("Instructions (one step per line)", new JScrollPane(instructionsArea)));
        panel.add(Box.createVerticalStrut(12));
        panel.add(buildField("Dietary restrictions", dietaryField));
        panel.add(Box.createVerticalStrut(12));
        panel.add(buildTimeRow());
        panel.add(Box.createVerticalStrut(12));
        panel.add(buildButtons());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JComponent buildField(String label, JComponent input) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(fieldLabel);
        container.add(Box.createVerticalStrut(4));
        container.add(input);
        return container;
    }

    private JComponent buildSection(String label, JComponent component) {
        JLabel sectionLabel = new JLabel(label);
        sectionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setBorder(BorderFactory.createLineBorder(new Color(0xDDDDDD)));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(sectionLabel);
        container.add(Box.createVerticalStrut(4));
        container.add(component);
        return container;
    }

    private JPanel buildTimeRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setOpaque(false);

        row.add(buildField("Prep (minutes)", prepField));
        row.add(buildField("Cook (minutes)", cookField));
        row.add(buildField("Servings", servingsField));

        return row;
    }

    private JPanel buildButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setOpaque(false);

        saveButton.setFocusable(false);
        cancelButton.setFocusable(false);

        buttons.add(cancelButton);
        buttons.add(saveButton);
        return buttons;
    }

    public void setFormTitle(String title) {
        headerLabel.setText(title);
    }

    public void setSaveAction(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public String getRecipeName() {
        return titleField.getText();
    }

    public void setRecipeName(String text) {
        titleField.setText(text);
    }

    public String getIngredients() {
        return ingredientsArea.getText();
    }

    public void setIngredients(String text) {
        ingredientsArea.setText(text);
    }

    public String getInstructions() {
        return instructionsArea.getText();
    }

    public void setInstructions(String text) {
        instructionsArea.setText(text);
    }

    public String getDietaryRestrictions() {
        return dietaryField.getText();
    }

    public void setDietaryRestrictions(String text) {
        dietaryField.setText(text);
    }

    public String getPrepTime() {
        return prepField.getText();
    }

    public void setPrepTime(String text) {
        prepField.setText(text);
    }

    public String getCookTime() {
        return cookField.getText();
    }

    public void setCookTime(String text) {
        cookField.setText(text);
    }

    public String getServings() {
        return servingsField.getText();
    }

    public void setServings(String text) {
        servingsField.setText(text);
    }
}
