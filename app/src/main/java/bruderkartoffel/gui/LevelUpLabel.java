package bruderkartoffel.gui;

import bruderkartoffel.game.progression.LevelUp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LevelUpLabel extends JLabel {

    private LevelUp levelUp;

    // width: (screen width / 2) / 4  -  10px
    // height /screen height / 2)
    public LevelUpLabel(Dimension screenSize, int index) {

        int width = (screenSize.width / 2) / 4 - 10;
        int height = screenSize.height / 3;
        int y = screenSize.height / 3;
        int x = screenSize.width / 4 + (index * (width + 5));

        setBounds(x, y, width, height);

        setForeground(Color.BLACK);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Color.DARK_GRAY);
        setOpaque(true);
    }

    public void setLevelUp(LevelUp levelUp) {
        this.levelUp = levelUp;
        this.setText(levelUp.getStat().name());
    }
}
