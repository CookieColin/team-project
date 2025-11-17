package frameworks.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RecipeDetailView extends JPanel {

    private final JLabel titleLabel = new JLabel("Recipe");
    private final JPanel ingredientListPanel = buildListPanel();
    private final JPanel instructionListPanel = buildListPanel();
    private final JLabel dietaryInfo = new JLabel();
    private final JLabel timeValueLabel = new JLabel();
    private final JButton backButton = new JButton("Back");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public RecipeDetailView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(buildScrollContent(), BorderLayout.CENTER);
    }

    private JComponent buildScrollContent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 24, 20, 24));
        mainPanel.setOpaque(false);

        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(8));

        mainPanel.add(buildSection("Ingredients:", ingredientListPanel));
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(buildSection("Instructions:", instructionListPanel));
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(buildDietarySection());
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(buildTimeSection());
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(buildButtonsRow());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        return scrollPane;
    }

    private static JPanel buildListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(new EmptyBorder(5, 30, 5, 10));
        panel.setOpaque(false);
        return panel;
    }

    private JComponent buildSection(String title, JPanel content) {
        JLabel header = new JLabel(title);
        header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setOpaque(false);
        section.add(header);
        section.add(content);
        return section;
    }

    private JPanel buildDietarySection() {
        JPanel dietaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        dietaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dietaryPanel.setOpaque(false);
        dietaryPanel.setBorder(new EmptyBorder(5, 20, 5, 10));

        JLabel dietaryLabel = new JLabel("Dietary restriction:");
        dietaryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        dietaryInfo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        dietaryPanel.add(dietaryLabel);
        dietaryPanel.add(dietaryInfo);
        return dietaryPanel;
    }

    private JPanel buildTimeSection() {
        JPanel timeRow = new JPanel();
        timeRow.setLayout(new BoxLayout(timeRow, BoxLayout.X_AXIS));
        timeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeRow.setBorder(new EmptyBorder(5, 20, 5, 10));
        timeRow.setOpaque(false);

        JLabel timeLabel = new JLabel("Time to prepare:");
        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        timeValueLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        timeValueLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        timeRow.add(timeLabel);
        timeRow.add(Box.createHorizontalStrut(8));
        timeRow.add(timeValueLabel);
        return timeRow;
    }

    private JPanel buildButtonsRow() {
        JPanel buttonsContainer = new JPanel(new BorderLayout());
        buttonsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonsContainer.setOpaque(false);
        buttonsContainer.setBorder(new EmptyBorder(8, 10, 10, 10));

        JButton[] buttons = new JButton[]{backButton, editButton, deleteButton};
        for (JButton button : buttons) {
            button.setFocusable(false);
        }

        buttonsContainer.add(wrapButton(backButton, FlowLayout.LEFT), BorderLayout.WEST);
        buttonsContainer.add(wrapButton(editButton, FlowLayout.CENTER), BorderLayout.CENTER);
        buttonsContainer.add(wrapButton(deleteButton, FlowLayout.RIGHT), BorderLayout.EAST);
        return buttonsContainer;
    }

    private JPanel wrapButton(JButton button, int alignment) {
        JPanel wrapper = new JPanel(new FlowLayout(alignment, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(button);
        return wrapper;
    }

    public void setRecipeDetails(String recipeName,
                                 List<String> ingredients,
                                 List<String> instructions,
                                 String dietaryRestrictions,
                                 String timeToPrepare) {
        titleLabel.setText(recipeName);
        populateList(ingredientListPanel, ingredients, false);
        populateList(instructionListPanel, instructions, true);
        dietaryInfo.setText(dietaryRestrictions);
        timeValueLabel.setText(timeToPrepare);
        revalidate();
        repaint();
    }

    private void populateList(JPanel target, List<String> items, boolean numbered) {
        target.removeAll();
        for (int i = 0; i < items.size(); i++) {
            String prefix = numbered ? (i + 1) + ". " : "• ";
            JLabel label = new JLabel(prefix + items.get(i));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setBorder(new EmptyBorder(2, 0, 2, 0));
            target.add(label);
        }
    }

    public void setBackAction(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
