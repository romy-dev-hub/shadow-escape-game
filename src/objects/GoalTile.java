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
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
