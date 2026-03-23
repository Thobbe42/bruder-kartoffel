package bruderkartoffel;

import bruderkartoffel.game.GameState;
import bruderkartoffel.gui.Mainframe;

public class App {


    public static void main(String[] args) {
        Mainframe frame = new Mainframe();
        GameState gs = frame.getGameState();

    /*    try {
            Thread.sleep(2000);
            for (int i = 0; i < 5; i++) {
                gs.spawnEnemy();
                gs.spawnEnemy();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    */
    }
}
