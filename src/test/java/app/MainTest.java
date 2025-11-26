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
        // verify the parent directory of the storage path is ~/.recipe-book
    void testResolveStoragePath_parentDirectoryCorrect() {
        Main main = new Main();
        Path path = invokeResolveStoragePath(main);

        String userHome = System.getProperty("user.home");
        Path expectedParent = Path.of(userHome, ".recipe-book");
        assertEquals(expectedParent, path.getParent());
    }

    @Test
        // verify that loadChef returns a Chef with the expected stub name
    void testLoadChef_returnsChefWithStubName() {
        Main main = new Main();

        ChefRepository stubRepo = new ChefRepositoryStub(false);
        setPrivateField(main, "chefRepository", stubRepo);

        Chef chef = invokeLoadChef(main);

        assertNotNull(chef, "Chef should not be null");
        assertEquals("Stub Chef", chef.getName(), "Chef name should come from the stub");
    }

    @Test
        // verify that the stub throws an IOException when configured to fail
    void testChefRepositoryStub_throwsOnFailureFlag() {
        ChefRepositoryStub failingStub = new ChefRepositoryStub(true);

        assertThrows(IOException.class, failingStub::loadChef,
                "Expected stub to throw IOException when shouldThrow is true");
    }

    @Test
        // verify that setPrivateField can overwrite an existing chefRepository instance
    void testSetPrivateField_overwritesRepository() throws NoSuchFieldException, IllegalAccessException {
        Main main = new Main();

        ChefRepositoryStub firstStub = new ChefRepositoryStub(false);
        ChefRepositoryStub secondStub = new ChefRepositoryStub(false);

        // first injection
        setPrivateField(main, "chefRepository", firstStub);
        // overwrite with second
        setPrivateField(main, "chefRepository", secondStub);

        var field = Main.class.getDeclaredField("chefRepository");
        field.setAccessible(true);
        Object value = field.get(main);

        assertSame(secondStub, value, "chefRepository field should reference the last injected stub");
    }
}