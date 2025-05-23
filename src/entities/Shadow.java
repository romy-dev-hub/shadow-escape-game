package entities;

import java.awt.*;
import utils.Constants;

public class Shadow {
    private int x, y;
    private final int size;
    private final int speed;
    private int[][] maze;

    public Shadow(int startX, int startY, int size, int speed, int[][] maze) {
        this.x = startX;
        this.y = startY;
        this.size = size;
        this.speed = speed;
        this.maze = maze;
    }

    public void moveOpposite(String direction) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case "UP":
                newY += speed; // Opposite of player UP
                break;
            case "DOWN":
                newY -= speed; // Opposite of player DOWN
                break;
            case "LEFT":
                newX += speed; // Opposite of player LEFT
                break;
            case "RIGHT":
                newX -= speed; // Opposite of player RIGHT
                break;
        }

        // Check maze boundaries and walls
        if (newX >= 0 && newX + size <= Constants.SCREEN_WIDTH && newY >= 0 && newY + size <= Constants.SCREEN_HEIGHT) {
            int nextCol = newX / Constants.TILE_SIZE;
            int nextRow = newY / Constants.TILE_SIZE;
            int rightCol = (newX + size - 1) / Constants.TILE_SIZE;
            int bottomRow = (newY + size - 1) / Constants.TILE_SIZE;

            if (maze[nextRow][nextCol] == 1 && maze[nextRow][rightCol] == 1 &&
                    maze[bottomRow][nextCol] == 1 && maze[bottomRow][rightCol] == 1) {
                x = newX;
                y = newY;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRoundRect(x, y, size, size, 32, 32);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}