package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.OUT_SIDE_HEIGHT;
import static utilz.Constants.OUT_SIDE_WIDTH;

public class LoadSave {
    public static BufferedImage GetSpriteAtlas(String path){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream(path);
        try{
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }
    public static int[][] GetLvlData(String path){
        BufferedImage img = GetSpriteAtlas(path);
        int lvlData[][] = new int[img.getHeight()][img.getWidth()];
        int maxColor = OUT_SIDE_HEIGHT*OUT_SIDE_WIDTH;

        for(int i=0; i<img.getHeight();i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int val = color.getRed();
                if (val >= maxColor) {
                    val = 0;
                }
                lvlData[i][j] = val;
            }
        }


        return lvlData;
    }

    public static BufferedImage[] loadArrayAni_1D(String path, int aniNum){
        BufferedImage img = GetSpriteAtlas(path);
        BufferedImage[] ani = new BufferedImage[aniNum];
        float width = img.getWidth()/aniNum;
        float height = img.getHeight();
        for(int i=0; i< ani.length;i++)
            ani[i] = img.getSubimage((int)(i *width), 0, (int)width, (int)height);
        return ani;
    }
    public static BufferedImage[][] loadArrayAni_2D(String path, int numW, int numH){
        BufferedImage img = GetSpriteAtlas(path);
        BufferedImage[][] ani = new BufferedImage[numH][numW];
        float width = img.getWidth()/numW;
        float height = img.getHeight()/numH;
        for(int i=0; i< ani.length;i++) // load animation first
            for(int j=0;j < ani[i].length;j++){
                ani[i][j] = img.getSubimage((int)(j * width), (int)(i * height), (int)width, (int)height);
            }

        return ani;
    }
}
