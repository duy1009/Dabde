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
    public static boolean boxCollision(Rectangle2D.Float box1, Rectangle2D.Float box2){
        if (Math.abs(Math.abs(box1.x + box1.width/2) - Math.abs(box2.x + box2.width/2)) < Math.abs(box1.width+box2.width)/2)
            if (Math.abs(Math.abs(box1.y + box1.height/2) - Math.abs(box2.y + box2.height/2)) < Math.abs(box1.height+box2.height)/2)
                return true;
        return false;
    }
    public static Rectangle2D.Float flipHorBox(int x, Rectangle2D.Float box){
        box.x = 2*x - box.x - box.width;
        return box;
    }
    public static void drawRect(Graphics g, int x, int y, int width, int height, int stroke){
//        for(int i=0;i<stroke;i++){
//            g.drawRect(x+i,y+i,width-i*2,height-i*2);
//        }
        g.fillRect(x,y, stroke,height-1);
        g.fillRect(x+width-1,y, stroke,height);
        g.fillRect(x,y, width-1,stroke);
        g.fillRect(x,y+height-2, width,stroke);
    }

}
