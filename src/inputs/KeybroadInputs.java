package inputs;


import characters.Character;
import client.Packets;
import client.Receive;
import client.Send;
import main.GamePanel;
import utilz.Constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.*;

public class KeybroadInputs implements KeyListener{
    private GamePanel gamePanel;
    private int mainChar;
    private Character Player;
    private Packets keyData;
    public KeybroadInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mainChar = gamePanel.getGame().getMainCharacter();
//        System.out.println(keyData);
    }
    public KeybroadInputs(GamePanel gamePanel, Send send) {
        this.gamePanel = gamePanel;
        mainChar = gamePanel.getGame().getMainCharacter();
        Player = gamePanel.getGame().getPlayer()[mainChar];
        keyData = send.getKeyData();
        System.out.println(keyData);
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
//    @Override
//    public void keyPressed(KeyEvent e) {
//        synchronized (e){
//            if (e.getKeyCode()==Player.getUpCtrl()){
//                keyData.setData(IP_UP, IP_PRESSED);
//                Player.setJump(true);
//            }else if (e.getKeyCode()==Player.getDownCtrl()){
//                keyData.setData(IP_DOWN, IP_PRESSED);
//                Player.setDown(true);
//            }else if (e.getKeyCode()==Player.getLeftCtrl()){
//                keyData.setData(IP_LEFT, IP_PRESSED);
//                Player.setLeft(true);
//            }else if (e.getKeyCode()==Player.getRightCtrl()){
//                keyData.setData(IP_RIGHT, IP_PRESSED);
//                Player.setRight(true);
//            }
//        }
//    }
//    @Override
//    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode()==Player.getUpCtrl()){
//            keyData.setData(IP_UP, IP_RELEASED);
//            Player.setJump(false);
//        }else if (e.getKeyCode()==Player.getDownCtrl()){
//            keyData.setData(IP_DOWN, IP_RELEASED);
//            Player.setDown(false);
//        }else if (e.getKeyCode()==Player.getLeftCtrl()){
//            keyData.setData(IP_LEFT, IP_RELEASED);
//            Player.setLeft(false);
//        }else if (e.getKeyCode()==Player.getRightCtrl()){
//            keyData.setData(IP_RIGHT, IP_RELEASED);
//            Player.setRight(false);
//        }
//    }

}
