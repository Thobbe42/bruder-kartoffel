package bruderkartoffel.gui;

import bruderkartoffel.game.progression.LevelUp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LevelUpLabel extends JLabel {

    private LevelUp levelUp;

    public LevelUpLabel(Dimension screenSize, int index) {

        int width = (screenSize.width / 2) / 4 - 10;
        int height = screenSize.height / 3;
        int y = screenSize.height / 3;
        int x = screenSize.width / 4 + (index * (width + 5));

        setBounds(x, y, width, height);

        setForeground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 2));
        setFont(new Font("Bold", Font.BOLD, 25));
        setHorizontalAlignment(SwingConstants.CENTER);
        setBackground(Color.GRAY);
        setOpaque(true);


        addMouseListener(new MouseListener() {
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
                setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.GRAY);
            }
        });
    }

    public void setLevelUp(LevelUp levelUp) {
        this.levelUp = levelUp;
        this.setText(levelUp.getStat().name());
    }
}
