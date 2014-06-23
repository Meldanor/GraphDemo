package de.meldanor.graphdemo;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.application.Application;
import de.meldanor.graphdemo.game.AssetManager;
import de.meldanor.graphdemo.game.Game;
import de.meldanor.graphdemo.game.GameGenerator;
import de.meldanor.graphdemo.gui.MainGUI;

public class Core {
    public static GameGenerator gameGenerator;
    public static Game currentGame;
    public static AssetManager assetManager;

    public static void main(String[] args) throws URISyntaxException, IOException {

        Core.gameGenerator = new GameGenerator();
        Core.assetManager = new AssetManager();

        Application.launch(MainGUI.class, args);

    }
}
