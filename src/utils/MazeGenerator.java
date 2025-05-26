package utils;

import java.util.Random;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class MazeGenerator {
    private final int width, height;
    private final int[][] maze;
    private final Random rand = new Random();
    private int goalX, goalY;
    private int shadowX, shadowY;

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
        placeShadewTile();
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
        } while (maze[goalY][goalX] == 0 || (Math.abs(goalX - 1) + Math.abs(goalY - 1) < 20)); // Made sure the goal is at least 20 steps away from the start
    }

    private void placeShadewTile() {
        // System.out.println("Starting shadow placement...");
        // Declaring variables to use BFS algorithm
        Queue<Tile> queue = new ArrayDeque<>();
        HashMap<Tile, Tile> parents = new HashMap<>();
        boolean[][] visited = new boolean[height][width];
        Tile start = new Tile(1, 1);
        queue.add(start);
        visited[1][1] = true;
        // System.out.println("BFS initialized from start position (1,1)");

        // BFS to find all reachable cells from start
        while (!queue.isEmpty()) {
            Tile cell = queue.poll();
            int x = cell.getX();
            int y = cell.getY();

            // check if the current cell is the goal
            if (x == goalX && y == goalY) {
                // System.out.println("Path to goal found at (" + goalX + "," + goalY + ")");
                break;
            }

            // Check all 4 directions
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[ny][nx] && maze[ny][nx] == 1) {
                    Tile next = new Tile(nx, ny);
                    queue.add(next);
                    visited[ny][nx] = true;
                    parents.put(next, cell);
                }
            }
        }

        // Print the parents map
        // System.out.println("Parents map contents:");
        // for (HashMap.Entry<Tile, Tile> entry : parents.entrySet()) {
        //     Tile child = entry.getKey();
        //     Tile parent = entry.getValue();
        //     System.out.println("Cell " + child + " has parent " + parent);
        // }


        // Fix the problme with the path reconstruction

        // System.out.println("BFS compdleted, reconstructing path...");
        // Retrieve the path from the goal to the player
        List<Tile> path = new ArrayList<>();
        Tile current = new Tile(goalX, goalY);
        
        while (current.getX() != 1 || current.getY() != 1) {
            current = parents.get(current);

            // if (current[0] == 1 && current[1] == 1) {
            if (current == null) {
                // System.out.println("Warning: Path reconstruction failed!");
                break;
            }

            // System.out.println("Current cell: [" + current.getX() + "," + current.getY() + "]");
            path.add(current);
        }

        // System.out.println("Path length: " + path.size() + " cells");
        // place the shadow tile in the middle of the path
        if (!path.isEmpty()) {
            Tile shadowTile = path.get(path.size() / 2);
            shadowX = shadowTile.getX();
            shadowY = shadowTile.getY();
            // System.out.println("Shadow placed at position (" + shadowX + "," + shadowY + ")");
        } else {
            // Fallback if path is empty
            shadowX = 3;
            shadowY = 3;
            // System.out.println("No path found, shadow placed at default position (3,3)");
        }
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

    public int getShadowX() {
        return shadowX;
    }

    public int getShadowY() {
        return shadowY;
    }
}
