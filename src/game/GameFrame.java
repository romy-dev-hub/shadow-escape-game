package game;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Container;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private Container container;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;

    public GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Shadow Escape");

        cardLayout = new CardLayout();
        container = this.getContentPane();
        container.setLayout(cardLayout);

        mainMenuPanel = new MainMenuPanel(this);
        gamePanel = new GamePanel(this); // Pass GameFrame to GamePanel

        container.add("MainMenu", mainMenuPanel);
        container.add("Game", gamePanel);

        cardLayout.show(container, "MainMenu");

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void startGame() {
        cardLayout.show(container, "Game");
        gamePanel.requestFocusInWindow();
    }

    public void showMainMenu() {
        cardLayout.show(container, "MainMenu");
        mainMenuPanel.requestFocusInWindow();
    }

    public void resetGame() {
        container.remove(gamePanel);
        gamePanel = new GamePanel(this); // Pass GameFrame to new GamePanel
        container.add("Game", gamePanel);
        cardLayout.show(container, "Game");
        gamePanel.requestFocusInWindow();
    }
}