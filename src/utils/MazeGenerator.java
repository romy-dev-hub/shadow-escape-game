package utils;

import java.util.Random;

public class MazeGenerator {
    private final int width, height;
    private final int[][] maze;
    private final Random rand = new Random();
    private int goalX, goalY;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = 0; // Initialize as walls
            }
        }
        generateMaze(1, 1);
        placeGoalTile();
    }

    public void generateMaze(int cx, int cy) {
        maze[cy][cx] = 1; // Mark current cell as path
        int[] directions = {1, 2, 3, 4}; // right, down, left, up
        shuffle(directions);

        for (int direction : directions) {
            int nx = cx;
            int ny = cy;

            switch (direction) {
                case 1: nx += 2; break; // move right
                case 2: ny += 2; break; // move down
                case 3: nx -= 2; break; // move left
                case 4: ny -= 2; break; // move up
            }

            if (nx >= 0 && nx < width && ny >= 0 && ny < height && maze[ny][nx] == 0) {
                maze[ny][nx] = 1;
                maze[cy + (ny - cy) / 2][cx + (nx - cx) / 2] = 1; // Connect cells
                generateMaze(nx, ny);
            }
        }
    }

    private void placeGoalTile() {
        // Place goal far from start (1,1)
        do {
            goalX = rand.nextInt(width);
            goalY = rand.nextInt(height);
        } while (maze[goalY][goalX] == 0 || (Math.abs(goalX - 1) + Math.abs(goalY - 1) < 15));
    }

    private void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getGoalX() {
        return goalX;
    }

    public int getGoalY() {
        return goalY;
    }
}