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

    public void draw(Graphics g) { // Keep messing with this.
        g.setColor(Color.GREEN);
        g.fillRoundRect(x, y, size, size, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
