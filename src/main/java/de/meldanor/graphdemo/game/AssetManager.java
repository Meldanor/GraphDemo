package de.meldanor.graphdemo.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.meldanor.graphdemo.Core;
import de.meldanor.graphdemo.game.Game.FieldType;
import javafx.scene.image.Image;

public class AssetManager {

    public Map<String, Image> images16;
    public Map<String, Image> images24;
    public Map<String, Image> images32;

    public AssetManager() {
        this.images16 = new HashMap<String, Image>();
        this.images24 = new HashMap<String, Image>();
        this.images32 = new HashMap<String, Image>();
    }

    public void loadImages(Path dir) throws IOException {
        Files.list(dir).forEach(e -> {
            String fileName = e.getFileName().toString();
            try {
                Image image = new Image(e.toUri().toURL().toString(), 16, 16, true, true);
                images16.put(fileName.substring(0, fileName.lastIndexOf('.')).toUpperCase(), image);

                image = new Image(e.toUri().toURL().toString(), 24, 24, true, true);
                images24.put(fileName.substring(0, fileName.lastIndexOf('.')).toUpperCase(), image);

                image = new Image(e.toUri().toURL().toString(), 32, 32, true, true);
                images32.put(fileName.substring(0, fileName.lastIndexOf('.')).toUpperCase(), image);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    public Image getImageFor(FieldType fieldType) {
        switch (Core.TILESIZE) {
            case 16 :
                return images16.get(fieldType.name());
            case 24 :
                return images24.get(fieldType.name());
            case 32 :
                return images32.get(fieldType.name());
        }
        return null;
    }
}
