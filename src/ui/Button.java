package ui;

import gamestates.GameState;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Button {
    private int xPos, yPos, index;
    private int width, height;
    private int xOffsetCenter;
    private boolean mouseOver, mousePressed;
    private GameState state;
    private BufferedImage[] imgs;
    private Rectangle bounds;
    public Button(int xPos, int yPos, int width, int height, String path_img, GameState state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        xOffsetCenter = width/2;
        this.state = state;
        imgs = LoadSave.loadArrayAni_1D(path_img, 3);
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
        g.drawImage(imgs[index], xPos-xOffsetCenter, yPos, width, height,null );
    }
    public void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
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
    public void applyGameState(){
        GameState.state = state;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }
    public Rectangle getBounds(){return bounds;}
    public void setXPos(int xPos){
        this.xPos=xPos;
        this.bounds.x = xPos - xOffsetCenter;
    }
}
