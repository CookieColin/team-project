package frameworks.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class RecipeDetailView extends JPanel {

    private static final int CARD_MAX_WIDTH = 960;
    private static final int CONTENT_MAX_WIDTH = 920;
    private static final Color PAGE_BG = new Color(0xF6F7FB);
    private static final Color CARD_BORDER = new Color(0xE3E6EA);
    private static final Color MUTED_TEXT = new Color(0x5F6368);

    private final JLabel titleLabel = new JLabel("Recipe");
    private final JPanel ingredientListPanel = buildListPanel();
    private final JPanel instructionListPanel = buildListPanel();
    private final JLabel dietaryInfo = new JLabel();
    private final JLabel prepValueLabel = new JLabel();
    private final JLabel cookValueLabel = new JLabel();
    private final JLabel servesValueLabel = new JLabel();
    private final JButton backButton = new JButton("Back");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public RecipeDetailView() {
        setLayout(new BorderLayout());
        setBackground(PAGE_BG);
        add(buildScrollContent(), BorderLayout.CENTER);
    }

    private JComponent buildScrollContent() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(18, 22, 22, 22)
        ));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(CARD_MAX_WIDTH, Integer.MAX_VALUE));

        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subhead = new JLabel("Review the details, then edit or delete as needed.");
        subhead.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        subhead.setForeground(MUTED_TEXT);
        subhead.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(subhead);
        card.add(Box.createVerticalStrut(14));
        card.add(buildSection("Ingredients", ingredientListPanel));
        card.add(Box.createVerticalStrut(12));
        card.add(buildSection("Instructions", instructionListPanel));
        card.add(Box.createVerticalStrut(14));
        card.add(buildDietarySection());
        card.add(Box.createVerticalStrut(10));
        card.add(buildTimeSection());
        card.add(Box.createVerticalStrut(16));
        card.add(buildButtonsRow());

        // Force the card to expand to the desired width while keeping computed height.
        Dimension cardSize = card.getPreferredSize();
        card.setPreferredSize(new Dimension(CARD_MAX_WIDTH, cardSize.height));
        card.setMaximumSize(new Dimension(CARD_MAX_WIDTH, Integer.MAX_VALUE));
        card.setMinimumSize(new Dimension(CARD_MAX_WIDTH, 0));

        JPanel centerRow = new JPanel();
        centerRow.setLayout(new BoxLayout(centerRow, BoxLayout.X_AXIS));
        centerRow.setOpaque(false);
        centerRow.add(Box.createHorizontalGlue());
        centerRow.add(card);
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

    private static JPanel buildListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(new EmptyBorder(6, 12, 6, 12));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(CONTENT_MAX_WIDTH, Integer.MAX_VALUE));
        panel.setOpaque(false);
        return panel;
    }

    private JComponent buildSection(String title, JPanel content) {
        JLabel header = new JLabel(title);
        header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.CENTER_ALIGNMENT);
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(CONTENT_MAX_WIDTH, Integer.MAX_VALUE));
        section.add(header);
        section.add(content);
        return section;
    }

    private JPanel buildDietarySection() {
        JPanel dietaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        dietaryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dietaryPanel.setOpaque(false);
        dietaryPanel.setBorder(new EmptyBorder(5, 12, 5, 12));
        dietaryPanel.setMaximumSize(new Dimension(CONTENT_MAX_WIDTH, 36));

        JLabel dietaryLabel = new JLabel("Dietary restriction:");
        dietaryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        dietaryInfo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        dietaryInfo.setForeground(MUTED_TEXT);

        dietaryPanel.add(dietaryLabel);
        dietaryPanel.add(dietaryInfo);
        return dietaryPanel;
    }

    private JPanel buildTimeSection() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setMaximumSize(new Dimension(CONTENT_MAX_WIDTH, 48));
        row.setBorder(new EmptyBorder(8, 12, 8, 12));
        row.setOpaque(false);

        row.add(buildTimeCell("Prep", prepValueLabel));
        row.add(buildTimeCell("Cook", cookValueLabel));
        row.add(buildTimeCell("Serves", servesValueLabel));
        return row;
    }

    private JPanel buildTimeCell(String label, JLabel valueLabel) {
        JLabel header = new JLabel(label);
        header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        valueLabel.setForeground(MUTED_TEXT);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel cell = new JPanel();
        cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
        cell.setOpaque(false);
        cell.setAlignmentX(Component.CENTER_ALIGNMENT);
        cell.add(header);
        cell.add(Box.createVerticalStrut(4));
        cell.add(valueLabel);
        return cell;
    }

    private JPanel buildButtonsRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(12, 10, 4, 10));

        JButton[] buttons = new JButton[]{backButton, editButton, deleteButton};
        for (JButton button : buttons) {
            button.setFocusable(false);
            button.setPreferredSize(new Dimension(96, 32));
            row.add(button);
        }
        return row;
    }

    public void setRecipeDetails(String recipeName,
                                 List<String> ingredients,
                                 List<String> instructions,
                                 String dietaryRestrictions,
                                 int prepMinutes,
                                 int cookMinutes,
                                 int servings) {
        titleLabel.setText(recipeName);
        populateList(ingredientListPanel, ingredients, false);
        populateList(instructionListPanel, instructions, true);
        dietaryInfo.setText(dietaryRestrictions);
        prepValueLabel.setText(prepMinutes + " min");
        cookValueLabel.setText(cookMinutes + " min");
        servesValueLabel.setText(String.valueOf(servings));
        revalidate();
        repaint();
    }

    private void populateList(JPanel target, List<String> items, boolean numbered) {
        target.removeAll();
        for (int i = 0; i < items.size(); i++) {
            String prefix = numbered ? (i + 1) + ". " : "• ";
            JLabel label = new JLabel(prefix + items.get(i));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
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
