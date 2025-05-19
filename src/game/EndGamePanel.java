package game;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGamePanel extends JPanel {
    private final GameFrame gameFrame;
    private final boolean won;
    private final String time;

    public EndGamePanel(GameFrame gameFrame, boolean won, String time) {
        this.gameFrame = gameFrame;
        this.won = won;
        this.time = time != null ? time : "00:00"; // Fallback if time is null
        this.setPreferredSize(new Dimension(960, 640));
        this.setLayout(null);

        // Play Again button - centered
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(380, 280, 200, 50); // Adjusted y-position
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setBackground(Color.DARK_GRAY);
        playAgainButton.setForeground(Color.CYAN);
        playAgainButton.setFocusPainted(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.resetGame();
            }
        });

        // Main Menu button
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBounds(380, 350, 200, 50); // Adjusted y-position
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuButton.setBackground(Color.DARK_GRAY);
        mainMenuButton.setForeground(Color.CYAN);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.showMainMenu();
            }
        });

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(380, 420, 200, 50); // Adjusted y-position
        quitButton.setFont(new Font("Arial", Font.BOLD, 20));
        quitButton.setBackground(Color.DARK_GRAY);
        quitButton.setForeground(Color.RED);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(playAgainButton);
        this.add(mainMenuButton);
        this.add(quitButton);

        // Force repaint to ensure buttons are drawn
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, Color.BLACK,
                960, 640, new Color(0, 50, 50));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, 960, 640);

        // Title
        g2.setColor(won ? Color.GREEN : Color.RED);
        g2.setFont(new Font("Chiller", Font.BOLD, 80));
        String title = won ? "Victory!" : "Game Over";
        FontMetrics fmTitle = g2.getFontMetrics();
        int titleWidth = fmTitle.stringWidth(title);
        g2.drawString(title, (960 - titleWidth) / 2, 160); // Centered

        // Subtitle
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.setColor(Color.WHITE);
        String subtitle = won ? "Escape Time: " + time : "The shadow caught you!";
        FontMetrics fmSubtitle = g2.getFontMetrics();
        int subtitleWidth = fmSubtitle.stringWidth(subtitle);
        g2.drawString(subtitle, (960 - subtitleWidth) / 2, 230); // Centered

        g2.dispose();
    }
}