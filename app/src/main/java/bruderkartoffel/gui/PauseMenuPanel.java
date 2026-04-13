package bruderkartoffel.gui;

import bruderkartoffel.game.core.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class PauseMenuPanel extends JPanel {

    private final JLabel resumeLabel;
    private final JLabel exitLabel;

    private GameState.Phase pausedPhase;

    public PauseMenuPanel(Runnable onExit, Consumer<GameState.Phase> onResume) {
        setLayout(null);
        setOpaque(false);


        this.resumeLabel = new JLabel("Resume");
        resumeLabel.setForeground(Color.WHITE);
        resumeLabel.setBackground(new Color(0, 0, 0, 216));
        resumeLabel.setFont(new Font("Bold", Font.BOLD, 25));
        resumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resumeLabel.setOpaque(true);
        resumeLabel.setBounds(48, 128, 400, 48);

        this.exitLabel = new JLabel("Exit");
        exitLabel.setForeground(Color.WHITE);
        exitLabel.setBackground(new Color(0, 0, 0, 216));
        exitLabel.setFont(new Font("Bold", Font.BOLD, 25));
        exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        exitLabel.setOpaque(true);
        exitLabel.setBounds(48, 196, 400, 48);


        exitLabel.addMouseListener(new MouseAdapter() {
            private Color prev = exitLabel.getBackground();
            @Override
            public void mouseClicked(MouseEvent e) {
                onExit.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                prev = exitLabel.getBackground();
                exitLabel.setBackground(Color.WHITE);
                exitLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitLabel.setBackground(prev);
                exitLabel.setForeground(Color.WHITE);
            }
        });


        resumeLabel.addMouseListener(new MouseAdapter() {
            private Color prev = resumeLabel.getBackground();
            @Override
            public void mouseClicked(MouseEvent e) {
                onResume.accept(pausedPhase);
                setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                prev = resumeLabel.getBackground();
                resumeLabel.setBackground(Color.WHITE);
                resumeLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resumeLabel.setBackground(prev);
                resumeLabel.setForeground(Color.WHITE);
            }
        });


        add(resumeLabel);
        add(exitLabel);

        setVisible(false);
    }

    public void setPausedPhase(GameState.Phase phase) {
        this.pausedPhase = phase;
    }

    public GameState.Phase getPausedPhase() {
        return pausedPhase;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
