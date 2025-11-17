package entities;

import java.util.Iterator;
import java.util.List;

public class Delete_Recipe {

    /**
     * Deletes a recipe by its name from the provided list.
     */
    public static boolean deleteByName(List<Recipe> recipes, String name) {
        Iterator<Recipe> it = recipes.iterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            if (r.getName().equalsIgnoreCase(name)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a specific Recipe object.
     */
    public static boolean deleteObject(List<Recipe> recipes, Recipe recipe) {
        return recipes.remove(recipe);
    }
}
