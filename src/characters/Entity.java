package characters;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float xDrawOffset;
    protected float yDrawOffset;
    protected float width, height;
    protected Rectangle2D.Float hitBox;

    public Entity(float width, float height){
        this.width = width;
        this.height = height;
    }
    protected void drawHitBox(Graphics g, int xLvlOffset, int yLvlOffset){
        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x-xLvlOffset, (int)hitBox.y-yLvlOffset, (int)hitBox.width, (int)hitBox.height);
    }
    protected void initHitBox(float x, float y, float width, float height){
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }
//    protected void updateHitBox(){
//        hitBox.x = (int)x;
//        hitBox.y = (int)y;
//    }
    public Rectangle2D.Float getHitBox(){return hitBox;}
}
