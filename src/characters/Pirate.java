package characters;

import static utilz.Constants.PLAYER_2_ATLAS;

public class Pirate extends Character{
    public Pirate(float x, float y, int[] keyBroad_player){
        super(x, y , 126,80,
                24, 6, 15f, 25f,
                keyBroad_player,
                PLAYER_2_ATLAS,
                8, 8);
        super.setAniAction(0,1,2,7,3);
    }
}
