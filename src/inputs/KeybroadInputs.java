package inputs;

import characters.Character;
import main.Game;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeybroadInputs implements KeyListener {
    private GamePanel gamePanel;


    public KeybroadInputs(GamePanel gamePanel, Character[] player) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
               System.out.println("W");
               gamePanel.setRectPos(gamePanel.getXRect(), gamePanel.getYDelta()-20);
               break;
            case KeyEvent.VK_S:
                System.out.println("S");
            case KeyEvent.VK_A:
                System.out.println("A");
                break;
            case KeyEvent.VK_D:
                System.out.println("D");
                break;

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
