package bruderkartoffel.gui;

import bruderkartoffel.game.entity.Stats;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    private Stats stats;

    private JLabel top;
    private JLabel level;
    private JLabel statName;
    private JLabel statValue;

    public StatsPanel(Stats stats, Dimension screenSize) {
        this.stats = stats;

        int width = screenSize.width / 4  - 10;
        int height = (int)(screenSize.height * 3/4);
        int y = 170;
        int x = 24 + (screenSize.height/3 + 8) * 4 + 16;

        setBounds(x,y, width, height);

        setBackground(new Color(0,0,0, 216));
        setOpaque(true);
        setLayout(null);

        // init components
        this.top = new JLabel("Stats");
        top.setForeground(Color.WHITE);
        top.setFont(new Font("Bold", Font.BOLD, 35));
        top.setHorizontalAlignment(SwingConstants.CENTER);
        top.setBounds(width/3, 8, width/3, 36);


        this.statName = new JLabel();
        statName.setForeground(Color.WHITE);
        statName.setFont(new Font("Bold", Font.BOLD, 20));
        statName.setHorizontalAlignment(SwingConstants.LEFT);
        statName.setVerticalAlignment(SwingConstants.TOP);
        statName.setBounds(16, 64, (width - 32)/2, height - 80);

        this.statValue = new JLabel();
        statValue.setForeground(Color.WHITE);
        statValue.setFont(new Font("Bold", Font.BOLD, 20));
        statValue.setHorizontalAlignment(SwingConstants.RIGHT);
        statValue.setVerticalAlignment(SwingConstants.TOP);
        statValue .setBounds((width-32)/2 + 16,64, (width - 32)/2, height - 80);

        add(top);
        add(statName);
        add(statValue);
    }

    public void refresh() {
        String names = "<html>" +
                "Max HP " +
                "<br>Damage " +
                "<br>Attack Speed " +
                "<br>Armor " +
                "<br>Dodge " +
                "<br>Speed " +
                "</html>";
        String values = "<html>" +
                stats.maxHP + "<br>" +
                stats.damage + "<br>" +
                stats.attackSpeed + "<br>" +
                stats.armor + "<br>" +
                stats.dodge + "<br>" +
                stats.speed + "<br>" +
                "</html>";
        statName.setText(names);
        statValue.setText(values);
    }
}
