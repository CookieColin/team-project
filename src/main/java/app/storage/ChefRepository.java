package app.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Chef;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads and persists the single-chef data set kept on each machine.
 */
public class ChefRepository {

    private static final String DEFAULT_RESOURCE = "chef.json";

    private final Path storagePath;
    private final ObjectMapper mapper;

    public ChefRepository(Path storagePath) {
        this.storagePath = storagePath;
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Chef loadChef() throws IOException {
        ensureStorageFile();
        try (InputStream in = Files.newInputStream(storagePath)) {
            return mapper.readValue(in, Chef.class);
        }
    }

    public void saveChef(Chef chef) throws IOException {
        createParentDirectories();
        mapper.writeValue(storagePath.toFile(), chef);
    }

    private void ensureStorageFile() throws IOException {
        if (Files.exists(storagePath)) {
            return;
        }
        createParentDirectories();
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_RESOURCE)) {
            if (resourceStream != null) {
                Files.copy(resourceStream, storagePath);
                return;
            }
        }
        saveChef(new Chef("Chef", null));
    }

    private void createParentDirectories() throws IOException {
        Path parent = storagePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
