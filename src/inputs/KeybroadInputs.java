package inputs;


import characters.Character;
import client.Packets;
import client.Receive;
import client.Send;
import gamestates.GameState;
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
//        mainChar = gamePanel.getGame().getMainCharacter();
//        System.out.println(keyData);
    }
//    public KeybroadInputs(GamePanel gamePanel, Send send) {
//        this.gamePanel = gamePanel;
//        mainChar = gamePanel.getGame().getMainCharacter();
//        Player = gamePanel.getGame().getPlayer()[mainChar];
//        keyData = send.getKeyData();
//        System.out.println(keyData);
//    }

    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("Key Type");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case PICK:
                gamePanel.getGame().getPick().keyPressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOption().keyPressed(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            case PICK:
                gamePanel.getGame().getPick().keyReleased(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOption().keyReleased(e);
                break;
            default:
                break;
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
