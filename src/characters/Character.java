package characters;


import main.Game;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.HelpMethods.*;

public class Character extends Entity{
    private boolean alive;
    private int team;
    public int moving, playerAction = IDLE, playerDir=-1;
    private float playerSpeed = 1.5f;
    private int up_ctrl, down_ctrl, left_ctrl, right_ctrl; // controller key
    private boolean up = false, down =false, left = false, right = false, jump =false;

    // Animation
    private BufferedImage img;
    private BufferedImage[][] Animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 15;   // frame update an animation
    public int ani_row_max, ani_col_max;
    float chr_w, chr_h;
    private float xDrawOffet;
    private float yDrawOffet;
    // Map
    private static int mapData[][];
    // Jump and Fall
    private float airSpeed;
    private float gravity = 0.025f*Game.SCALE;
    private float jumpSpeed = -2.25f*Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Character(float x, float y,int width, int height,
                     float HB_x, float HB_y,float HB_width, float HB_height,
                     int team,
                     int[] ctrl, // {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D}
                     String animation_path,
                     int ani_row_max, int ani_col_max) // pixel
    {
        super(x,y,width, height);
        setControl(ctrl);
        this.x = x; this.y = y;
        this.ani_col_max = ani_col_max; this.ani_row_max = ani_row_max;
        this.team = team;
        this.alive = true;

        loadAnimation(animation_path);
        this.xDrawOffet = HB_x* width * Game.SCALE / chr_w;;
        this.yDrawOffet = HB_y* height * Game.SCALE / chr_h;

        initHitBox(x, y, HB_width* width *Game.SCALE / chr_w, HB_height* height* Game.SCALE / chr_h);

        System.out.println(hitBox.width + " " + hitBox.height);

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

    private void loadAnimation(String path) {
        img = LoadSave.GetSpriteAtlas(path);
        this.chr_h = img.getHeight()/ani_row_max;
        this.chr_w = img.getWidth()/ani_col_max;
        Animations = new BufferedImage[ani_row_max][ani_col_max];
        for(int i=0; i< Animations.length;i++) // load animation first
            for(int j=0;j < Animations[i].length;j++){
                Animations[i][j] = img.getSubimage((int)(j * chr_w), (int)(i * chr_h), (int)chr_w, (int)chr_h);
            }

    }
    public static void loadMapData(int[][] mapDt){
        mapData = mapDt;
    }

    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimations();

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
//        g.drawImage(Animations[playerAction][aniIndex].getSubimage(0,0,chr_w,chr_h),(int)x,(int)y,null);
        g.drawImage(Animations[playerAction][aniIndex],
                (int) (hitBox.x - xDrawOffet ),
                (int) (hitBox.y - yDrawOffet ),
                (int)width,(int)height, null);

        drawHitBox(g);
    }
    public void updatePos(){
        moving = 0;
        float xSpeed = 0;
        if (left){
            xSpeed-= playerSpeed;
            moving = 1;
        }
        if (right){
            xSpeed+= playerSpeed;
            moving = 1;
        }

        if (jump){
            jump();
        }

        if (!inAir){
            if (!IsEntityOnFloor(hitBox, mapData))
                inAir = true;
        }
        if (inAir){
            if(!IsSolidBox(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, mapData)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed);
            }else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed>0){
                    inAir = false;
                    airSpeed = 0;
                }else
                    airSpeed = fallSpeedAfterCollision;
                updateXpos(xSpeed);

            }
        }else {
            updateXpos(xSpeed);
        }

    }

    private void jump() {
        if(inAir)   return;
        inAir = true;
        airSpeed = jumpSpeed;
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
    public void setJump(boolean val){this.jump = val;}
    public void up(){
        if(!IsSolidBox(hitBox.x,hitBox.y-playerSpeed,hitBox.width,hitBox.height, mapData))
            hitBox.y-=playerSpeed;
    }
    private void updateXpos(float xSpeed){

        if(!IsSolidBox(hitBox.x+xSpeed,hitBox.y,hitBox.width,hitBox.height, mapData)) {
            hitBox.x += xSpeed;
        }
        else{
            hitBox.x = GetEntityNextPosToWall(hitBox,xSpeed);
        }
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
