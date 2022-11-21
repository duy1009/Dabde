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
                System.out.print(val + " ");
            }
            System.out.println(" ");
        }


        return lvlData;
    }

}
