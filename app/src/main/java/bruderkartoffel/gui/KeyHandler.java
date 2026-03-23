package bruderkartoffel.gui;

import bruderkartoffel.game.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private final GameState gameState;

    public KeyHandler(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> gameState.up = true;
            case KeyEvent.VK_A -> gameState.left = true;
            case KeyEvent.VK_S -> gameState.down = true;
            case KeyEvent.VK_D -> gameState.right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> gameState.up = false;
            case KeyEvent.VK_A -> gameState.left = false;
            case KeyEvent.VK_S -> gameState.down = false;
            case KeyEvent.VK_D -> gameState.right = false;
        }
    }
}
