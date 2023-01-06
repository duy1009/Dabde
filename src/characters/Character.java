package characters;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.HelpMethods.*;


public abstract class Character extends Entity{
    // Animations action
    public  int IDLE = 0;
    public int MOVING = 1;

    public int JUMP = 3;
    public int FALLING = 3;
    public int DOWN = 4;

    protected int moving, playerAction = IDLE, playerDir=-1;

    protected float playerSpeed = PLAYER_SPEED_DEFAULT*Game.SCALE;
    private int up_ctrl, down_ctrl, left_ctrl, right_ctrl; // controller key
    protected boolean skill_1 = false, skill_2 = false, skill_3 = false, skill_4 = false;
    protected int skill_1_ctrl, skill_2_ctrl, skill_3_ctrl, skill_4_ctrl;
    private boolean down = false, left = false, right = false, jump =false;

    // Animation
    private BufferedImage img;
    private BufferedImage[][] Animations;
    protected int aniTick = 0, aniIndex = 0, aniSpeed = ANI_SPEED_DEFAULT;   // frame update an animation
    private int ani_row_max, ani_col_max;
    private int FlipX=0;
    protected int FlipW=1;
    float chr_w, chr_h;

    // Map
    protected static int mapData[][];
    // Jump and Fall
    private float airSpeed;
    private float gravity = 0.04f* Game.SCALE;
    private float jumpSpeed = -3.0f*Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    // Down
    private float heightHitBoxDown, heightHitBoxNotDown;
    private float downSpeed = 0.5f*playerSpeed;
    private boolean isDown = false;
    private boolean inAir = false;
    protected boolean lockMoving = false;
    // Health Bar
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int)(192*Game.SCALE);
    private int statusBarHeight = (int)(58*Game.SCALE);
    private int statusBarX = (int)(10*Game.SCALE);
    private int statusBarY = (int)(10*Game.SCALE);


    private int healthBarWidth = (int)(152*Game.SCALE);
    private int healthBarHeight = (int)(4*Game.SCALE);
    private int healthBarXStart = (int)(34*Game.SCALE);
    private int healthBarYStart = (int)(14*Game.SCALE);
    // Status bar flip
    private boolean statusBarFlip = false;
    private int healthBarXFlip = Game.GAME_WIDTH -(healthBarXStart + statusBarX);
    private int statusBarXFlip = Game.GAME_WIDTH - statusBarX;
    private int maxHealth = 1000;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;


    public Character(float x, float y,int width, int height,
                          float HB_x, float HB_y,float HB_width, float HB_height,
                          int[] ctrl, // {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D}
                          String animation_path,
                          int ani_row_max, int ani_col_max) // pixel
    {

        super(width, height);

        setControl(ctrl);
        this.ani_col_max = ani_col_max; this.ani_row_max = ani_row_max;


        loadAnimation(animation_path, true);
        this.xDrawOffset = HB_x* width * Game.SCALE / chr_w;;
        this.yDrawOffset = HB_y* height * Game.SCALE / chr_h;

        initHitBox(x, y, HB_width* width/ chr_w, HB_height* height/ chr_h);

        heightHitBoxDown = 0.5f*hitBox.height;
        heightHitBoxNotDown = hitBox.height;

    }


    private void loadAnimation(String path, boolean addFlip) {
        img = LoadSave.GetSpriteAtlas(path);
        this.chr_h = img.getHeight()/ani_row_max;
        this.chr_w = img.getWidth()/ani_col_max;
        Animations = new BufferedImage[ani_row_max][ani_col_max];

        for(int i=0; i< Animations.length;i++) // load animation first
            for(int j=0;j < Animations[i].length;j++){
                Animations[i][j] = img.getSubimage((int)(j * chr_w), (int)(i * chr_h), (int)chr_w, (int)chr_h);
            }
        statusBarImg = LoadSave.GetSpriteAtlas(STATUS_BAR);
    }
    public static void loadMapData(int[][] mapDt){
        mapData = mapDt;
    }

    public void update(){
        if(!lockMoving)
            updatePos();
        updateAnimationTick();
        setAnimations();
        updateSkill();
        updateHealthBar();
    }
    public void updateAnimationTick() {
        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= Animations[0].length){
                aniIndex = 0;
            }
        }
    }

    public void setAnimations(){
        int startAni = playerAction;
        switch (moving){
            case 0:
                playerAction = IDLE;
                break;
            case 1:
                playerAction = MOVING;
                break;
            case 2:
                playerAction = DOWN;
                break;
//            case 3:
//                playerAction = SKILL1;
//            case 4:
//                playerAction = SKILL2;
//            case 5:
//                playerAction = SKILL3;
//            case 6:
//                playerAction = SKILL4;
        }
        if( inAir){
            if(airSpeed<0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }
        setSkillAni();
        if (startAni != playerAction){
            restAniTick();
        }
    }
    public void setAniAction(int idle, int moving, int up, int down, int fall){
        IDLE = idle; MOVING=moving ; JUMP = up; DOWN = down; FALLING = fall;
    }
    public void restAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }
    private void drawUI(Graphics g){
        if (statusBarFlip) {
            g.drawImage(statusBarImg, statusBarXFlip, statusBarY, statusBarWidth*-1, statusBarHeight, null);
            g.setColor(Color.red);
            g.fillRect(healthBarXFlip - healthWidth, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
        }else{
            g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
            g.setColor(Color.red);
            g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
        }

    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        g.drawImage(Animations[playerAction][aniIndex],
                (int) (hitBox.x - xDrawOffset - xLvlOffset + FlipX),
                (int) (hitBox.y - yDrawOffset - yLvlOffset),
                (int)width*FlipW,(int)height, null);

        drawHitBox(g, xLvlOffset, yLvlOffset);
        renderSkill(g, xLvlOffset, yLvlOffset);
        drawUI(g);
    }



    private void updateHealthBar(){
        healthWidth = (int)(currentHealth*healthBarWidth/(float)maxHealth);
    }
    public void updatePos(){
        moving = 0;
        float xSpeed = 0;
        if (left){
            xSpeed-= playerSpeed;
            moving = 1;
            FlipX = (int)width;
            FlipW = -1;
        }
        if (right){
            xSpeed+= playerSpeed;
            moving = 1;
            FlipX = 0;
            FlipW = 1;
        }

        if (down){
            down();
            moving = 2;
        } else {
            if (hitBox.height != heightHitBoxNotDown) {
                if (!IsSolidBox(hitBox.x, hitBox.y - heightHitBoxNotDown + heightHitBoxDown, hitBox.width, heightHitBoxNotDown, mapData))
                    standUp();
                else{
                    down();
                    moving =2;
                }
            }
        }
        if (jump){
            jump();
        }

        if (!inAir){
            if (!IsEntityOnFloor(hitBox, mapData)){
                inAir = true;
            }

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
    private void standUp(){
        this.playerSpeed = PLAYER_SPEED_DEFAULT*Game.SCALE;
        if(this.hitBox.height != heightHitBoxNotDown){
            this.hitBox.y -= heightHitBoxNotDown - heightHitBoxDown;
        }
        this.hitBox.height = heightHitBoxNotDown;

    }


    public void setControl(int[] ctrl){
        this.up_ctrl = ctrl[0];
        this.down_ctrl = ctrl[1];
        this.left_ctrl = ctrl[2];
        this.right_ctrl = ctrl[3];

        this.skill_1_ctrl = ctrl[4];
        this.skill_2_ctrl = ctrl[5];
        this.skill_3_ctrl = ctrl[6];
        this.skill_4_ctrl = ctrl[7];
    }
    public int getUpCtrl(){return up_ctrl;}
    public int getDownCtrl(){return down_ctrl;}
    public int getLeftCtrl(){return left_ctrl;}
    public int getRightCtrl(){return right_ctrl;}
    public int getSkill_1_ctrl(){return skill_1_ctrl;}
    public int getSkill_2_ctrl(){return skill_2_ctrl;}
    public int getSkill_3_ctrl(){return skill_3_ctrl;}
    public int getSkill_4_ctrl(){return skill_4_ctrl;}

    public void setDown(boolean val){
        this.down = val;
    }
    public void setStatusBarFlip(boolean val){statusBarFlip = val;};
    private void down(){
        this.playerSpeed = downSpeed;
        if (this.hitBox.height != heightHitBoxDown){
            this.hitBox.y+= heightHitBoxNotDown - heightHitBoxDown;
        }
        this.hitBox.height = heightHitBoxDown;

    }
    public void addHP(int val){
        currentHealth += val;
        if (currentHealth <0)
            currentHealth=0;
        else if(currentHealth > maxHealth)
            currentHealth = maxHealth;
    }
    public void setLeft(boolean val){this.left = val;}
    public void setRight(boolean val){this.right = val;}
    public void setJump(boolean val){this.jump = val;}

    private void updateXpos(float xSpeed){
        if(!IsSolidBox(hitBox.x+xSpeed,hitBox.y,hitBox.width,hitBox.height, mapData)) {
            hitBox.x += xSpeed;
        }
        else{
            hitBox.x = GetEntityNextPosToWall(hitBox,xSpeed);
        }
    }
    public void resetDir(){
//        down = false;
        left = false;
        right = false;
        jump = false;
    }
    public void setDirection(int dir){
        this.playerDir = dir;
        moving = 1;
    }
    public Character getCharacter(){return this;}
    public void setMoving(int moving){this.moving = moving;}

    /*Override*/
    public void setSkill_1(boolean val){System.out.println("Error: this is setSkill function of Character");}
    public void setSkill_2(boolean val){System.out.println("Error: this is setSkill function of Character");}
    public void setSkill_3(boolean val){System.out.println("Error: this is setSkill function of Character");}
    public void setSkill_4(boolean val){System.out.println("Error: this is setSkill function of Character");}
    public void setSkillAni(){};
    protected void renderSkill(Graphics g, int xLvlOffset, int yLvlOffset) {
    }
    protected void updateSkill(){}
}

