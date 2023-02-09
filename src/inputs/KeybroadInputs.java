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

    }


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

}
