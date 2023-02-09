package gamestates;

import audio.AudioPlayer;
import characters.Character;
import characters.Fighter;
import characters.Pirate;
import characters.skill.Objection;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static utilz.Constants.MAP1;

public class Playing extends State implements StateMethods{
    public Character[] player = new Character[2];
    private int mainCharacter = 0;
    private LevelManager levelManager;
    public int xLvlOffset, yLvlOffset;
    public int xLvlOffset2, yLvlOffset2;
    private int leftBorder = (int) (0.3*Game.GAME_WIDTH);
    private int rightBorder = (int) (0.7*Game.GAME_WIDTH);
    private int upBorder = (int) (0.3*Game.GAME_HEIGHT);
    private int downBorder = (int)(0.7*Game.GAME_HEIGHT);
    private int lvlTilesWide = LoadSave.GetLvlData(MAP1)[0].length;
    private int lvlTileHigh = LoadSave.GetLvlData(MAP1).length;
    private int maxTilesOffsetX = lvlTilesWide-Game.TILES_IN_WIDTH;
    private int maxTilesOffsetY = lvlTileHigh-Game.TILES_IN_HEIGHT;
    private int maxLvlOffsetX = maxTilesOffsetX*Game.TILES_SIZE;
    private int maxLvlOffsetY = maxTilesOffsetY*Game.TILES_SIZE;
    private Vector<Objection> obj;
    public Playing(Game game) {
        super(game);
        initClass();
    }
    public void initClass(){
        levelManager = new LevelManager(game);
        Character.loadMapData(levelManager.getLevelOne().getLevelData());
        obj = new Vector<>();
    }
    public Character[] getPlayer(){return this.player;}

    @Override
    public void update() {
        for(int i=0;i< player.length;i++)
            player[i].update();
        for(Objection i:obj){
            if(i.isActivate())
                i.update();
        }
        levelManager.update();
        checkCloseToBorder();
        checkCloseToBorder2();
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset, yLvlOffset);
        for(int i=0;i< player.length;i++)
            player[i].render(g, xLvlOffset, yLvlOffset);
        for(Objection i:obj){
            if(i.isActivateAni())
                i.render(g, xLvlOffset, yLvlOffset);
        }
    }
    @Override
    public void draw2(Graphics g){
        levelManager.draw(g, xLvlOffset2, yLvlOffset2);
        for(int i=0;i< player.length;i++)
            player[i].render(g, xLvlOffset2, yLvlOffset2);
        for(Objection i:obj){
            if(i.isActivateAni())
                i.render(g, xLvlOffset2, yLvlOffset2);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        Character Player[] = gamePanel.getGame().getPlayer();
        for (int i =0; i<player.length; i++){
            if (e.getKeyCode()==player[i].getUpCtrl()){
                player[i].setJump(true);
            }else if (e.getKeyCode()==player[i].getDownCtrl()){
                player[i].setDown(true);
            }else if (e.getKeyCode()==player[i].getLeftCtrl()){
                player[i].setLeft(true);
            }else if (e.getKeyCode()==player[i].getRightCtrl()){
                player[i].setRight(true);
            } else if (e.getKeyCode()==player[i].getSkill_1_ctrl()){
                player[i].setSkill_1(true);
            }else if (e.getKeyCode()==player[i].getSkill_2_ctrl()){
                player[i].setSkill_2(true);
            }else if (e.getKeyCode()==player[i].getSkill_3_ctrl()){
                player[i].setSkill_3(true);
            }else if (e.getKeyCode()==player[i].getSkill_4_ctrl()){
                player[i].setSkill_4(true);
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (int i =0; i<player.length; i++){
            if (e.getKeyCode()==player[i].getUpCtrl()){
                player[i].setJump(false);
            }else if (e.getKeyCode()==player[i].getDownCtrl()){
                player[i].setDown(false);
            }else if (e.getKeyCode()==player[i].getLeftCtrl()){
                player[i].setLeft(false);
            }else if (e.getKeyCode()==player[i].getRightCtrl()){
                player[i].setRight(false);
            }
        }
    }

    private void checkCloseToBorder(){
        int playerX = (int) player[mainCharacter].getHitBox().x;
        int playerY = (int) player[mainCharacter].getHitBox().y;
        int diffX = playerX - xLvlOffset;
        int diffY = playerY - yLvlOffset;

        if (diffX >rightBorder){
            xLvlOffset+=diffX-rightBorder;}
        else if(diffX <leftBorder)
            xLvlOffset += diffX - leftBorder;
        if (diffY >downBorder){
            yLvlOffset+=diffY - downBorder;}
        else if(diffY <upBorder)
            yLvlOffset += diffY - upBorder;

        if (xLvlOffset>maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if(xLvlOffset<0){
            xLvlOffset = 0;
        }
        if (yLvlOffset>maxLvlOffsetY)
            yLvlOffset = maxLvlOffsetY;
        else if(yLvlOffset<0){
            yLvlOffset = 0;
        }
    }
    private void checkCloseToBorder2(){
        int playerX = (int) player[1].getHitBox().x;
        int playerY = (int) player[1].getHitBox().y;
        int diffX = playerX - xLvlOffset2;
        int diffY = playerY - yLvlOffset2;

        if (diffX >rightBorder){
            xLvlOffset2+=diffX-rightBorder;}
        else if(diffX <leftBorder)
            xLvlOffset2 += diffX - leftBorder;
        if (diffY >downBorder){
            yLvlOffset2+=diffY - downBorder;}
        else if(diffY <upBorder)
            yLvlOffset2 += diffY - upBorder;

        if (xLvlOffset2>maxLvlOffsetX)
            xLvlOffset2 = maxLvlOffsetX;
        else if(xLvlOffset2<0){
            xLvlOffset2 = 0;
        }
        if (yLvlOffset2>maxLvlOffsetY)
            yLvlOffset2 = maxLvlOffsetY;
        else if(yLvlOffset2<0){
            yLvlOffset2 = 0;
        }
    }
    public void setMainCharacter(int num){mainCharacter = num;}
    public int getMainCharacter(){return mainCharacter;}
    public Vector<Objection> getObj(){return obj;}

}
