package main;

import characters.Character;
import inputs.KeybroadInputs;
import inputs.MouseInputs;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    float xDelta = 20,yDelta = 20;
    public Character player_1;
    public GamePanel() {
        setPanelSize();
        int[] keyBroad_player_1 = {KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D};
        player_1 = new Character(0,0, Character.CATCHING_TEAM, keyBroad_player_1);
        MouseInputs mouse= new MouseInputs(this);
        addKeyListener(new KeybroadInputs(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
//        player_1.
        player_1.updateAnimation(g);
        player_1.setPos(xDelta,yDelta);
        xDelta++;
//        g.drawImage(Animations[1][aniIndex].getSubimage(0,0,chr_w,chr_h),150,0,null);
//        g.drawImage(Animations[2][aniIndex].getSubimage(0,0,chr_w,chr_h),300,0,null);
//        g.drawImage(Animations[3][aniIndex].getSubimage(0,0,chr_w,chr_h),450,0,null);
//        g.fillRect((int)xDelta,(int)yDelta,200,50);   // Draw rectangle

    }




    public void setPanelSize(){
        Dimension size = new Dimension(960,640);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    public float getXRect(){
        return xDelta;
    }
    public float getYDelta(){
        return yDelta;
    }
    public void setRectPos(float x , float y){
        this.xDelta = x;
        this.yDelta = y;
    }
}
