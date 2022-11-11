package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    public LevelManager(Game game){
        this.game = game;
//        levelSprite = LoadSave.GetSpriteAtlas(LEVEL_ATLAS);
        importOutSide();
        levelOne = new Level(LoadSave.GetLvlData(MAP1));
    }
    public void importOutSide(){
        BufferedImage img = LoadSave.GetSpriteAtlas(LEVEL_ATLAS);
        levelSprite = new BufferedImage[OUT_SIDE_HEIGHT*OUT_SIDE_WIDTH];
        int ob = OUT_SIDE_ONE_BLOCK;
        for(int i=0;i<OUT_SIDE_HEIGHT;i++){
            for(int j=0;j<OUT_SIDE_WIDTH;j++){
                int index = i*OUT_SIDE_WIDTH+j;
                levelSprite[index] = img.getSubimage(j*ob,i*ob,ob,ob);
            }
        }
    }
    public void draw(Graphics g){
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
            for(int i=0;i<Game.TILES_IN_WIDTH;i++){
                int index =levelOne.getSpriteIndex(j,i);
                g.drawImage(levelSprite[index], Game.TILES_SIZE*i, Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE, null);

            }
    }
    public void update(){

    }

    public Level getLevelOne() {
        return levelOne;
    }
}
