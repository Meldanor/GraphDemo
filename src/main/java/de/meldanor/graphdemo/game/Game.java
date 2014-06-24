package de.meldanor.graphdemo.game;

import java.awt.Point;

public class Game {

    public enum FieldType {
        FREE, BARRIER, ENEMY, BOOSTER, PLAYER, GOAL;

        private static FieldType[] vals = values();

        public static FieldType getByOrdinal(int ord) {
            return vals[ord];
        }
    }

    private int[][] field;

    private int width;
    private int height;

    private Point playerPos;
    private Point goalPos;

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

    public void setPlayer(int x, int y) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight())
            return;

        // Free old position
        if (this.playerPos != null) {
            this.field[playerPos.y][playerPos.x] = FieldType.FREE.ordinal();
        }

        this.playerPos = new Point(x, y);

        this.field[y][x] = FieldType.PLAYER.ordinal();
    }

    public Point getPlayerPos() {
        return playerPos;
    }

    public void setGoal(int x, int y) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight())
            return;

        // Free old position
        if (this.goalPos != null) {
            this.field[goalPos.y][goalPos.x] = FieldType.FREE.ordinal();
        }
        this.goalPos = new Point(x, y);
        this.field[y][x] = FieldType.GOAL.ordinal();
    }

    public Point getGoalPos() {
        return goalPos;
    }

    public FieldType getFieldTypeAt(int x, int y) {
        return FieldType.getByOrdinal(field[y][x]);
    }

    public FieldType getFieldTypeAt(Point p) {
        return getFieldTypeAt(p.x, p.y);
    }

    public boolean isWalkable(int x, int y) {
        FieldType t = getFieldTypeAt(x, y);
        switch (t) {
            case BARRIER :
                return false;
            default :
                return true;
        }
    }
}
