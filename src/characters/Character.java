package characters;

import utilz.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Character implements Constants {
    private float x, y; // pos
    private boolean alive;
    private int team;
    public int moving, playerAction = IDLE, playerDir=-1;
    private int up_ctrl, down_ctrl, left_ctrl, right_ctrl; // controller key
    private boolean up = false, down =false, left = false, right = false;
    private BufferedImage img;
    private BufferedImage[][] Animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 15;   // frame update an animation
    public int ani_row_max, ani_col_max, chr_w, chr_h;
    private float playerSpeed = 2f;

    public Character(float x, float y,
                     int team,
                     int[] ctrl, // {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D}
                     String animation_path,
                     int ani_row_max, int ani_col_max) // pixel
    {
        setControl(ctrl);
        this.x = x; this.y = y;
        this.ani_col_max = ani_col_max; this.ani_row_max = ani_row_max;
        this.team = team;
        this.alive = true;
        importImg(animation_path);
        loadAnimation();
    }
    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void killed(){
        alive = false;
    }
    public boolean isAlive(){
        return alive;
    }

    private void loadAnimation() {
        Animations = new BufferedImage[ani_row_max][ani_col_max];
        for(int i=0; i< Animations.length;i++) // load animation first
            for(int j=0;j < Animations[i].length;j++){
                Animations[i][j] = img.getSubimage(j * chr_w, i * chr_h, chr_w, chr_h);
            }
    }

    public void update(){
        updateAnimationTick();
        setAnimations();
        updatePos();
    }
    public void updateAnimationTick() {
        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= Animations[0].length)
                aniIndex = 0;
        }
    }

    public void setAnimations(){
        int startAni = playerAction;
        if(moving != 0 )
            playerAction = RUNNING;
        else
            playerAction = IDLE;


        if (startAni != playerAction){
            restAniTick();
        }
    }
    public void restAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }
    public void render(Graphics g){
        g.drawImage(Animations[playerAction][aniIndex].getSubimage(0,0,chr_w,chr_h),(int)x,(int)y,null);
    }
    public void updatePos(){
        moving = 0;
//        if (moving == 1){
            if (up && !down){
                moving = 1;
                up();
            }else if (down && !up){
                moving = 1;
                down();
            }

            if (left && !right){
                moving = 1;
                left();
            }else if (right && !left){
                moving = 1;
                right();
            }
//        }
    }
    public void importImg(String path){
        InputStream is = getClass().getResourceAsStream(path);
        try{
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }
        this.chr_h = (int)(img.getHeight()/ani_row_max);
        this.chr_w = (int)(img.getWidth()/ani_col_max);

    }
    public void setControl(int[] ctrl){
        this.up_ctrl = ctrl[0];
        this.down_ctrl = ctrl[1];
        this.left_ctrl = ctrl[2];
        this.right_ctrl = ctrl[3];
    }
    public int getUpCtrl(){return up_ctrl;}
    public int getDownCtrl(){return down_ctrl;}
    public int getLeftCtrl(){return left_ctrl;}
    public int getRightCtrl(){return right_ctrl;}
    public void setUp(boolean val){this.up = val;}
    public void setDown(boolean val){this.down = val;}
    public void setLeft(boolean val){this.left = val;}
    public void setRight(boolean val){this.right = val;}
    public void up(){
        y-=playerSpeed;
    }
    public void down(){
        y+=playerSpeed;
    }
    public void left(){
        x-=playerSpeed;
    }
    public void right(){
        x+=playerSpeed;
    }
    public void resetDir(){
        up = false;
        down = false;
        left = false;
        right = false;
    }
    public void setDirection(int dir){
        this.playerDir = dir;
        moving = 1;
    }
    public Character getCharacter(){return this;}
    public void setMoving(int moving){this.moving = moving;}
}
