package game;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private GameFrame gameFrame;

    public MainMenuPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.setPreferredSize(new Dimension(960, 640)); // Match game size
        this.setLayout(null); // Absolute positioning for buttons

        // Start button
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(380, 300, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.CYAN);
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.startGame();
            }
        });

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(380, 370, 200, 50);
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

        this.add(startButton);
        this.add(quitButton);
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
        g2.setColor(Color.CYAN);
        g2.setFont(new Font("Chiller", Font.BOLD, 80));
        g2.drawString("Shadow Escape", 280, 200);

        // Subtitle
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Navigate the maze, avoid your shadow!", 330, 250);

        g2.dispose();
    }
}