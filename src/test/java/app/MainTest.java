package app;

import app.storage.ChefRepository;
import entities.Chef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    // ensures that correct main and JSON path is returned and able to be viewed by user
    void testResolveStoragePath_returnsCorrectPath() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        String userHome = System.getProperty("user.home");
        assertEquals(Path.of(userHome, ".recipe-book", "chef.json"), path);
    }

    @Test
    // ensures user Chef can be called and not empty if there exist non-empty fields
    void testLoadChef_success() {
        Main main = new Main();

        // Inject a stub ChefRepository that returns a real Chef
        ChefRepository fakeRepo = new ChefRepositoryStub(false);
        setPrivateField(main, "chefRepository", fakeRepo);

        Chef chef = invokeLoadChef(main);
        assertNotNull(chef);
        assertEquals("Stub Chef", chef.getName());
    }

    // Test double methods for MainTest Unittests

    static class ChefRepositoryStub extends ChefRepository {
        private final boolean shouldThrow;

        public ChefRepositoryStub(boolean shouldThrow) {
            super(Path.of("")); // Not used
            this.shouldThrow = shouldThrow;
        }

        @Override
        public Chef loadChef() throws IOException {
            if (shouldThrow) throw new IOException("Fake I/O Error");
            return new Chef("Stub Chef", new ArrayList<>());
        }
    }

    // Helper methods to access private Main methods

    private Path invokeResolveStoragePath(Main main) {
        try {
            var method = Main.class.getDeclaredMethod("resolveStoragePath");
            method.setAccessible(true);
            return (Path) method.invoke(main);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Chef invokeLoadChef(Main main) {
        try {
            var method = Main.class.getDeclaredMethod("loadChef");
            method.setAccessible(true);
            return (Chef) method.invoke(main);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = Main.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMainInstantiation() {
        Main main = new Main();
        assertNotNull(main);
    }

    @Test
    void testResolveStoragePath_containsChefJson() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        assertTrue(path.toString().endsWith("chef.json"));
    }

    @Test
    void testResolveStoragePath_containsRecipeBookFolder() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        assertTrue(path.toString().contains(".recipe-book"));
    }

    @Test
    void testChefRepositoryStub_returnsChefWithEmptyRecipes() {
        ChefRepository stub = new ChefRepositoryStub(false);
        try {
            Chef chef = stub.loadChef();
            assertNotNull(chef.getRecipes());
            assertTrue(chef.getRecipes().isEmpty());
        } catch (IOException e) {
            fail("Should not throw exception");
        }
    }
}