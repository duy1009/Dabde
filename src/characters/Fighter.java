package characters;

import static utilz.Constants.PLAYER_1_ATLAS;

public class Fighter extends Character {
    public Fighter(float x, float y, int[] keyBroad_player) {
        super(x, y, 126, 80,
                45, 19, 30f, 50f, // offset and width, height
                keyBroad_player,
                PLAYER_1_ATLAS,
                8, 10);
        super.setAniAction(2, 5, 5, 7, 3);
    }

}
