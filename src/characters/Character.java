package characters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Character implements CharacterConst{
    private float x, y; // pos
    private boolean alive;
    private int team;
    private int up_ctrl, down_ctrl, left_ctrl, right_ctrl; // controller
    private BufferedImage img;
    private BufferedImage[][] Animations;
    private int aniTick, aniIndex, aniSpeed = 15;   // frame update an animation
    private int ani_row_max, ani_col_max, chr_w, chr_h;

    public Character(float x, float y,
                     int team,
                     int[] ctrl, // {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D}
                     String animation_path,
                     int ani_row_max, int ani_col_max) // pixel
    {
        setControl(ctrl);
        this.x = x; this.y = y;
        this.ani_col_max = ani_col_max; this.ani_row_max = ani_row_max;
        this.team = team;
        this.alive = true;
        importImg(animation_path);
        loadAnimation();
    }
    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void killed(){
        alive = false;
    }
    public boolean isAlive(){
        return alive;
    }

    private void loadAnimation() {
        Animations = new BufferedImage[ani_row_max][ani_col_max];
        for(int i=0; i< Animations.length;i++) // load animation first
            for(int j=0;j < Animations[i].length;j++){
                Animations[i][j] = img.getSubimage(j * chr_w, i * chr_h, chr_w, chr_h);
            }
    }
    public void updateAnimationTick() {
        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= Animations[0].length)
                aniIndex = 0;
        }
    }
    public void updateAnimation(Graphics g){
        updateAnimationTick();
        g.drawImage(Animations[2][aniIndex].getSubimage(0,0,chr_w,chr_h),(int)x,(int)y,null);
    }
    public void importImg(String path){
        InputStream is = getClass().getResourceAsStream(path);
        try{
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }
        this.chr_h = (int)(img.getHeight()/ani_row_max);
        this.chr_w = (int)(img.getWidth()/ani_col_max);

    }
    public void setControl(int[] ctrl){
        this.up_ctrl = ctrl[0];
        this.down_ctrl = ctrl[1];
        this.left_ctrl = ctrl[2];
        this.right_ctrl = ctrl[3];
    }

}
