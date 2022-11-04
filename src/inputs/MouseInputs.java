package inputs;

import characters.Character;
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse click");

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        NV1.setPos(x);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {
        // We wont use this

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // We wont use this
    }
}
