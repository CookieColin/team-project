package frameworks.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RecipeDetailView extends JPanel {

    private final JLabel titleLabel = new JLabel("Recipe");
    private final JTextArea ingredientsArea = buildReadOnlyArea();
    private final JTextArea instructionsArea = buildReadOnlyArea();
    private final JLabel dietaryLabel = new JLabel("Dietary restriction: ");
    private final JLabel timeLabel = new JLabel("Time to prepare: ");
    private final JButton backButton = new JButton("Back");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public RecipeDetailView() {
        setLayout(new BorderLayout(0, 24));
        setBorder(new EmptyBorder(24, 32, 24, 32));
        setBackground(Color.WHITE);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private static JTextArea buildReadOnlyArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        area.setBorder(new EmptyBorder(8, 8, 8, 8));
        area.setBackground(new Color(0xFAFAFA));
        return area;
    }

    private JComponent buildHeader() {
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(titleLabel, BorderLayout.CENTER);
        header.add(new JSeparator(), BorderLayout.SOUTH);
        return header;
    }

    private JComponent buildContent() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(buildSection("Ingredients:", new JScrollPane(ingredientsArea)));
        container.add(Box.createVerticalStrut(16));
        container.add(buildSection("Instructions:", new JScrollPane(instructionsArea)));
        container.add(Box.createVerticalGlue());
        return container;
    }

    private JComponent buildFooter() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1, 0, 4));
        infoPanel.setOpaque(false);
        dietaryLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        infoPanel.add(dietaryLabel);
        infoPanel.add(timeLabel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setOpaque(false);
        for (JButton button : new JButton[]{backButton, editButton, deleteButton}) {
            button.setFocusable(false);
            buttons.add(button);
        }

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.add(infoPanel, BorderLayout.CENTER);
        footer.add(buttons, BorderLayout.SOUTH);
        return footer;
    }

    private JComponent buildSection(String title, JComponent content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        section.add(label, BorderLayout.NORTH);

        content.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0)));
        section.add(content, BorderLayout.CENTER);
        return section;
    }

    public void setRecipeDetails(String recipeName,
                                 List<String> ingredients,
                                 List<String> instructions,
                                 String dietaryRestrictions,
                                 String timeToPrepare) {
        titleLabel.setText(recipeName);
        ingredientsArea.setText(formatBullets(ingredients));
        instructionsArea.setText(formatNumbered(instructions));
        dietaryLabel.setText("Dietary restriction: " + dietaryRestrictions);
        timeLabel.setText("Time to prepare: " + timeToPrepare);
        ingredientsArea.setCaretPosition(0);
        instructionsArea.setCaretPosition(0);
    }

    private String formatBullets(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append("• ").append(line).append('\n');
        }
        return builder.toString();
    }

    private String formatNumbered(List<String> steps) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < steps.size(); i++) {
            builder.append(i + 1)
                    .append(". ")
                    .append(steps.get(i))
                    .append('\n');
        }
        return builder.toString();
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
