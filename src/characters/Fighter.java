package characters;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.HelpMethods.*;

public class Fighter extends Character {
    private static int NORMAL_ATTACK = 4;
    private static int SP_ATTACK = 8;
    private int SKILL_1_RECOVERY_TIME = 700;
    private int SKILL_2_RECOVERY_TIME = 4000;
    private int SKILL_3_RECOVERY_TIME = 10000;
    private int SKILL_4_RECOVERY_TIME = 5000;
    private int SKILL_1_WORK_TIME = 600;
    private int SKILL_2_WORK_TIME = 3000;
    private int SKILL_3_WORK_TIME = 500;
    private int SKILL_4_WORK_TIME = 1500;
    private int SKILL_4_START_ACTIVATE_BOX_TIME = 500;
    private static final int DEFAULT_WIDTH_KUNAI = (int)(70*Game.SCALE);
    private static final int DEFAULT_HEIGHT_KUNAI = (int)(70*Game.SCALE);
    private long preTimeSkill1 = 0, preTimeSkill2 = 0, preTimeSkill3 = 0, preTimeSkill4 = 0;
    private boolean firstUpdateSkill1 = false, firstUpdateSkill2 = false, firstUpdateSkill3 = false,firstUpdateSkill4 = false;
    private boolean activateAttackBox = false, activateBox2 = false, activateBox3 = false, activateBox4 = false;
    private boolean firstEndSkill4;
    private int flipWSkill2 = 1, flipXSkill2 = 0;
    private float skill2Speed = 2*Game.SCALE;
    private int numOfPlayer = 0;
    private Character[] player;
    private Rectangle2D.Float attackBox = new Rectangle2D.Float();
    private Rectangle2D.Float skill2Box = new Rectangle2D.Float();
    private Rectangle2D.Float skill4Box = new Rectangle2D.Float();
    private int skill2OffsetX = (int)(17*Game.SCALE), skill2OffsetY = (int)(8*Game.SCALE);
    private int skill3X = 0, skill3Y = 0;
    private BufferedImage kunai, flash;

    public Fighter(float x, float y,int numberOfPlayer, int[] keyBroad_player, Character[] player) {
        super(x, y, 126, 80,
                45, 19, 30f, 50f, // offset and width, height
                keyBroad_player,
                PLAYER_1_ATLAS,
                9, 10);
        super.setAniAction(2, 5, 5, 7, 3);
        numOfPlayer = numberOfPlayer;
        initAttackBox();
        this.player = player;
        kunai = LoadSave.GetSpriteAtlas("/kunai.png");
        flash = LoadSave.GetSpriteAtlas("/Skill3.png");
    }
    private void initAttackBox(){
        attackBox.width =0;
        attackBox.height =0;
        attackBox.x=0;
        attackBox.y=0;
    }

