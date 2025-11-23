package frameworks.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Editable version of the recipe detail layout so users can add or update recipes.
 */
public class RecipeFormView extends JPanel {

    private static final int FORM_MAX_WIDTH = 780;
    private static final int AREA_MIN_HEIGHT = 180;

    private final JLabel headerLabel = new JLabel("Add Recipe");
    private final JTextField titleField = new JTextField();
    private final JTextArea ingredientsArea = buildArea();
    private final JTextArea instructionsArea = buildArea();
    private final JComboBox<String> dietaryDropdown = buildDietaryDropdown();
    private final JTextField prepField = new JTextField();
    private final JTextField cookField = new JTextField();
    private final JTextField servingsField = new JTextField();
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    public RecipeFormView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setBackground(new Color(0xF6F7FB));
        add(buildContent(), BorderLayout.CENTER);
    }

    private static JTextArea buildArea() {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
        area.setBackground(new Color(0xFBFBFD));
        return area;
    }

    private JComponent buildContent() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(true);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(0xE3E6EA), 1, true),
                new EmptyBorder(18, 22, 22, 22)
        ));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(FORM_MAX_WIDTH, Integer.MAX_VALUE));

        headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subheader = new JLabel("Capture ingredients, steps, and timing in one place.");
        subheader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        subheader.setForeground(new Color(0x5F6368));
        subheader.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(headerLabel);
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(subheader);
        formPanel.add(Box.createVerticalStrut(14));
        formPanel.add(buildField("Recipe title", titleField));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(buildWideSection("Ingredients (one per line)", new JScrollPane(ingredientsArea)));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(buildWideSection("Instructions (one step per line)", new JScrollPane(instructionsArea)));
        formPanel.add(Box.createVerticalStrut(14));
        formPanel.add(buildField("Dietary restrictions", dietaryDropdown));
        formPanel.add(Box.createVerticalStrut(14));
        formPanel.add(buildTimeRow());
        formPanel.add(Box.createVerticalStrut(18));
        formPanel.add(buildButtons());

        JPanel centerRow = new JPanel();
        centerRow.setLayout(new BoxLayout(centerRow, BoxLayout.X_AXIS));
        centerRow.setOpaque(false);
        centerRow.add(Box.createHorizontalGlue());
        centerRow.add(formPanel);
        centerRow.add(Box.createHorizontalGlue());

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(Box.createVerticalStrut(8));
        container.add(centerRow);
        container.add(Box.createVerticalStrut(8));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        return scrollPane;
    }

    private JComponent buildField(String label, JComponent input) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setMaximumSize(new Dimension(FORM_MAX_WIDTH, 32));
        input.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.setMaximumSize(new Dimension(FORM_MAX_WIDTH, 72));
        container.add(fieldLabel);
        container.add(Box.createVerticalStrut(4));
        container.add(input);
        return container;
    }

    private JComponent buildWideSection(String label, JComponent component) {
        JLabel sectionLabel = new JLabel(label);
        sectionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setBorder(new CompoundBorder(
                new LineBorder(new Color(0xDADDE3), 1, true),
                new EmptyBorder(2, 2, 2, 2)
        ));
        component.setPreferredSize(new Dimension(FORM_MAX_WIDTH, AREA_MIN_HEIGHT));
        component.setMinimumSize(new Dimension(FORM_MAX_WIDTH, AREA_MIN_HEIGHT));
        component.setMaximumSize(new Dimension(FORM_MAX_WIDTH, 280));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.setMaximumSize(new Dimension(FORM_MAX_WIDTH, 320));
        container.add(sectionLabel);
        container.add(Box.createVerticalStrut(4));
        container.add(component);
        return container;
    }

    private JPanel buildTimeRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setMaximumSize(new Dimension(FORM_MAX_WIDTH, 64));

        row.add(buildField("Prep (minutes)", prepField));
        row.add(buildField("Cook (minutes)", cookField));
        row.add(buildField("Servings", servingsField));

        return row;
    }

    private JPanel buildButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.setOpaque(false);

        saveButton.setFocusable(false);
        cancelButton.setFocusable(false);
        saveButton.setPreferredSize(new Dimension(88, 32));
        cancelButton.setPreferredSize(new Dimension(88, 32));

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
        Object value = dietaryDropdown.getSelectedItem();
        return value == null ? "" : value.toString();
    }

    public void setDietaryRestrictions(String text) {
        selectDietaryOption(text);
    }

    private static JComboBox<String> buildDietaryDropdown() {
        List<String> options = Arrays.asList(
                "None",
                "Vegetarian",
                "Vegan",
                "Halal",
                "Kosher",
                "Gluten-free",
                "Dairy-free",
                "Nut-free",
                "Pescatarian",
                "Keto",
                "Other"
        );
        JComboBox<String> box = new JComboBox<>(options.toArray(new String[0]));
        box.setEditable(false);
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        return box;
    }

    private void selectDietaryOption(String value) {
        if (value == null || value.isBlank()) {
            dietaryDropdown.setSelectedIndex(0);
            return;
        }
        ComboBoxModel<String> model = dietaryDropdown.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            String option = model.getElementAt(i);
            if (option.equalsIgnoreCase(value)) {
                dietaryDropdown.setSelectedIndex(i);
                return;
            }
        }
        dietaryDropdown.setSelectedItem(value);
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
