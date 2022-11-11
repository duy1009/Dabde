package utilz;

import main.Game;

import static utilz.Constants.*;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y,float width, float height, int[][] mapData){
        if(!IsSolid(x,y,mapData))
            if(!IsSolid(x+width,y, mapData))
                if(!IsSolid(x,y+height, mapData))
                    if(!IsSolid(x+width,y+height, mapData))
                        return true;
        return false;
    }

    public static boolean IsSolid(float x, float y, int[][] mapData){
        if(x <= 0 || x >= Game.GAME_WIDTH)
            return true;
        if(y<=0 || y>= Game.GAME_HEIGHT)
            return true;
        float xInx = x/OUT_SIDE_ONE_BLOCK;
        float yInx = y/OUT_SIDE_ONE_BLOCK;
        int value = mapData[(int)yInx][(int)xInx];
        if (value > OUT_SIDE_WIDTH*OUT_SIDE_HEIGHT || value < 0)
            return true;

        for (int i:COLLISION_VALUE) {
            if(value == i) return true;
        }
        return false;
    }
}
