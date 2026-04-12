package bruderkartoffel.gui;

import javax.swing.*;
import java.awt.*;

public class PauseMenuPanel extends JPanel {

    private final JLabel resumeLabel;
    private final JLabel exitLabel;

    public PauseMenuPanel() {
        //setBounds(0, 0, size.width, size.height);
        setLayout(null);
        setOpaque(false);


        this.resumeLabel = new JLabel("Resume");
        resumeLabel.setForeground(Color.WHITE);
        resumeLabel.setBackground(new Color(80, 80, 80, 216));
        resumeLabel.setFont(new Font("Bold", Font.BOLD, 25));
        resumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resumeLabel.setOpaque(true);
        resumeLabel.setBounds(48, 48, 400, 48);

        this.exitLabel = new JLabel("Exit");
        exitLabel.setForeground(Color.WHITE);
        exitLabel.setBackground(new Color(80, 80, 80, 216));
        exitLabel.setFont(new Font("Bold", Font.BOLD, 25));
        exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        exitLabel.setOpaque(true);
        exitLabel.setBounds(48, 104, 400, 48);

        add(resumeLabel);
        add(exitLabel);

        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
