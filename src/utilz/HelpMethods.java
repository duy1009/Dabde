package utilz;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;

public class HelpMethods {

    public static boolean IsSolidBox(float x, float y,float width, float height, int[][] mapData){
        int maxWidth = Game.TILES_SIZE*mapData[0].length;
        int maxHeight = Game.TILES_SIZE*mapData.length;
        if(x <= 0 || x+width >= maxWidth)
            return true;
        if(y<=0 || y+height>= maxHeight)
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
                boolean solid = true;
                for (int i:BLOCKS_CAN_MOVE) {
                    if(value == i) {
                        solid = false;
                        break;
                    }
                }
                if (solid) return true;
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
        if(airSpeed > 0){//fall
            return (((int)((hitBox.y+ hitBox.height)/Game.TILES_SIZE)+1)*Game.TILES_SIZE)  - hitBox.height-1;
        }
        else // jump
            return (int)(hitBox.y/Game.TILES_SIZE)*Game.TILES_SIZE+1;
    }
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] mapData){
        float x = hitBox.x, y=hitBox.y, width=hitBox.width, height=hitBox.height;
        int maxWidth = Game.TILES_SIZE*mapData[0].length;
        int maxHeight = Game.TILES_SIZE*mapData.length;
        if(x <= 0 || x+width >= maxWidth)
            return true;
        if(y<=0 || y+height+1>= maxHeight)
            return true;
        int xInxMin = (int)(x/Game.TILES_SIZE);
        int xInxMax = (int)((x+width)/Game.TILES_SIZE);
        int yInx = (int)((y+height+1)/Game.TILES_SIZE);

        for(int xInx = xInxMin; xInx<=xInxMax; xInx++){
            int value = mapData[yInx][xInx];
            if (value > OUT_SIDE_WIDTH*OUT_SIDE_HEIGHT || value < 0)
                return true;

//            for (int i:BLOCKS_CAN_MOVE) {
//                if (value != i) return true;
//            }
            boolean solid = true;
            for (int i:BLOCKS_CAN_MOVE) {
                if(value == i) {
                    solid = false;
                    break;
                }
            }
            if (solid) return true;
        }

        return false;
    }

}
