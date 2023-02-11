package characters.skill;
import characters.Character;
import main.Game;
import utilz.HelpMethods;

import java.awt.image.BufferedImage;
import java.util.Vector;

import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsSolidBox;

public class Traps extends Objection{
    private final int EFFECT_TIME = 1500;
    private Character[] player;

    private int[][] map;
    private float airSpeed = 0;
    private float gravity = 0.04f* Game.SCALE;
    private boolean effect = false;
    private float slow = 0.5f;
    private long preTime = 0;
    private int playerEffect =0;
    private final int BASIC_DAME = 50;
    private final int TIME_PLUS_DAME = 1000;
    private final int DAME_PLUS = 10;
    private final int DAME_MAX = 180;
    private int dame = BASIC_DAME;
    private long preTimeAddDame = 0;
    public Traps(float x, float y,
                 float HB_x, float HB_y, float HB_w, float HB_h,
                 BufferedImage[][] ani,
                 int numOfPlayer, Character[] player, float FlipW, int[][] map
    ){
        super(x, y, HB_x, HB_y, HB_w, HB_h, ani, numOfPlayer, FlipW);
        this.player = player;
        this.map = map;
        preTimeAddDame = System.currentTimeMillis();
    }
    @Override
    protected void updatePos(){
        if(activate){
            if(!effect){
                for(int i=0;i<player.length;i++){
                    if(i!= numOfPlayer){
                        if(HelpMethods.boxCollision(hitBox, player[i].getHitBox())){
                            player[i].addHP(-dame);
                            player[i].changeSpeed(slow);
                            activateAni = false;
                            effect = true;
                            playerEffect = i;
                            preTime = System.currentTimeMillis();
                        }
                    }
                }
                if(!IsSolidBox(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, map)){
                    hitBox.y += airSpeed;
                    airSpeed += gravity;
                }else {
                    hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                    if (airSpeed>0){
                        airSpeed = 0;
                    }
                }
            }else{
                if(System.currentTimeMillis() - preTime> EFFECT_TIME){
                    player[playerEffect].changeSpeed(1/slow);
                    activate = false;
                }
            }

            if(dame < DAME_MAX){
                if(System.currentTimeMillis() - preTimeAddDame > TIME_PLUS_DAME){
                    dame += DAME_PLUS;
                    preTimeAddDame = System.currentTimeMillis();
                }
            }

        }
    }

}
