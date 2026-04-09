package bruderkartoffel.gui;

import bruderkartoffel.game.progression.LevelUp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LevelUpPanel extends JLabel {

    private LevelUp levelUp;

    private JLabel name;
    private ImageIcon imageIcon;
    private JLabel selector;
    private JLabel stats;

    public LevelUpPanel(Dimension screenSize, int index) {

        // init bounds
        int width = screenSize.height / 3;
        int y = screenSize.height / 3;
        int x = 24 + (index * (width + 8));

        setBounds(x, y, width, width);
        setBackground(new Color(0,0,0, 216));
        setOpaque(true);


        // initialize components
        this.name = new JLabel();
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Bold", Font.BOLD, 20));
        name.setHorizontalAlignment(CENTER);
        name.setBounds(width/4, width/6, width/2, 20);


        this.selector = new JLabel("Choose");
        selector.setBackground(new Color(80, 80, 80, 216));
        selector.setForeground(Color.WHITE);
        selector.setFont(new Font("Bold", Font.BOLD, 25));
        selector.setHorizontalAlignment(CENTER);
        selector.setOpaque(true);
        selector.setBounds(8,4*width/5, width - 16,width/6);


        this.stats = new JLabel();
        stats.setBounds(8, 2 * width/6, width - 16, 16);
        stats.setFont(new Font("Plain", Font.PLAIN, 16));
        stats.setForeground(Color.WHITE);
        stats.setHorizontalAlignment(LEFT);


        add(name);
        add(selector);
        add(stats);

        selector.addMouseListener(new MouseListener() {

            private Color prev = selector.getBackground();
            @Override
            public void mouseClicked(MouseEvent e) {
                levelUp.apply();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                prev = selector.getBackground();
                selector.setBackground(Color.WHITE);
                selector.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                selector.setBackground(prev);
                selector.setForeground(Color.WHITE);
            }
        });
    }

    public void setLevelUp(LevelUp levelUp) {
        this.levelUp = levelUp;
        this.name.setText(levelUp.getStat().name());

        String statVal = levelUp.getValue() + " " + levelUp.getStat().name();
        this.stats.setText(statVal);
    }
}
