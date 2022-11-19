package inputs;


import characters.Character;
import main.GamePanel;
import utilz.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeybroadInputs implements KeyListener{
    private GamePanel gamePanel;

    public KeybroadInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("Key Type");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Character Player[] = gamePanel.getGame().getPlayer();
        for (int i =0; i<Player.length; i++){
            if (e.getKeyCode()==Player[i].getUpCtrl()){
                Player[i].setJump(true);
            }else if (e.getKeyCode()==Player[i].getDownCtrl()){
                Player[i].setDown(true);
            }else if (e.getKeyCode()==Player[i].getLeftCtrl()){
                Player[i].setLeft(true);
            }else if (e.getKeyCode()==Player[i].getRightCtrl()){
                Player[i].setRight(true);
            }

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        Character Player[] = gamePanel.getGame().getPlayer();
        for (int i =0; i<Player.length; i++){
            if (e.getKeyCode()==Player[i].getUpCtrl()){
                Player[i].setJump(false);
            }else if (e.getKeyCode()==Player[i].getDownCtrl()){
                Player[i].setDown(false);
            }else if (e.getKeyCode()==Player[i].getLeftCtrl()){
                Player[i].setLeft(false);
            }else if (e.getKeyCode()==Player[i].getRightCtrl()){
                Player[i].setRight(false);
            }
        }
    }
}
