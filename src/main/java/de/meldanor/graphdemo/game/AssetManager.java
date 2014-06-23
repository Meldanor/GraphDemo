package de.meldanor.graphdemo.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.meldanor.graphdemo.game.Game.FieldType;
import javafx.scene.image.Image;

public class AssetManager {

    public Map<String, Image> images;

    public AssetManager() {
        this.images = new HashMap<String, Image>();
    }

    public void loadImages(Path dir) throws IOException {
        Files.list(dir).forEach(e -> {
            String fileName = e.getFileName().toString();
            try {
                Image image = new Image(e.toUri().toURL().toString());

                images.put(fileName.substring(0, fileName.lastIndexOf('.')).toUpperCase(), image);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
    
    public Image getImageFor(FieldType fieldType) {
        return images.get(fieldType.name());
    }

}
