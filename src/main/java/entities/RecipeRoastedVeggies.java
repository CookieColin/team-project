package entities;

import javax.swing.*;
import java.awt.*;

public class RecipeRoastedVeggies {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Roasted Veggie Bowl");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // ===== MAIN PANEL (vertical layout) =====
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            // ===== INGREDIENTS HEADER / LIST =====
            JLabel header = new JLabel("Ingredients:");
            header.setFont(new Font("SansSerif", Font.BOLD, 15));
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(header);

            JPanel ingredientListPanel = new JPanel();
            ingredientListPanel.setLayout(new BoxLayout(ingredientListPanel, BoxLayout.Y_AXIS));
            ingredientListPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 20, 5));
            ingredientListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            String[] ingredients = {
                    "• one medium sweet potato, peeled and cubed",
                    "• one chopped red bell pepper",
                    "• one zucchini, sliced into half-moons",
                    "• one small red onion cut into wedges",
                    "• one cup broccoli florets",
                    "• two tbsp olive oil",
                    "• one tsp salt",
                    "• one tsp paprika (optional: smoked)",
                    "• half a tsp of black pepper",
                    "• one cup cooked quinoa or brown rice (base)"
            };

            for (String item : ingredients) {
                JLabel label = new JLabel(item);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
                ingredientListPanel.add(label);
            }

            mainPanel.add(ingredientListPanel);

            // ===== INSTRUCTIONS =====
            JLabel instructionsHeader = new JLabel("Instructions:");
            instructionsHeader.setFont(new Font("SansSerif", Font.BOLD, 15));
            instructionsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(instructionsHeader);

            JPanel instructionListPanel = new JPanel();
            instructionListPanel.setLayout(new BoxLayout(instructionListPanel, BoxLayout.Y_AXIS));
            instructionListPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 25, 5));
            instructionListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            String[] instructions = {
                    "1. Preheat oven to 400°F (200°C).",
                    "2. Toss chopped vegetables with olive oil, salt, pepper, and paprika.",
                    "3. Spread evenly on a baking sheet lined with parchment paper.",
                    "4. Roast for 25–30 minutes, stirring halfway through.",
                    "5. Serve over quinoa or rice and top with tahini or your favorite dressing."
            };

            for (String step : instructions) {
                JLabel label = new JLabel(step);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
                instructionListPanel.add(label);
            }

            mainPanel.add(instructionListPanel);

            // ===== DIETARY RESTRICTION SECTION =====
            JPanel dietaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            dietaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 10));
            dietaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel dietaryLabel = new JLabel("Dietary restriction:");
            dietaryLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

            JLabel dietaryInfo = new JLabel(
                    "Vegan, Vegetarian, Gluten-free (if using quinoa or certified GF rice), Dairy-free, Contains no nuts (unless added in toppings)"
            );
            dietaryInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));

            dietaryPanel.add(dietaryLabel);
            dietaryPanel.add(dietaryInfo);
            mainPanel.add(dietaryPanel);

            // ===== TIME TO PREPARE SECTION =====
            JPanel timeRow = new JPanel();
            timeRow.setLayout(new BoxLayout(timeRow, BoxLayout.X_AXIS));
            timeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            timeRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

            JLabel timeLabel = new JLabel("Time to prepare: ");
            timeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            timeRow.add(timeLabel);

            // Simple label for minutes
            JLabel minutesValueLabel = new JLabel("45 minutes");
            minutesValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            minutesValueLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            timeRow.add(minutesValueLabel);

            mainPanel.add(timeRow);

            // ===== BUTTONS ROW (Back | Edit | Delete) =====
            JPanel buttonsContainer = new JPanel(new BorderLayout());
            buttonsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            buttonsContainer.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));

            JButton backBtn = new JButton("Back");
            JPanel leftWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            leftWrap.add(backBtn);
            buttonsContainer.add(leftWrap, BorderLayout.WEST);

            JButton editBtn = new JButton("Edit");
            JPanel centerWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            centerWrap.add(editBtn);
            buttonsContainer.add(centerWrap, BorderLayout.CENTER);

            JButton deleteBtn = new JButton("Delete");
            JPanel rightWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rightWrap.add(deleteBtn);
            buttonsContainer.add(rightWrap, BorderLayout.EAST);

            mainPanel.add(buttonsContainer);

            // ===== FRAME SETUP =====
            frame.add(new JScrollPane(mainPanel));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
