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
    private int xPos, yPos;
    private int xScroll;
    private int width, height;
    private int xOffsetCenter;
    private int maxBar = 100;
    private int currentBar = (int)(maxBar*0.8);
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

        initBounds();
    }
    private void initBounds() {
        bounds = new Rectangle(xPos-xOffsetCenter, yPos, width, height);
    }

    public void draw(Graphics g){
        g.drawImage(imgBar, xPos-xOffsetCenter, yPos, width, height,null );
        button.draw(g);
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
        }
    }

    public float getValue(){return ((float)currentBar)/maxBar;}
}