    @Override
    protected void updateSkill() {
        long currentTime = System.currentTimeMillis();
        if (currentTime-preTimeSkill1 >= SKILL_1_WORK_TIME ){
            skill_1 = false;
            activateAttackBox = false;
        }
        if (currentTime-preTimeSkill2 >= SKILL_2_WORK_TIME ){
            skill_2 = false;
            activateBox2 = false;
        }
        if (currentTime-preTimeSkill3 >= SKILL_3_WORK_TIME ){
            skill_3 = false;
        }
        if (currentTime-preTimeSkill4 >= SKILL_4_WORK_TIME ){
            skill_4 = false;
        }
        if(!skill_4 && firstEndSkill4){
//            playerSpeed = PLAYER_SPEED_DEFAULT*Game.SCALE;
            firstEndSkill4 = false;
            changeSpeed(2);

            if(!skill_1){
                aniSpeed = ANI_SPEED_DEFAULT;
            }
        }
        updateSkill1();
        updateSkill2();
        updateSkill3();
        updateSkill4();
    }
    private void updateSkill1(){
        if(skill_1){
            if(firstUpdateSkill1){
                aniSpeed = 8; // animation faster
                activateAttackBox = true;
                firstUpdateSkill1 = false;
            }
            if (activateAttackBox){
                attackBox.width = 30*Game.SCALE;
                attackBox.height = 45*Game.SCALE;
                attackBox.x = hitBox.x + 20*Game.SCALE;
                attackBox.y = hitBox.y + 2*Game.SCALE;
                if (this.FlipW == -1)
                    attackBox = flipHorBox((int)(this.hitBox.x + this.hitBox.width/2),attackBox);

                for(int i=0;i<player.length;i++) {
                    if (i == numOfPlayer)
                        continue;
                    if (boxCollision(attackBox, player[i].getHitBox())) {
                        System.out.println(attackBox.x + " " + attackBox.y + "-" + hitBox.x + "" + hitBox.y);
                        player[i].addHP(-100);
                        activateAttackBox = false;
                    }
                }
            }

        }
    }
    private void updateSkill2(){
        if(skill_2) {
            if (firstUpdateSkill2) {
                skill2Box.width = 42 * Game.SCALE;
                skill2Box.height = 16 * Game.SCALE;
                skill2Box.y = hitBox.y + 3 * Game.SCALE;
                skill2Box.x = hitBox.x + hitBox.width/2;
                if (FlipW == -1){
                    skill2Speed = -2*Game.SCALE;
                    flipWSkill2 = -1;
                    flipXSkill2 = DEFAULT_WIDTH_KUNAI;
                    skill2Box = flipHorBox((int) (hitBox.x + hitBox.width/2),skill2Box);
                }else {
                    skill2Speed = 3f*Game.SCALE;
                    flipWSkill2 = 1;
                    flipXSkill2 = 0;
                }

                firstUpdateSkill2 = false;
                activateBox2 = true;
            }
            if (activateBox2) {

                if(flipWSkill2 == -1){
                    if(IsSolidBox(skill2Box.x+skill2Speed, skill2Box.y, 5*Game.SCALE, skill2Box.height, this.mapData)){
                        skill2Box.x = GetEntityNextPosToWall(skill2Box,skill2Speed);
                        activateBox2 = false;
                    }else{
                        skill2Box.x += skill2Speed;
                    }
                }else {
                    if(IsSolidBox(skill2Box.x + skill2Box.width -5*Game.SCALE +skill2Speed, skill2Box.y, 5*Game.SCALE, skill2Box.height, this.mapData)){
                        skill2Box.x = GetEntityNextPosToWall(skill2Box,skill2Speed);
                        activateBox2 = false;
                    }else{
                        skill2Box.x += skill2Speed;
                    }
                }
                for(int i=0;i<player.length;i++){
                    if (i==this.numOfPlayer)
                        continue;
                    if (boxCollision(skill2Box, player[i].getHitBox())) {
                        player[i].addHP(-70);
                        activateBox2 = false;
//                        skill_2 = false;
                    }
                }
            }



        }


    }

