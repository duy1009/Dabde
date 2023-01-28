package characters;

import characters.skill.Objection;
import characters.skill.Traps;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import static utilz.Constants.*;
import static utilz.HelpMethods.boxCollision;
import static utilz.HelpMethods.flipHorBox;

public class Pirate extends Character{
    private final int ICE_BLOCK = 47;
    private final int AIR_BLOCK = 11;
    private int NORMAL_ATTACK = 4;
    private int SKILL_1_RECOVERY_TIME = 350;
    private int SKILL_2_RECOVERY_TIME = 10000;
    private int SKILL_3_RECOVERY_TIME = 10000;
    private int SKILL_4_RECOVERY_TIME = 5000;
    private int SKILL_1_WORK_TIME = 300;
    private int SKILL_2_WORK_TIME = 3000;
    private int SKILL_3_WORK_TIME = 500;
    private int SKILL_4_WORK_TIME = 4000;
    private float RATIO_SKILL_2 = 2f;
    private long preTimeSkill1 = 0, preTimeSkill2 = 0, preTimeSkill3 = 0, preTimeSkill4 = 0;

    private Character[] player;
    private Rectangle2D.Float attackBox = new Rectangle2D.Float();
    private Rectangle2D.Float skill2Box = new Rectangle2D.Float();
    private Rectangle2D.Float skill4Box = new Rectangle2D.Float();
    private int flipWSkill2 = 1, flipXSkill2 = 0;
    private boolean activateAttackBox = false, activateBox2 = false, activateBox3 = false, activateBox4 = false;
    private boolean firstUpdateSkill1 = false, firstUpdateSkill2 = false, firstUpdateSkill3 = false,firstUpdateSkill4 = false;
    private boolean firstEndSkill2;
    private boolean firstEndSkill4;
    private int applyAniSkill4 = 0;
    private BufferedImage[][] trapAni;
    private Vector<Objection> obj;
    private int indX1=-1, indY1=-1, indX2=-1, indY2=-1, indX3=-1, indY3=-1;
    private final int SKILL_4_TICK_DEFAULT = 60;
    private int skill4Tick = SKILL_4_TICK_DEFAULT;

