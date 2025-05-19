package game;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import entities.Player;
import entities.Shadow;
import objects.GoalTile;
import utils.MazeGenerator;
import utils.GameTimer;
import utils.Constants;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Player player;
    private Shadow shadow;
    private GoalTile goalTile;
    private MazeGenerator mazeGen;
    private int[][] maze;
    private GameTimer timer;
    private boolean gameOver;
    private volatile Thread gameThread;
    private final Random rand = new Random();
    private final GameFrame gameFrame;

    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public GamePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.setFocusable(true);

        initializeGame();
        startGameThread();
    }

    private void initializeGame() {
        mazeGen = new MazeGenerator(Constants.MAX_COLS, Constants.MAX_ROWS);
        maze = mazeGen.getMaze();

        // Place player at a valid path cell
        int playerCol = 1, playerRow = 1;
        while (maze[playerRow][playerCol] == 0) {
            playerCol++;
            if (playerCol >= Constants.MAX_COLS) {
                playerCol = 1;
                playerRow++;
            }
        }
        player = new Player(playerCol * Constants.TILE_SIZE, playerRow * Constants.TILE_SIZE,
                Constants.TILE_SIZE, Constants.PLAYER_SPEED, maze);

        // Place shadow at a random valid path cell, away from player and goal
        int shadowCol, shadowRow;
        do {
            shadowCol = rand.nextInt(Constants.MAX_COLS);
            shadowRow = rand.nextInt(Constants.MAX_ROWS);
        } while (maze[shadowRow][shadowCol] == 0 ||
                (shadowCol == playerCol && shadowRow == playerRow) ||
                (shadowCol == mazeGen.getGoalX() && shadowRow == mazeGen.getGoalY()));
        shadow = new Shadow(shadowCol * Constants.TILE_SIZE, shadowRow * Constants.TILE_SIZE,
                Constants.TILE_SIZE, Constants.PLAYER_SPEED, maze);

        // Place goal tile
        goalTile = new GoalTile(mazeGen.getGoalX() * Constants.TILE_SIZE, mazeGen.getGoalY() * Constants.TILE_SIZE,
                Constants.TILE_SIZE);

        timer = new GameTimer();
        timer.start();
        gameOver = false;
    }

    public void startGameThread() {
        stopGameThread();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {
        if (gameThread != null) {
            Thread tempThread = gameThread;
            gameThread = null;
            try {
                tempThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000.0 / Constants.FPS;
        long lastTime = System.currentTimeMillis();
        long currentTime;

        while (gameThread == Thread.currentThread()) {
            currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= drawInterval) {
                if (!gameOver) {
                    update();
                }
                repaint();
                lastTime = currentTime;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // Handle player movement
        if (upPressed) {
            player.move("UP");
            shadow.moveOpposite("UP");
        }
        if (downPressed) {
            player.move("DOWN");
            shadow.moveOpposite("DOWN");
        }
        if (leftPressed) {
            player.move("LEFT");
            shadow.moveOpposite("LEFT");
        }
        if (rightPressed) {
            player.move("RIGHT");
            shadow.moveOpposite("RIGHT");
        }

        // Check collisions
        if (player.getBounds().intersects(shadow.getBounds())) {
            gameOver = true;
            timer.stop();
            String elapsedTime = timer.getElapsedTime();
            System.out.println("Collision! Game Over. Time: " + elapsedTime);
            gameFrame.showEndGame(false, elapsedTime);
        }

        // Check win condition
        if (player.getBounds().intersects(goalTile.getBounds())) {
            gameOver = true;
            timer.stop();
            String elapsedTime = timer.getElapsedTime();
            System.out.println("Goal reached! You Win! Time: " + elapsedTime);
            gameFrame.showEndGame(true, elapsedTime);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw maze border
        g2.setColor(Color.CYAN);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Draw maze
        for (int y = 0; y < Constants.MAX_ROWS; y++) {
            for (int x = 0; x < Constants.MAX_COLS; x++) {
                g2.setColor(maze[y][x] == 1 ? Color.DARK_GRAY : Color.BLACK);
                g2.fillRect(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE,
                        Constants.TILE_SIZE, Constants.TILE_SIZE);
                g2.setColor(Color.WHITE);
                g2.drawRect(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE,
                        Constants.TILE_SIZE, Constants.TILE_SIZE);
            }
        }

        // Draw game objects
        goalTile.draw(g2);
        player.draw(g2);
        shadow.draw(g2);

        // Draw UI
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Time: " + timer.getElapsedTime(), 10, 20);

        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}