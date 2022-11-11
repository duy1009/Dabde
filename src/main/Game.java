package main;

import characters.Character;
import levels.LevelManager;
import utilz.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static utilz.Constants.CATCHING_TEAM;
import static utilz.Constants.RUNNING_TEAM;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private LevelManager levelManager;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public Character[] player = new Character[2];
    public Game() throws IOException {
        initClass();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        gamePanel.requestFocus();
        startGameLoop();

    }
    private void initPlayer(){
        int[] keyBroad_player_1 = {
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D};
        player[0] = new Character(
                200f,100f, 32,50,
                CATCHING_TEAM,
                keyBroad_player_1,
                Constants.PLAYER_1_ATLAS,
                4, 12);
        int[] keyBroad_player_2 = {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT};
        player[1] = new Character(
                100f,100f, 32,50,
                RUNNING_TEAM,
                keyBroad_player_2,
                Constants.PLAYER_2_ATLAS,
                4, 12);
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
    }
    public void render(Graphics g){
        levelManager.draw(g);
        for(int i=0;i< player.length;i++)
            player[i].render(g);

    }
    public GamePanel getGamePanel(){return this.gamePanel;}
    public Character[] getPlayer(){return this.player;}
}
