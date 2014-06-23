package de.meldanor.graphdemo.game;

public class Game {

    public enum FieldType {
        FREE, BARRIER, ENEMY, BOOSTER;

        private static FieldType[] vals = values();

        public static FieldType getByOrdinal(int ord) {
            return vals[ord];
        }
    }

    private int[][] field;

    private int width;
    private int height;

    public Game(int[][] field) {
        this.field = field;
        this.width = field[0].length;
        this.height = field.length;
    }

    public int[][] getField() {
        return field;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public FieldType getFieldTypeAt(int x, int y) {
        return FieldType.getByOrdinal(field[y][x]);
    }
}
