package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.BOX_BUTTON;
import static utilz.Constants.TICK_BUTTON;

public class LoadBox {
    private BufferedImage imgBox, imgReady;
    private int xPos, yPos, size;
    private int xTex, yTex;
    private int xOffsetCenter;
    private boolean ready = true;
    private long preTime = 0;
    private long currentTime;
    private long RECOVER_TIME = 5000;
    private long recoverTime = 0;

    public LoadBox(int x, int y, int size){
        this.xPos = x;
        this.yPos = y;
        this.size = size;
        this.xOffsetCenter = size/2;
        this.xTex =x -5 ;
        this.yTex = y+xOffsetCenter+2;
        loadImg();
    }
    private void loadImg(){
        imgBox = LoadSave.GetSpriteAtlas(BOX_BUTTON);
        imgReady = LoadSave.GetSpriteAtlas(TICK_BUTTON);
    }
    public void setPos(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.xTex = x-5;
        this.yTex = y+xOffsetCenter+2;
    }
    public void draw(Graphics g){
        g.drawImage(imgBox, xPos-xOffsetCenter, yPos, size, size,null );
        if (ready){
            g.drawImage(imgReady, xPos-xOffsetCenter, yPos, size, size,null );
        }else {
            g.drawString(Long.toString(recoverTime/1000), xTex, yTex);
        }
    }
    public void update(){
        currentTime = System.currentTimeMillis();
        recoverTime =  preTime + RECOVER_TIME - currentTime ;
//        System.out.println(recoverTime/1000);
        if(recoverTime<=0){
            ready = true;
        }else {
            ready = false;
        }
    }
    public void setRecoverTime(long val){RECOVER_TIME = val;}
    public void reload(){
        preTime = System.currentTimeMillis();
    }

}
