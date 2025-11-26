package app;

import app.storage.ChefRepository;
import entities.Chef;
import org.junit.jupiter.api.Test;
import entities.Recipe;
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
// validate that resolveStoragePath points to a JSON file
    void testResolveStoragePath_hasJsonExtension() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        assertTrue(path.toString().endsWith("chef.json"));
    }

    @Test
// ensure that resolveStoragePath uses hidden directory
    void testResolveStoragePath_hiddenDirectory() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        assertTrue(path.toString().contains(".recipe-book"));
    }

    @Test
// test loadChef returns non-null even if repository returns empty Chef
    void testLoadChef_repositoryReturnsEmptyChef() {
        Main main = new Main();
        ChefRepository fakeRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() {
                return new Chef(); // empty
            }
        };
        setPrivateField(main, "chefRepository", fakeRepo);
        Chef chef = invokeLoadChef(main);
        assertNotNull(chef);
    }

    @Test
// loadChef returns chef with expected empty recipe list
    void testLoadChef_emptyChefHasEmptyRecipes() {
        Main main = new Main();
        ChefRepository fakeRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() { return new Chef("A", new ArrayList<>()); }
        };
        setPrivateField(main, "chefRepository", fakeRepo);
        Chef chef = invokeLoadChef(main);
        assertTrue(chef.getRecipes().isEmpty());
    }



    @Test
// ensure storage path always resolves under home directory
    void testResolveStoragePath_startsWithHomeDirectory() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        assertTrue(path.startsWith(System.getProperty("user.home")));
    }

    @Test
// test that reflection method for loadChef is private
    void testLoadChefMethodIsPrivate() throws Exception {
        var method = Main.class.getDeclaredMethod("loadChef");
        assertFalse(method.canAccess(new Main())); // not public
    }

    @Test
// test that resolveStoragePath method is private
    void testResolveStoragePathMethodIsPrivate() throws Exception {
        var method = Main.class.getDeclaredMethod("resolveStoragePath");
        assertFalse(method.canAccess(new Main()));
    }

    @Test
// test that setPrivateField actually overwrites the chefRepository field
    void testSetPrivateField_overwritesRepository() {
        Main main = new Main();
        ChefRepository fakeRepo = new ChefRepositoryStub(false);
        setPrivateField(main, "chefRepository", fakeRepo);
        Chef loaded = invokeLoadChef(main);
        assertEquals("Stub Chef", loaded.getName());
    }



    @Test
// test that stub repository receiving correct constructor path doesn't break tests
    void testChefRepositoryStubIgnoresPath() {
        ChefRepositoryStub stub = new ChefRepositoryStub(false);
        assertDoesNotThrow(stub::loadChef);
    }

    @Test
// test that path uses correct parent directory
    void testResolveStoragePath_parentDirectoryCorrect() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);
        assertEquals(".recipe-book", path.getParent().getFileName().toString());
    }

    @Test
// ensure that loadChef returns Chef object with name preserved
    void testLoadChef_preservesNameFromRepository() {
        Main main = new Main();

        ChefRepository fakeRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() { return new Chef("Custom Name", new ArrayList<>()); }
        };
        setPrivateField(main, "chefRepository", fakeRepo);

        Chef chef = invokeLoadChef(main);
        assertEquals("Custom Name", chef.getName());
    }

    @Test
// test loadChef when recipes list is prepopulated
    void testLoadChef_withPrePopulatedRecipes() {
        Main main = new Main();
        ChefRepository fakeRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() {
                Chef c = new Chef();
                c.addRecipe(new Recipe());
                return c;
            }
        };
        setPrivateField(main, "chefRepository", fakeRepo);
        Chef chef = invokeLoadChef(main);
        assertEquals(1, chef.getRecipes().size());
    }

    @Test
// ensure loadChef handles repository with unusual values
    void testLoadChef_handlesWeirdChefValues() {
        Main main = new Main();
        ChefRepository fakeRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() {
                return new Chef("", null); // strange but allowed
            }
        };
        setPrivateField(main, "chefRepository", fakeRepo);
        Chef chef = invokeLoadChef(main);
        assertNotNull(chef.getRecipes()); // null must be sanitized
    }

    @Test
        // verify that resolveStoragePath returns an absolute path
    void testResolveStoragePath_isAbsolutePath() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);

        assertTrue(path.isAbsolute(), "Storage path should be absolute");
    }

    @Test
        // verify that the storage file name is exactly chef.json
    void testResolveStoragePath_fileNameIsChefJson() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);

        assertEquals("chef.json", path.getFileName().toString());
    }

    @Test
        // calling resolveStoragePath multiple times should return the same path
    void testResolveStoragePath_isDeterministic() {
        Main main = new Main();
        Path first = invokeResolveStoragePath(main);
        Path second = invokeResolveStoragePath(main);

        assertEquals(first, second, "resolveStoragePath should be deterministic");
    }

    @Test
        // verify that loadChef always returns a Chef instance
    void testLoadChef_returnsChefInstance() {
        Main main = new Main();
        ChefRepository fakeRepo = new ChefRepositoryStub(false);
        setPrivateField(main, "chefRepository", fakeRepo);

        Chef chef = invokeLoadChef(main);

        assertNotNull(chef);
        assertInstanceOf(Chef.class, chef);
    }

    @Test
        // verify that stub repository always returns a Chef with name 'Stub Chef'
    void testChefRepositoryStub_returnsStubChefName() throws IOException {
        ChefRepositoryStub stub = new ChefRepositoryStub(false);

        Chef chef = stub.loadChef();

        assertNotNull(chef);
        assertEquals("Stub Chef", chef.getName());
    }

    @Test
        // verify that stub repository returns a Chef with a non-null recipes list
    void testChefRepositoryStub_recipesListNotNull() throws IOException {
        ChefRepositoryStub stub = new ChefRepositoryStub(false);

        Chef chef = stub.loadChef();

        assertNotNull(chef.getRecipes(), "Recipes list from stub should not be null");
        assertTrue(chef.getRecipes().isEmpty(), "Default stub recipes list should be empty");
    }

    @Test
        // ensure setPrivateField can inject a custom anonymous ChefRepository
    void testSetPrivateField_injectsAnonymousRepository() {
        Main main = new Main();

        ChefRepository customRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() {
                return new Chef("Anonymous Chef", new ArrayList<>());
            }
        };

        setPrivateField(main, "chefRepository", customRepo);

        Chef chef = invokeLoadChef(main);
        assertEquals("Anonymous Chef", chef.getName());
    }

    @Test
        // verify that loadChef correctly reflects a repository that pre-populates multiple recipes
    void testLoadChef_chefWithMultipleRecipes() {
        Main main = new Main();

        ChefRepository fakeRepo = new ChefRepositoryStub(false) {
            @Override
            public Chef loadChef() {
                Chef c = new Chef("Multi Chef", new ArrayList<>());
                c.addRecipe(new Recipe());
                c.addRecipe(new Recipe());
                return c;
            }
        };

        setPrivateField(main, "chefRepository", fakeRepo);
        Chef chef = invokeLoadChef(main);

        assertEquals("Multi Chef", chef.getName());
        assertEquals(2, chef.getRecipes().size());
    }

    @Test
        // verify that the parent directory of the storage path is not null
    void testResolveStoragePath_parentDirectoryNotNull() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);

        assertNotNull(path.getParent(), "Storage path should have a parent directory");
    }

}