package characters.skill;

import characters.Entity;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.ANI_SPEED_DEFAULT;

public abstract class  Objection extends Entity {
    BufferedImage[][] ani;
    protected int aniTick = 0, aniIndex = 0, aniSpeed = ANI_SPEED_DEFAULT;
    protected int numOfPlayer;
    private float FlipW, FlipX;
    protected int ObjectAction = 0;
    protected boolean activate = true, activateAni = true;
    public Objection(float x, float y,
                     float HB_x, float HB_y, float HB_w, float HB_h,
                     BufferedImage[][] ani,
                     int numOfPlayer, float FlipW){
        super(ani[0][0].getWidth(), ani[0][0].getHeight());
        this.xDrawOffset = HB_x * Game.SCALE;
        this.yDrawOffset = HB_y * Game.SCALE;
        initHitBox(x/Game.SCALE, y/Game.SCALE, HB_w, HB_h);
        this.numOfPlayer = numOfPlayer;
        this.ani = ani;
        this.FlipW = FlipW;

        if (FlipW ==1){
            FlipX = 0;
        }else {
            FlipX = (int)width;
        }
    }
    public void update(){
        updatePos();
        updateAnimationTick();
    }
    public void updateAnimationTick() {
        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= ani[0].length){
                aniIndex = 0;
            }
        }
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        g.drawImage(ani[ObjectAction][aniIndex],
                (int) (hitBox.x - xDrawOffset - xLvlOffset + FlipX),
                (int) (hitBox.y - yDrawOffset - yLvlOffset),
                (int)(width*FlipW),(int)height, null);
        drawHitBox(g, xLvlOffset, yLvlOffset);
    }

    public boolean isActivate() {
        return activate;
    }
    public boolean isActivateAni(){
        return activateAni;
    }

    protected void updatePos(){
        System.out.println("This is Objection class!!");
    }
}
