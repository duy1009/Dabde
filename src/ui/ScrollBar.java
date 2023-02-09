package ui;

import gamestates.GameState;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.VolumeButtons.*;
import static utilz.Constants.VOLUME_BAR;
import static utilz.Constants.VOLUME_BUTTON;

public class ScrollBar {
    private Button button;
    private int xPos, yPos, index;
    private int xScroll, yScroll;
    private int width, height;
    private int xOffsetCenter;
    private int maxBar = 100;
    private int currentBar = (int)(maxBar*0.5);
    private boolean mouseOver, mousePressed;

    private BufferedImage imgBar;
    private Rectangle bounds;
    public ScrollBar(int xPos, int yPos, int width, int height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        xOffsetCenter = width/2;
        this.xScroll = this.xPos-xOffsetCenter+width*currentBar/maxBar;
        button = new Button(this.xScroll, yPos, VOLUME_WIDTH, VOLUME_HEIGHT, VOLUME_BUTTON, GameState.OPTIONS);
        imgBar = LoadSave.GetSpriteAtlas(VOLUME_BAR);

        this.yScroll = this.yPos+(height-button.getBounds().height)/2;
        initBounds();
    }
    private void initBounds() {
        bounds = new Rectangle(xPos-xOffsetCenter, yPos, width, height);
    }
    public void setSizeButton(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void draw(Graphics g){
        g.drawImage(imgBar, xPos-xOffsetCenter, yPos, width, height,null );
        button.draw(g);
    }
    public void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }
    public void setValue(int value){
        if(value <0)
            value=0;
        else if(value>maxBar)
            value = maxBar;
        this.currentBar = value;
    }
    public void updateBar(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(this.bounds.contains(x, y)){
            this.setValue((int)(x-this.bounds.getX())*maxBar/width);
            button.setXPos(x);
            System.out.println(currentBar);
        }
    }
    public boolean isIn(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(this.bounds.contains(x, y)){
            return true;
        }
        return false;
    }
    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }
    public Rectangle getBounds(){return bounds;}
    public float getValue(){return ((float)currentBar)/maxBar;}
}