    private void updateSkill3(){
        if (skill_3) {
            if (firstUpdateSkill3){
                if (!IsSolidBox(skill2Box.x+1, skill2Box.y-20*Game.SCALE, hitBox.width, hitBox.height, mapData)) {
                    skill3X = (int)(hitBox.x-xDrawOffset);
                    skill3Y = (int)(hitBox.y-yDrawOffset);
                    hitBox.x = skill2Box.x+1;
                    hitBox.y = skill2Box.y-20*Game.SCALE;
                    firstUpdateSkill3 = false;
                    skill_2 = false;

                }
            }
        }
    }
    private void updateSkill4(){
        if(skill_4){
            if(firstUpdateSkill4){
                aniSpeed = 25; // animation faster
                activateBox4 = true;
                firstUpdateSkill4 = false;
//                playerSpeed = PLAYER_SPEED_DEFAULT/2*Game.SCALE;
                changeSpeed(0.5f);
            }
            if(SKILL_4_START_ACTIVATE_BOX_TIME < System.currentTimeMillis() - preTimeSkill4)
                if (activateBox4){
                    skill4Box.width = 70*Game.SCALE;
                    skill4Box.height = 70*Game.SCALE;
                    skill4Box.x = hitBox.x + 10*Game.SCALE;
                    skill4Box.y = hitBox.y - 15*Game.SCALE;
                    if (this.FlipW == -1)
                        skill4Box = flipHorBox((int)(this.hitBox.x + this.hitBox.width/2),skill4Box);

                    for(int i=0;i<player.length;i++) {
                        if (i == numOfPlayer)
                            continue;
                        if (boxCollision(skill4Box, player[i].getHitBox())) {
                            System.out.println(skill4Box.x + " " + skill4Box.y + "-" + hitBox.x + "" + hitBox.y);
                            player[i].addHP(-200);
                            activateBox4 = false;
                        }
                    }
                }

        }
    }
    @Override
    public void setSkillAni(){
        if(skill_1)
            playerAction = NORMAL_ATTACK;
        if(skill_4)
            playerAction = SP_ATTACK;
    }
    private void renderAttackBox(Graphics g, int xLvlOffset, int yLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x-xLvlOffset, (int)attackBox.y-yLvlOffset, (int)attackBox.width, (int)attackBox.height);
    }

    private void renderSkill2(Graphics g,int xLvlOffset,int yLvlOffset){
        g.drawImage(kunai,
                (int)(skill2Box.x - skill2OffsetX - xLvlOffset + flipXSkill2),
                (int)(skill2Box.y - skill2OffsetY-yLvlOffset),
                DEFAULT_WIDTH_KUNAI*flipWSkill2, DEFAULT_HEIGHT_KUNAI,null);
    }
    private void renderSkill3(Graphics g,int xLvlOffset,int yLvlOffset){
        g.drawImage(flash,
                (int)(skill3X - xLvlOffset),
                (int)(skill3Y - yLvlOffset),
                DEFAULT_WIDTH_KUNAI, DEFAULT_HEIGHT_KUNAI,null);
    }
    private void renderBox2(Graphics g, int xLvlOffset, int yLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)skill2Box.x-xLvlOffset, (int)skill2Box.y-yLvlOffset, (int)skill2Box.width, (int)skill2Box.height);
    }
    private void renderBox4(Graphics g, int xLvlOffset, int yLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)skill4Box.x-xLvlOffset, (int)skill4Box.y-yLvlOffset, (int)skill4Box.width, (int)skill4Box.height);
    }
    @Override
    protected void renderSkill(Graphics g,int xLvlOffset,int yLvlOffset) {

        if(skill_2) {
            renderSkill2(g, xLvlOffset, yLvlOffset);
        }
        if(skill_3){
            renderSkill3(g, xLvlOffset, yLvlOffset);
        }
        if(activateAttackBox)
            renderAttackBox(g, xLvlOffset, yLvlOffset);
        if(activateBox2)
            renderBox2(g, xLvlOffset, yLvlOffset);
        if(activateBox4)
            renderBox4(g, xLvlOffset, yLvlOffset);
    }
    @Override
    public void setSkill_1(boolean val){
        long currentTime = System.currentTimeMillis();

        if(val && currentTime - preTimeSkill1 >= SKILL_1_RECOVERY_TIME) {
            firstUpdateSkill1 = true;
            skill_1 = true;
            preTimeSkill1 = System.currentTimeMillis();
        }
    }
    @Override
    public void setSkill_2(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill2 >=SKILL_2_RECOVERY_TIME) {
            skill_2 = true;
            firstUpdateSkill2 = true;
            preTimeSkill2 = System.currentTimeMillis();
        }
    }
    @Override
    public void setSkill_3(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill3 >=SKILL_3_RECOVERY_TIME) {
            skill_3 = true;
            firstUpdateSkill3 = true;
            if(skill_2) { // if skill2 do not work, skill_3 will not work
                preTimeSkill3 = System.currentTimeMillis();
            }
        }
    }
    @Override
    public void setSkill_4(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill4 >=SKILL_4_RECOVERY_TIME) {
            skill_4 = true;
            firstUpdateSkill4 = true;
            firstEndSkill4 = true;
            preTimeSkill4 = System.currentTimeMillis();
        }
    }

}
