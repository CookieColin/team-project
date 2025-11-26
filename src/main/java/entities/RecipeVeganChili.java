package entities;

import javax.swing.*;
import java.awt.*;

public class RecipeVeganChili {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Vegan Chili");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            JLabel header = new JLabel("Ingredients:");
            header.setFont(new Font("SansSerif", Font.BOLD, 15));
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(header);

            JPanel ingredientListPanel = new JPanel();
            ingredientListPanel.setLayout(new BoxLayout(ingredientListPanel, BoxLayout.Y_AXIS));
            ingredientListPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 20, 5));
            ingredientListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            String[] ingredients = {
                    "• 1 tbsp olive oil",
                    "• 1 onion, chopped",
                    "• 3 cloves garlic, minced",
                    "• 1 red bell pepper, chopped",
                    "• 1 zucchini, chopped",
                    "• 1 can black beans (drained)",
                    "• 1 can kidney beans (drained)",
                    "• 1 can corn kernels (optional)",
                    "• 1 can crushed tomatoes",
                    "• 2 tbsp tomato paste",
                    "• 2 tsp chili powder",
                    "• 1 tsp cumin",
                    "• Salt and black pepper to taste",
                    "• Optional toppings: avocado, cilantro, lime"
            };

            for (String item : ingredients) {
                JLabel label = new JLabel(item);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
                ingredientListPanel.add(label);
            }

            mainPanel.add(ingredientListPanel);

            JLabel instructionsHeader = new JLabel("Instructions:");
            instructionsHeader.setFont(new Font("SansSerif", Font.BOLD, 15));
            instructionsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(instructionsHeader);

            JPanel instructionListPanel = new JPanel();
            instructionListPanel.setLayout(new BoxLayout(instructionListPanel, BoxLayout.Y_AXIS));
            instructionListPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 25, 5));
            instructionListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            String[] instructions = {
                    "1. Heat oil in a large pot over medium heat.",
                    "2. Sauté onion and garlic for 2–3 minutes.",
                    "3. Add bell pepper and zucchini; cook until soft.",
                    "4. Stir in chili powder, cumin, salt, and pepper.",
                    "5. Add beans, corn, crushed tomatoes, and tomato paste.",
                    "6. Simmer for 25–30 minutes, stirring occasionally.",
                    "7. Serve hot with your favorite toppings."
            };

            for (String step : instructions) {
                JLabel label = new JLabel(step);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
                instructionListPanel.add(label);
            }

            mainPanel.add(instructionListPanel);

            JPanel dietaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            dietaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 10));
            dietaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel dietaryLabel = new JLabel("Dietary restriction:");
            dietaryLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            JLabel dietaryInfo = new JLabel("Vegan, Gluten-free, Dairy-free, Nut-free");
            dietaryInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));

            dietaryPanel.add(dietaryLabel);
            dietaryPanel.add(dietaryInfo);
            mainPanel.add(dietaryPanel);

            JPanel timeRow = new JPanel();
            timeRow.setLayout(new BoxLayout(timeRow, BoxLayout.X_AXIS));
            timeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            timeRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

            JLabel timeLabel = new JLabel("Time to prepare: ");
            timeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            timeRow.add(timeLabel);

            JLabel minutesValueLabel = new JLabel("40 minutes");
            minutesValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            minutesValueLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            timeRow.add(minutesValueLabel);

            mainPanel.add(timeRow);

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

            frame.add(new JScrollPane(mainPanel));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
