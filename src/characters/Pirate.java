package characters;

import static utilz.Constants.PLAYER_2_ATLAS;

public class Pirate extends Character{
    private int SKILL_1_TIME = 1000;
    private int SKILL_2_TIME = 1000;
    private int SKILL_3_TIME = 1000;
    private int SKILL_4_TIME = 1000;
    private long preTimeSkill1 = 0, preTimeSkill2 = 0, preTimeSkill3 = 0, preTimeSkill4 = 0;
    private int numOfPlayer = 0;
    private Character[] player;

    public Pirate(float x, float y, int numberOfPlayer, int[] keyBroad_player, Character[] player){
        super(x, y , 126,80,
                24, 6, 15f, 25f,
                keyBroad_player,
                PLAYER_2_ATLAS,
                8, 8);
        super.setAniAction(0,1,2,7,3);
        numOfPlayer =numberOfPlayer;
        this.player = player;
    }



    @Override
    public void setSkill_1(boolean val){
        long currentTime = System.currentTimeMillis();

        if(val && currentTime - preTimeSkill1 <=SKILL_1_TIME)
            skill_1 = true;
    }
    @Override
    public void setSkill_2(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill2 <=SKILL_2_TIME)
            skill_2 = true;
    }
    @Override
    public void setSkill_3(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill3 <=SKILL_3_TIME)
            skill_3 = true;
    }
    @Override
    public void setSkill_4(boolean val){
        long currentTime = System.currentTimeMillis();
        if(val && currentTime - preTimeSkill4 <=SKILL_4_TIME)
            skill_4 = true;
    }
}
