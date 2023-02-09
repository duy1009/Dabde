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
    private BufferedImage back_ground;
    public LevelManager(Game game){
        this.game = game;
        importOutSide();
        levelOne = new Level(LoadSave.GetLvlData(MAP1));
        back_ground = LoadSave.GetSpriteAtlas(BACKGROUND);
    }
    public void importOutSide(){
        BufferedImage img = LoadSave.GetSpriteAtlas(LEVEL_ATLAS);
        levelSprite = new BufferedImage[OUT_SIDE_HEIGHT*OUT_SIDE_WIDTH];
        int ob = Game.TILES_DEFAULT_SIZE;
        for(int i=0;i<OUT_SIDE_HEIGHT;i++){
            for(int j=0;j<OUT_SIDE_WIDTH;j++){
                int index = i*OUT_SIDE_WIDTH+j;
                levelSprite[index] = img.getSubimage(j*ob,i*ob,ob,ob);
            }
        }
    }
    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        drawBackGround(g);
        for(int j=0;j<levelOne.getLevelData().length;j++)
            for(int i=0;i<levelOne.getLevelData()[0].length;i++){
                int index =levelOne.getSpriteIndex(j,i);
                g.drawImage(levelSprite[index], Game.TILES_SIZE*i - xLvlOffset, Game.TILES_SIZE*j - yLvlOffset,Game.TILES_SIZE,Game.TILES_SIZE, null);

            }
    }
    public void update(){

    }
    public void drawBackGround(Graphics g){
        g.drawImage(back_ground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
    }

    public Level getLevelOne() {
        return levelOne;
    }
}
