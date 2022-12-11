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
    private GamePanel gamePanel;
    private Thread gameThread;
    private LevelManager levelManager;
    private final int FPS_SET = 60;
    private final int UPS_SET = 150;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 15;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private int xLvlOffset;
    private int yLvlOffset;
    private int leftBorder = (int) (0.2*GAME_WIDTH);
    private int rightBorder = (int) (0.8*GAME_WIDTH);
    private int upBorder = (int) (0.2*GAME_HEIGHT);
    private int downBorder = (int)(0.8*GAME_HEIGHT);
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
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        gamePanel.requestFocus();
        System.out.println("Map size: "+lvlTilesWide + "x" +lvlTileHigh);
//        BufferedImage im = LoadSave.GetSpriteAtlas("/test.png");
//        HelpMethods.isBlankImage(im);
        startGameLoop();
    }
    private void initPlayer(){
        int[] keyBroad_player_1 = {
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D};
        int[] keyBroad_player_2 = {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT};

        player[0] = new Pirate(1000f,10f, keyBroad_player_1);
        player[1] = new Fighter(450f,100f, keyBroad_player_2);

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
        long curr_time = System.nanoTime();
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
                System.out.println("Main Character:"+player[mainCharacter].getHitBox().x/32 +" "+player[mainCharacter].getHitBox().y/32);
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
    public void render(Graphics g){
        levelManager.draw(g, xLvlOffset, yLvlOffset);
        for(int i=0;i< player.length;i++)
            player[i].render(g, xLvlOffset, yLvlOffset);
    }
    public void setMainCharacter(int num){mainCharacter = num;}
    public int getMainCharacter(){return mainCharacter;}
    public GamePanel getGamePanel(){return this.gamePanel;}
    public Character[] getPlayer(){return this.player;}
}
