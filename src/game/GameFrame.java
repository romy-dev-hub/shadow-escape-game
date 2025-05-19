package game;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Container;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private Container container;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private EndGamePanel endGamePanel;

    public GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Shadow Escape");

        cardLayout = new CardLayout();
        container = this.getContentPane();
        container.setLayout(cardLayout);

        mainMenuPanel = new MainMenuPanel(this);
        gamePanel = new GamePanel(this);

        container.add("MainMenu", mainMenuPanel);
        container.add("Game", gamePanel);

        cardLayout.show(container, "MainMenu");

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void startGame() {
        container.remove(gamePanel);
        gamePanel = new GamePanel(this);
        container.add("Game", gamePanel);
        cardLayout.show(container, "Game");
        gamePanel.requestFocusInWindow();
    }

    public void showMainMenu() {
        cardLayout.show(container, "MainMenu");
        mainMenuPanel.requestFocusInWindow();
    }

    public void showEndGame(boolean won, String time) {
        if (endGamePanel != null) {
            container.remove(endGamePanel);
        }
        endGamePanel = new EndGamePanel(this, won, time);
        container.add("EndGame", endGamePanel);
        cardLayout.show(container, "EndGame");
        endGamePanel.requestFocusInWindow();
        container.revalidate();
        container.repaint();
    }

    public void resetGame() {
        startGame();
    }
}