package entities;

import java.util.*;


public class Chef {
    private String name;
    private ArrayList<Recipe> recipes;

    public Chef(String name) {
        this.name = name;

    }

    public void loadRecipes(List<Recipe> recip) {
        for (int i = 0; i < recip.size(); i++) {
            recipes.add(recip.get(i));
        }
    }

}
