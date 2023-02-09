package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.BOX_BUTTON;
import static utilz.Constants.TICK_BUTTON;

public class TickBox {
    private BufferedImage imgBox, imgTick;
    private int xPos, yPos, size;
    private int xOffsetCenter;
    private Rectangle bounds;
    private boolean isTick = false;
    public TickBox(int x, int y, int size){
        this.xPos = x;
        this.yPos = y;
        this.size = size;
        this.xOffsetCenter = size/2;
        loadImg();
        initBounds();
    }
    private void loadImg(){
        imgBox = LoadSave.GetSpriteAtlas(BOX_BUTTON);
        imgTick = LoadSave.GetSpriteAtlas(TICK_BUTTON);
    }
    private void initBounds() {
        bounds = new Rectangle(xPos-xOffsetCenter, yPos, size, size);
    }
    public void draw(Graphics g){
        g.drawImage(imgBox, xPos-xOffsetCenter, yPos, size, size,null );
        if (isTick){
            g.drawImage(imgTick, xPos-xOffsetCenter, yPos, size, size,null );
        }
    }
    public void update(){

    }
    public Rectangle getBound(){return bounds;}
    public boolean isTick(){
        return isTick;
    }
    public void setTick(boolean value){isTick = value;}
    public void reverse(MouseEvent e){
        isTick = !isTick;
    }
}