    public Pirate(float x, float y, int numberOfPlayer, int[] keyBroad_player, Character[] player, Vector<Objection> obj){
        super(x, y , 126,80,
                24, 6, 15f, 25f,
                keyBroad_player,
                PLAYER_2_ATLAS,
                8, 8);
        super.setAniAction(0,1,2,7,3);
        numOfPlayer =numberOfPlayer;
        this.obj = obj;
        this.player = player;

        this.trapAni = LoadSave.loadArrayAni_2D(TRAP_ATLAS, 4,1);

    }
    public Pirate(Character[] player, Vector<Objection> obj){
        super(0, 0 , 126,80,
                24, 6, 15f, 25f,
                null,
                PLAYER_2_ATLAS,
                8, 8);
        super.setAniAction(0,1,2,7,3);
        this.obj = obj;
        this.player = player;
        this.trapAni = LoadSave.loadArrayAni_2D(TRAP_ATLAS, 4,1);
    }


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
            createAirBlock(indX1, indY1);
            createAirBlock(indX2, indY2);
            createAirBlock(indX3, indY3);
            firstEndSkill4 = false;
        }
        if(!skill_2 && firstEndSkill2){
            changeSpeed(1/RATIO_SKILL_2);
            firstEndSkill2 = false;
        }
        if(!skill_1){
            aniSpeed = ANI_SPEED_DEFAULT;
        }
        updateSkill1();
        updateSkill2();
        updateSkill3();
        updateSkill4();
    }

    private void updateSkill1(){
        if(skill_1){
            if(firstUpdateSkill1){
                aniSpeed = 15; // animation faster
                activateAttackBox = true;
                firstUpdateSkill1 = false;
            }
            if (activateAttackBox){
                attackBox.width = 45*Game.SCALE;
                attackBox.height = 20*Game.SCALE;
                attackBox.x = hitBox.x + 30*Game.SCALE;
                attackBox.y = hitBox.y + 25*Game.SCALE;
                if (this.FlipW == -1)
                    attackBox = flipHorBox((int)(this.hitBox.x + this.hitBox.width/2),attackBox);

                for(int i=0;i<player.length;i++) {
                    if (i == numOfPlayer)
                        continue;
                    if (boxCollision(attackBox, player[i].getHitBox())) {
                        System.out.println(attackBox.x + " " + attackBox.y + "-" + hitBox.x + "" + hitBox.y);
                        player[i].addHP(-80);
                        activateAttackBox = false;
                    }
                }
            }

        }
    }
    private void updateSkill2(){
        if(skill_2){
            if(firstUpdateSkill2){
                changeSpeed(RATIO_SKILL_2);
                firstUpdateSkill2 = false;
            }
        }
    }
    private void updateSkill3(){
        if(skill_3){
            if(firstUpdateSkill3){
                Traps newTrap = new Traps(hitBox.x, hitBox.y,
                        0f,0f,25f,21f,
                        trapAni,
                        numOfPlayer, player, FlipW,
                        obj, mapData);
                this.obj.add(newTrap);
                firstUpdateSkill3 = false;
            }
        }
    }
    private void updateSkill4(){
        if(skill_4){
            if(firstUpdateSkill4){
                if(FlipW == 1){
                    indX1 = (int)((hitBox.x+hitBox.width)/Game.TILES_SIZE + 1);
                    indX2 = (int)((hitBox.x+hitBox.width)/Game.TILES_SIZE + 1)+1;
                    indX3 = (int)((hitBox.x+hitBox.width)/Game.TILES_SIZE + 1)+1;
                }else {
                    indX1 = (int)(hitBox.x/Game.TILES_SIZE - 1);
                    indX2 = (int)(hitBox.x/Game.TILES_SIZE - 1)-1;
                    indX3 = (int)(hitBox.x/Game.TILES_SIZE - 1)-1;
                }
                indY1 = (int)(hitBox.y/Game.TILES_SIZE);
                indY2 = (int)(hitBox.y/Game.TILES_SIZE);
                indY3 = (int)(hitBox.y/Game.TILES_SIZE)-1;

                if(!createIceBlock(indX1, indY1))
                    preTimeSkill4 = 0;
                firstUpdateSkill4 = false;
            }
            else if(skill4Tick == 0){
                createIceBlock(indX2, indY2);
                createIceBlock(indX3, indY3);
            }
            skill4Tick--;
        }
    }
    private boolean inMap(int indX, int indY){
        if(indX<mapData[0].length && indX>=0)
            if(indY<mapData.length && indY>=0)
                return true;
        return false;
    }
    private boolean createIceBlock(int indX, int indY){
        if(inMap(indX,indY)) {
            mapData[indY][indX] = ICE_BLOCK;
            return true;
        }
        return false;
    }
    private boolean createAirBlock(int indX, int indY){
        if(inMap(indX,indY)) {
            mapData[indY][indX] = AIR_BLOCK;
            return true;
        }
        return false;
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
            firstEndSkill2 = true;
            preTimeSkill2 = System.currentTimeMillis();
        }
    }
    @Override
    public void setSkill_3(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill3 >=SKILL_3_RECOVERY_TIME) {
            skill_3 = true;
            firstUpdateSkill3 = true;
            preTimeSkill3 = System.currentTimeMillis();

        }
    }
    @Override
    public void setSkill_4(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill4 >=SKILL_4_RECOVERY_TIME) {
            skill_4 = true;
            firstUpdateSkill4 = true;
            firstEndSkill4 = true;
            applyAniSkill4 = aniSpeed*4;
            skill4Tick = SKILL_4_TICK_DEFAULT;
            preTimeSkill4 = System.currentTimeMillis();
        }
    }
    @Override
    public void setSkillAni(){
        if(skill_1)
            playerAction = NORMAL_ATTACK;
        if(applyAniSkill4 >0) {
            playerAction = JUMP;
            applyAniSkill4--;
        }
    }
    private void renderAttackBox(Graphics g, int xLvlOffset, int yLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x-xLvlOffset, (int)attackBox.y-yLvlOffset, (int)attackBox.width, (int)attackBox.height);
    }

    @Override
    protected void renderSkill(Graphics g,int xLvlOffset,int yLvlOffset) {
        if(activateAttackBox)
            renderAttackBox(g, xLvlOffset, yLvlOffset);

    }
}
