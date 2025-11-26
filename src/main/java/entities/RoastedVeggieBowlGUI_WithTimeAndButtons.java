import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoastedVeggieBowlGUI_WithTimeAndButtons {

    // ===== DocumentFilter for numeric-only input =====
    static class NumericFilter extends DocumentFilter {
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
        }
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                throws BadLocationException {
            if (text.matches("\\d*")) super.insertString(fb, offset, text, attr);
        }
    }

    // ===== View mode rows =====
    static List<JPanel> ingredientRows = new ArrayList<>();
    static List<JLabel> ingredientLabels = new ArrayList<>();

    static List<JPanel> instructionRows = new ArrayList<>();
    static List<JLabel> instructionLabels = new ArrayList<>();

    // ===== Edit mode rows =====
    static List<JPanel> ingredientEditRows = new ArrayList<>();
    static List<JTextField> ingredientFields = new ArrayList<>();

    static List<JPanel> instructionEditRows = new ArrayList<>();
    static List<JTextField> instructionFields = new ArrayList<>();

    // ===== Dietary =====
    static JLabel dietaryInfoLabel;
    static JTextArea dietaryInfoArea;

    // ===== Time =====
    static JTextField minutesNumberField;

    // ===== Buttons =====
    static JButton addIngredientBtn = new JButton("Add Ingredient");
    static JButton addStepBtn = new JButton("Add Step");

    // ===== Backup state (for Back button) =====
    static boolean editMode = false;
    static List<JPanel> ingredientRowsBackup = new ArrayList<>();
    static List<JPanel> instructionRowsBackup = new ArrayList<>();
    static String dietaryBackupText;
    static String minutesBackup;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Roasted Veggie Bowl");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            // ===========================================================
            // INGREDIENTS
            // ===========================================================

            JLabel header = new JLabel("Ingredients:");
            header.setFont(new Font("SansSerif", Font.BOLD, 15));
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(header);

            JPanel ingredientListPanel = new JPanel();
            ingredientListPanel.setLayout(new BoxLayout(ingredientListPanel, BoxLayout.Y_AXIS));
            ingredientListPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 20, 5));
            ingredientListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            String[] ingredients = {
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
            };

            for (String ing : ingredients) {
                JLabel lbl = new JLabel("• " + ing);

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);

                row.add(lbl);

                ingredientRows.add(row);
                ingredientLabels.add(lbl);
                ingredientListPanel.add(row);
            }

            // Add Ingredient button (hidden until Edit mode)
            addIngredientBtn.setVisible(false);
            addIngredientBtn.addActionListener(e -> {

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel bullet = new JLabel("• ");

                JTextField field = new JTextField("");
                field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

                row.add(bullet);
                row.add(field);

                ingredientEditRows.add(row);
                ingredientFields.add(field);

                ingredientListPanel.add(row, ingredientListPanel.getComponentCount() - 1);
                ingredientListPanel.revalidate();
                ingredientListPanel.repaint();
            });

            ingredientListPanel.add(addIngredientBtn);
            mainPanel.add(ingredientListPanel);

            // ===========================================================
            // INSTRUCTIONS
            // ===========================================================

            JLabel instructionsHeader = new JLabel("Instructions:");
            instructionsHeader.setFont(new Font("SansSerif", Font.BOLD, 15));
            instructionsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(instructionsHeader);

            JPanel instructionListPanel = new JPanel();
            instructionListPanel.setLayout(new BoxLayout(instructionListPanel, BoxLayout.Y_AXIS));
            instructionListPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 25, 5));
            instructionListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            String[] steps = {
                    "Preheat oven to 400°F (200°C).",
                    "Toss chopped vegetables with olive oil, salt, pepper, and paprika.",
                    "Spread evenly on a baking sheet lined with parchment paper.",
                    "Roast for 25–30 minutes, stirring halfway through.",
                    "Serve over quinoa or rice and top with tahini or your favorite dressing."
            };

            for (int i = 0; i < steps.length; i++) {
                JLabel lbl = new JLabel((i + 1) + ". " + steps[i]);

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);

                row.add(lbl);

                instructionRows.add(row);
                instructionLabels.add(lbl);
                instructionListPanel.add(row);
            }

            // Add Step button (hidden until Edit)
            addStepBtn.setVisible(false);
            addStepBtn.addActionListener(e -> {

                int nextNumber = 1;

                if (!instructionEditRows.isEmpty()) {
                    JLabel last = (JLabel) instructionEditRows.get(instructionEditRows.size()-1).getComponent(0);
                    nextNumber = Integer.parseInt(last.getText().replace(". ", "")) + 1;

                } else if (!instructionRows.isEmpty()) {
                    JLabel last = (JLabel) instructionRows.get(instructionRows.size()-1).getComponent(0);
                    nextNumber = Integer.parseInt(last.getText().substring(0, last.getText().indexOf("."))) + 1;
                }

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel numLabel = new JLabel(nextNumber + ". ");

                JTextField field = new JTextField("");
                field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

                row.add(numLabel);
                row.add(field);

                instructionEditRows.add(row);
                instructionFields.add(field);

                instructionListPanel.add(row, instructionListPanel.getComponentCount() - 1);
                instructionListPanel.revalidate();
                instructionListPanel.repaint();
            });

            instructionListPanel.add(addStepBtn);
            mainPanel.add(instructionListPanel);

            // ===========================================================
            // DIETARY
            // ===========================================================

            JPanel dietaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            dietaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel dietaryLabel = new JLabel("Dietary restriction:");
            dietaryLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

            dietaryInfoLabel = new JLabel(
                    "Vegan, Vegetarian, Gluten-free (if using quinoa or certified GF rice), Dairy-free, Contains no nuts (unless added in toppings)"
            );

            dietaryPanel.add(dietaryLabel);
            dietaryPanel.add(dietaryInfoLabel);
            mainPanel.add(dietaryPanel);

            // ===========================================================
            // TIME
            // ===========================================================

            JPanel timeRow = new JPanel();
            timeRow.setLayout(new BoxLayout(timeRow, BoxLayout.X_AXIS));
            timeRow.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel timeLabel = new JLabel("Time to prepare:");
            timeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            timeRow.add(timeLabel);
            timeRow.add(Box.createHorizontalGlue());

            minutesNumberField = new JTextField("45");
            minutesNumberField.setColumns(3);
            minutesNumberField.setMaximumSize(new Dimension(50, minutesNumberField.getPreferredSize().height));
            minutesNumberField.setHorizontalAlignment(JTextField.RIGHT);
            minutesNumberField.setEditable(false);

            ((AbstractDocument) minutesNumberField.getDocument()).setDocumentFilter(new NumericFilter());

            JPanel numberPanel = new JPanel();
            numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.X_AXIS));
            numberPanel.add(minutesNumberField);

            JPanel minutesLabelPanel = new JPanel();
            minutesLabelPanel.setLayout(new BoxLayout(minutesLabelPanel, BoxLayout.X_AXIS));
            minutesLabelPanel.add(new JLabel("minutes"));

            timeRow.add(numberPanel);
            timeRow.add(minutesLabelPanel);
            mainPanel.add(timeRow);

            // ===========================================================
            // BUTTONS
            // ===========================================================

            JPanel buttonsContainer = new JPanel(new BorderLayout());
            buttonsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton backBtn = new JButton("Back");
            JPanel backWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            backWrap.add(backBtn);
            buttonsContainer.add(backWrap, BorderLayout.WEST);

            JButton editBtn = new JButton("Edit");
            JPanel editWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            editWrap.add(editBtn);
            buttonsContainer.add(editWrap, BorderLayout.CENTER);

            JButton deleteBtn = new JButton("Delete");
            JPanel deleteWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            deleteWrap.add(deleteBtn);
            buttonsContainer.add(deleteWrap, BorderLayout.EAST);

            mainPanel.add(buttonsContainer);

            // ===========================================================
            // BACK BUTTON
            // ===========================================================

            backBtn.addActionListener(e -> {
                if (editMode) {
                    cancelEditMode(ingredientListPanel, instructionListPanel, dietaryPanel);
                    editBtn.setText("Edit");
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            });

            // ===========================================================
            // EDIT / SAVE BUTTON
            // ===========================================================

            editBtn.addActionListener(e -> {

                if (!editMode) {
                    enterEditMode(ingredientListPanel, instructionListPanel, dietaryPanel);

                    addIngredientBtn.setVisible(true);
                    addStepBtn.setVisible(true);
                    minutesNumberField.setEditable(true);

                    editBtn.setText("Save");
                    editMode = true;

                } else {

                    saveEdits(ingredientListPanel, instructionListPanel, dietaryPanel);

                    addIngredientBtn.setVisible(false);
                    addStepBtn.setVisible(false);
                    minutesNumberField.setEditable(false);

                    editBtn.setText("Edit");
                    editMode = false;
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            });

            // ===========================================================
            // FINAL SETUP
            // ===========================================================

            frame.add(new JScrollPane(mainPanel));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // ===========================================================
    // ENTER EDIT MODE (Backup included)
    // ===========================================================

    private static void enterEditMode(
            JPanel ingredientPanel,
            JPanel instructionPanel,
            JPanel dietaryPanel
    ) {
        ingredientEditRows.clear();
        ingredientFields.clear();
        instructionEditRows.clear();
        instructionFields.clear();

        ingredientRowsBackup.clear();
        ingredientRowsBackup.addAll(ingredientRows);

        instructionRowsBackup.clear();
        instructionRowsBackup.addAll(instructionRows);

        dietaryBackupText = dietaryInfoLabel.getText();
        minutesBackup = minutesNumberField.getText();

        // -----------------------------------------------------------
        // INGREDIENTS → EDIT
        // -----------------------------------------------------------
        for (int i = 0; i < ingredientRows.size(); i++) {

            JPanel oldRow = ingredientRows.get(i);
            JLabel oldLabel = ingredientLabels.get(i);

            String text = oldLabel.getText().substring(2);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel bullet = new JLabel("• ");
            JTextField field = new JTextField(text);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

            row.add(bullet);
            row.add(field);

            ingredientEditRows.add(row);
            ingredientFields.add(field);

            swapComponent(ingredientPanel, oldRow, row);
        }

        // -----------------------------------------------------------
        // INSTRUCTIONS → EDIT
        // -----------------------------------------------------------
        for (int i = 0; i < instructionRows.size(); i++) {

            JPanel oldRow = instructionRows.get(i);
            JLabel oldLabel = instructionLabels.get(i);

            int num = i + 1;
            String text = oldLabel.getText().substring(oldLabel.getText().indexOf(". ") + 2);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel numLabel = new JLabel(num + ". ");
            JTextField field = new JTextField(text);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

            row.add(numLabel);
            row.add(field);

            instructionEditRows.add(row);
            instructionFields.add(field);

            swapComponent(instructionPanel, oldRow, row);
        }

        // -----------------------------------------------------------
        // DIETARY → EDIT (FULL WIDTH)
        // -----------------------------------------------------------
        dietaryInfoArea = new JTextArea(dietaryInfoLabel.getText());
        dietaryInfoArea.setLineWrap(true);
        dietaryInfoArea.setWrapStyleWord(true);
        dietaryInfoArea.setRows(3);

        // Slightly wider box (not huge)
        dietaryInfoArea.setPreferredSize(new Dimension(500, 100));
        dietaryInfoArea.setMinimumSize(new Dimension(450, 80));
        dietaryInfoArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        swapComponent(dietaryPanel, dietaryInfoLabel, dietaryInfoArea);
    }

    // ===========================================================
    // SAVE EDITS
    // ===========================================================

    private static void saveEdits(
            JPanel ingredientPanel,
            JPanel instructionPanel,
            JPanel dietaryPanel
    ) {
        ingredientRows.clear();
        ingredientLabels.clear();

        instructionRows.clear();
        instructionLabels.clear();

        // -----------------------------------------------------------
        // SAVE INGREDIENTS
        // -----------------------------------------------------------
        for (int i = 0; i < ingredientEditRows.size(); i++) {

            JTextField field = ingredientFields.get(i);
            String text = field.getText().trim();

            if (text.isEmpty()) {
                ingredientPanel.remove(ingredientEditRows.get(i));
                continue;
            }

            JLabel lbl = new JLabel("• " + text);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.add(lbl);

            ingredientRows.add(row);
            ingredientLabels.add(lbl);

            swapComponent(ingredientPanel, ingredientEditRows.get(i), row);
        }

        // -----------------------------------------------------------
        // SAVE INSTRUCTIONS
        // -----------------------------------------------------------
        int stepNum = 1;

        for (int i = 0; i < instructionEditRows.size(); i++) {

            JTextField field = instructionFields.get(i);
            String text = field.getText().trim();

            if (text.isEmpty()) {
                instructionPanel.remove(instructionEditRows.get(i));
                continue;
            }

            JLabel lbl = new JLabel(stepNum + ". " + text);

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.add(lbl);

            instructionRows.add(row);
            instructionLabels.add(lbl);

            swapComponent(instructionPanel, instructionEditRows.get(i), row);

            stepNum++;
        }

        dietaryInfoLabel.setText(dietaryInfoArea.getText());
        swapComponent(dietaryPanel, dietaryInfoArea, dietaryInfoLabel);
    }

    // ===========================================================
    // CANCEL EDIT MODE — BACK BUTTON
    // ===========================================================

    private static void cancelEditMode(
            JPanel ingredientPanel,
            JPanel instructionPanel,
            JPanel dietaryPanel
    ) {

        ingredientPanel.removeAll();
        for (JPanel row : ingredientRowsBackup) {
            ingredientPanel.add(row);
        }
        ingredientPanel.add(addIngredientBtn);
        addIngredientBtn.setVisible(false);

        instructionPanel.removeAll();
        for (JPanel row : instructionRowsBackup) {
            instructionPanel.add(row);
        }
        instructionPanel.add(addStepBtn);
        addStepBtn.setVisible(false);

        dietaryInfoLabel.setText(dietaryBackupText);
        if (dietaryInfoArea != null)
            swapComponent(dietaryPanel, dietaryInfoArea, dietaryInfoLabel);

        minutesNumberField.setEditable(false);
        minutesNumberField.setText(minutesBackup);

        ingredientEditRows.clear();
        ingredientFields.clear();
        instructionEditRows.clear();
        instructionFields.clear();

        editMode = false;
    }

    // ===========================================================
    // HELPER: SWAP COMPONENTS IN PANEL
    // ===========================================================

    private static void swapComponent(JPanel panel, Component oldComp, Component newComp) {
        for (int i = 0; i < panel.getComponentCount(); i++) {
            if (panel.getComponent(i) == oldComp) {
                panel.remove(i);
                panel.add(newComp, i);
                return;
            }
        }
    }
}
