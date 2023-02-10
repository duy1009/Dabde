package inputs;

import characters.Character;
import gamestates.GameState;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener{
    GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void mouseDragged(MouseEvent e) {


    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println("Mouse move");
//        gamePanel.setRectPos(e.getX(),e.getY());
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case PICK:
                gamePanel.getGame().getPick().mouseMoved(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOption().mouseMoved(e);
                break;
            case ENDGAME:
                gamePanel.getGame().getEndGame().mouseMoved(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            case PICK:
                gamePanel.getGame().getPick().mouseClicked(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOption().mouseClicked(e);
                break;
            case ENDGAME:
                gamePanel.getGame().getEndGame().mouseClicked(e);
                break;
            default:
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case PICK:
                gamePanel.getGame().getPick().mousePressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOption().mousePressed(e);
                break;
            case ENDGAME:
                gamePanel.getGame().getEndGame().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case PICK:
                gamePanel.getGame().getPick().mouseReleased(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getOption().mouseReleased(e);
                break;
            case ENDGAME:
                gamePanel.getGame().getEndGame().mouseReleased(e);
                break;
            default:
                break;
        }
    }
    public void mouseEntered(MouseEvent e) {
        // We wont use this

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // We wont use this
    }
}
