package utilz;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

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
    public static boolean IsSolidBox(float x, float y,float width, float height, int[][] mapData){
        int maxWidth = Game.TILES_SIZE*mapData[0].length;
        if(x <= 0 || x+width >= maxWidth)
            return true;
        if(y<=0 || y+height>= Game.GAME_HEIGHT)
            return true;
        int xInxMin = (int)(x/Game.TILES_SIZE);
        int xInxMax = (int)((x+width)/Game.TILES_SIZE);
        int yInxMin = (int)(y/Game.TILES_SIZE);
        int yInxMax = (int)((y+height)/Game.TILES_SIZE);

        for(int xInx = xInxMin; xInx<=xInxMax; xInx++)
            for(int yInx = yInxMin; yInx<=yInxMax; yInx++){
                int value = mapData[yInx][xInx];
                if (value > OUT_SIDE_WIDTH*OUT_SIDE_HEIGHT || value < 0)
                    return true;

                for (int i:BLOCKS_CAN_MOVE) {
                    if(value != i) return true;
                }
            }

        return false;
    }
    public static boolean IsSolid(float x, float y, int[][] mapData){
        if(x <= 0 || x >= Game.GAME_WIDTH)
            return true;
        if(y<=0 || y>= Game.GAME_HEIGHT)
            return true;
        float xInx = x/Game.TILES_SIZE;
        float yInx = y/Game.TILES_SIZE;
        int value = mapData[(int)yInx][(int)xInx];
        if (value > OUT_SIDE_WIDTH*OUT_SIDE_HEIGHT || value < 0)
            return true;

        for (int i:BLOCKS_CAN_MOVE) {
            if(value != i) return true;
        }
        return false;
    }
    public static float GetEntityNextPosToWall(Rectangle2D.Float hitbox, float xSpeed){
        if(xSpeed>0){
            return (((int)((hitbox.x+ hitbox.width)/Game.TILES_SIZE)+1)*Game.TILES_SIZE)  - hitbox.width-1;
        }else {
            return (int)(hitbox.x/Game.TILES_SIZE)*Game.TILES_SIZE+1;
        }
    }
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed){
        int currentTile = (int)(hitBox.y/Game.TILES_SIZE);
        if(airSpeed > 0){//fall
            return (((int)((hitBox.y+ hitBox.height)/Game.TILES_SIZE)+1)*Game.TILES_SIZE)  - hitBox.height-1;
        }
        else // jump
            return (int)(hitBox.y/Game.TILES_SIZE)*Game.TILES_SIZE+1;
    }
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] mapData){
        float x = hitBox.x, y=hitBox.y, width=hitBox.width, height=hitBox.height;
        int maxWidth = Game.TILES_SIZE*mapData[0].length;
        if(x <= 0 || x+width >= maxWidth)
            return true;
        if(y<=0 || y+height+1>= Game.GAME_HEIGHT)
            return true;
        int xInxMin = (int)(x/Game.TILES_SIZE);
        int xInxMax = (int)((x+width)/Game.TILES_SIZE);
        int yInx = (int)((y+height+1)/Game.TILES_SIZE);

        for(int xInx = xInxMin; xInx<=xInxMax; xInx++){
            int value = mapData[yInx][xInx];
            if (value > OUT_SIDE_WIDTH*OUT_SIDE_HEIGHT || value < 0)
                return true;

            for (int i:BLOCKS_CAN_MOVE) {
                if (value != i) return true;
            }
        }

        return false;
    }

}
