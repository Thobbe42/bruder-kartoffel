package bruderkartoffel.gui;

import bruderkartoffel.game.entity.Stats;
import bruderkartoffel.game.progression.LevelUp;
import bruderkartoffel.game.progression.ProgressionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelUpPanel extends JPanel {

    private List<UpgradePanel> upgradePanels;
    private StatsPanel statsPanel;

    private boolean inited = false;

    private ProgressionHandler progressionHandler;


    public LevelUpPanel(ProgressionHandler progressionHandler) {
        this.progressionHandler = progressionHandler;
        progressionHandler.registerLevelConsumer(this::setLevels);
    }


    public void init(Stats stats) {
        Dimension size = getSize();
        this.upgradePanels = new ArrayList<>(4);
        this.statsPanel = new StatsPanel(stats, size);

        setLayout(null);
        setOpaque(false);

        for (int i = 0; i < 4; i++) {
            upgradePanels.add(new UpgradePanel(size, i));
            add(upgradePanels.get(i));
            upgradePanels.get(i).setVisible(true);
        }

        add(statsPanel);
        statsPanel.refresh();
        statsPanel.setVisible(true);

        inited = true;
    }

    private void setLevels(List<LevelUp> levels) {
        for (int i = 0; i < levels.size(); i++) {
            upgradePanels.get(i).setLevelUp(levels.get(i));
        }
        statsPanel.refresh();
    }

    public boolean isInited() {
        return inited;
    }
}
