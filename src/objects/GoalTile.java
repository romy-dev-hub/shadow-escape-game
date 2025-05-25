package objects;

import java.awt.*;

public class GoalTile {
    private int x, y;
    private final int size;

    public GoalTile(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics g) {
        int offset = (size - (size * 3/4)) / 2; // Calculate offset for centering
        int smallerSize = size * 3/4; // Make entity 75% of tile size
        
        g.setColor(new Color(188, 231, 132)); // Changed this to a green that matches the background
        g.fillRoundRect(x + offset, y + offset, smallerSize, smallerSize, 32, 32);
    }

    public Rectangle getBounds() {
        int offset = (size - (size * 3/4)) / 2;
        int smallerSize = size * 3/4;
        return new Rectangle(x + offset, y + offset, smallerSize, smallerSize);
    }
}
