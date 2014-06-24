package de.meldanor.graphdemo.game;

import java.util.Arrays;
import java.util.Random;

import de.meldanor.graphdemo.game.Game.FieldType;

public class GameGenerator {

    private Random rand;

    public GameGenerator() {
        this.rand = new Random();
    }

    public Game generate(int size, int barriers, int enemies, int booster) {
        return generate(size, size, barriers, enemies, booster);
    }

    public Game generate(int width, int height, int barriers, int enemies, int booster) {

        int[][] field = new int[height][];

        for (int i = 0; i < field.length; i++) {
            int[] bs = new int[width];
            Arrays.fill(bs, Game.FieldType.FREE.ordinal());
            field[i] = bs;
        }

        double total = width * height;
        System.out.println(barriers);
        System.out.println(enemies);
        System.out.println(booster);
        barriers = (int) (total * (barriers / 100.0));
        enemies = (int) (total * (enemies / 100.0));
        booster = (int) (total * (booster / 100.0));
        System.out.println(barriers);
        System.out.println(enemies);
        System.out.println(booster);

        generateFieldType(field, barriers, FieldType.BARRIER);
        generateFieldType(field, enemies, FieldType.ENEMY);
        generateFieldType(field, booster, FieldType.BOOSTER);

        return new Game(field);
    }

    private void generateFieldType(int[][] field, int max, FieldType fieldType) {
        for (int i = 0; i < max; ++i) {
            int y = rand.nextInt(field.length);
            int x = rand.nextInt(field[0].length);
            if (field[y][x] == Game.FieldType.FREE.ordinal())
                field[y][x] = fieldType.ordinal();
        }
    }
}
