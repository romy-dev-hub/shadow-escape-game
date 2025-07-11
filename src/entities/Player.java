package entities;

import java.awt.*;
import utils.Constants;

public class Player {

    private int x, y;
    private final int size;
    private final int speed;
    private int[][] maze;

    public Player(int startX, int startY, int size, int speed, int[][] maze) {
        this.x = startX;
        this.y = startY;
        this.size = size;
        this.speed = speed;
        this.maze = maze;
    }

    public void move(String direction) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case "UP":
                newY -= speed;
                break;
            case "DOWN":
                newY += speed;
                break;
            case "LEFT":
                newX -= speed;
                break;
            case "RIGHT":
                newX += speed;
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
        int offset = (size - (size * 3/4)) / 2; // Calculate offset for centering
        int smallerSize = size * 3/4; // Make entity 75% of tile size
        
        g.setColor(new Color(66, 217, 200)); // Changed to a light cyan
        g.fillRoundRect(x + offset, y + offset, smallerSize, smallerSize, 32, 32);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        int offset = (size - (size * 3/4)) / 2;
        int smallerSize = size * 3/4;
        return new Rectangle(x + offset, y + offset, smallerSize, smallerSize);
    }
}
