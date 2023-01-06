package main;

import characters.Character;
import characters.Fighter;
import characters.Pirate;
import client.ManagerSocket;
import levels.LevelManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utilz.HelpMethods;
import static utilz.Constants.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GameWindow2 gameWindow2;
    private GamePanel gamePanel;
    private GamePanel2 gamePanel2;
    private Thread gameThread;
    private LevelManager levelManager;
    private final int FPS_SET = 40;
    private final int UPS_SET = 150;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1f;
    public final static int TILES_IN_WIDTH = 25;
    public final static int TILES_IN_HEIGHT = 12;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public int xLvlOffset, yLvlOffset;
    public int xLvlOffset2, yLvlOffset2;
    private int leftBorder = (int) (0.3*GAME_WIDTH);
    private int rightBorder = (int) (0.7*GAME_WIDTH);
    private int upBorder = (int) (0.3*GAME_HEIGHT);
    private int downBorder = (int)(0.7*GAME_HEIGHT);
    private int lvlTilesWide = LoadSave.GetLvlData(MAP1)[0].length;
    private int lvlTileHigh = LoadSave.GetLvlData(MAP1).length;
    private int maxTilesOffsetX = lvlTilesWide-TILES_IN_WIDTH;
    private int maxTilesOffsetY = lvlTileHigh-TILES_IN_HEIGHT;
    private int maxLvlOffsetX = maxTilesOffsetX*TILES_SIZE;
    private int maxLvlOffsetY = maxTilesOffsetY*TILES_SIZE;
    public Character[] player = new Character[2];
    private int mainCharacter = 0;
    private ManagerSocket managerSocket;
    public Game() throws IOException {
        initClass();
//        managerSocket = new ManagerSocket(this,"localhost", 3333);
//        gamePanel = new GamePanel(this, managerSocket.getSend());
        gamePanel = new GamePanel(this);
        gamePanel2 = new GamePanel2(this);
        gameWindow = new GameWindow(gamePanel);
        gameWindow2 = new GameWindow2(gamePanel2);
        gamePanel.requestFocusInWindow();
        gamePanel.requestFocus();
        System.out.println("Map size: " + lvlTilesWide + "x" + lvlTileHigh);
        startGameLoop();
    }
    private void initPlayer(){
        int[] keyBroad_player_1 = {
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_G,
                KeyEvent.VK_T,
                KeyEvent.VK_Y,
                KeyEvent.VK_U};
        int[] keyBroad_player_2 = {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_NUMPAD1,
                KeyEvent.VK_NUMPAD4,
                KeyEvent.VK_NUMPAD5,
                KeyEvent.VK_NUMPAD6};

        player[0] = new Pirate(1000f,10f, 0,keyBroad_player_1, player);
        player[1] = new Fighter(450f,100f, 1,keyBroad_player_2, player);
        player[1].setStatusBarFlip(true);
    }
    private void initClass(){
        levelManager = new LevelManager(this);
        initPlayer();
        Character.loadMapData(levelManager.getLevelOne().getLevelData());
    }
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;
        long pre_time = System.nanoTime();
        long curr_time;
        double DeltaU = 0, DeltaF = 0;
        int frame = 0, updates = 0;

        long lastCheck = System.currentTimeMillis();
        while (true){
            curr_time = System.nanoTime();
            DeltaU += (curr_time-pre_time)/timePerUpdate;
            DeltaF += (curr_time-pre_time)/timePerFrame;
            pre_time = curr_time;
            if(DeltaF >= 1){
                gamePanel.repaint();
                gamePanel2.repaint();
                frame++;
                DeltaF--;
            }
            if(DeltaU>=1){
                update();
                updates++;
                DeltaU--;
            }
            if(System.currentTimeMillis() - lastCheck>=1000){
                System.out.println("FPS: " + frame +"|" + "UPS: " + updates);
                frame = 0;
                updates = 0;
                lastCheck = System.currentTimeMillis();
            }
        }
    }
    public void update(){
        for(int i=0;i< player.length;i++)
            player[i].update();
        levelManager.update();
        checkCloseToBorder();
        checkCloseToBorder2();
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
    public void render(Graphics g){
        levelManager.draw(g, xLvlOffset, yLvlOffset);
        for(int i=0;i< player.length;i++)
            player[i].render(g, xLvlOffset, yLvlOffset);
    }
    public void render2(Graphics g){
        levelManager.draw(g, xLvlOffset2, yLvlOffset2);
        for(int i=0;i< player.length;i++)
            player[i].render(g, xLvlOffset2, yLvlOffset2);
    }
    public void setMainCharacter(int num){mainCharacter = num;}
    public int getMainCharacter(){return mainCharacter;}
    public GamePanel getGamePanel(){return this.gamePanel;}
    public Character[] getPlayer(){return this.player;}
}
