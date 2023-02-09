package main;

import audio.AudioPlayer;
import client.ManagerSocket;
import gamestates.*;
import gamestates.Menu;

import java.awt.*;

import java.io.IOException;


public class Game implements Runnable{
    private GameWindow gameWindow;
    private GameWindow2 gameWindow2;
    private GamePanel gamePanel;
    private GamePanel2 gamePanel2;
    private Thread gameThread;

    private Playing playing;
    private Menu menu;
    private Setting setting;
    private AudioPlayer audioPlayer;
    private CharacterPicker characterPicker;
    private int FPS_SET = 60;
    private final int UPS_SET = 150;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1f;
    public final static int TILES_IN_WIDTH = 22;
    public final static int TILES_IN_HEIGHT = 18;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

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
        gamePanel2.requestFocusInWindow();
        gamePanel2.requestFocus();
        startGameLoop();
//        obj.add(ind, value);
//        obj.remove(ind);
//        obj.size();
    }

    private void initClass() {
        audioPlayer = new AudioPlayer();
        audioPlayer.setVolume(0.9f);
        audioPlayer.playMenuSong();

        menu = new Menu(this);
        playing = new Playing(this);
        characterPicker = new CharacterPicker(this);
        setting = new Setting(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){

        double timePerUpdate = 1000000000.0/UPS_SET;
        long pre_time = System.nanoTime();
        long curr_time;
        double DeltaU = 0, DeltaF = 0;
        int frame = 0, updates = 0;

        long lastCheck = System.currentTimeMillis();
        while (true){
            double timePerFrame = 1000000000.0/FPS_SET;
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
        switch (GameState.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                setting.update();
                break;
            case QUIT:
                System.exit(0);
            case PICK:
                characterPicker.update();
                break;
            default:
                break;
        }
    }

    public void render(Graphics g){
        switch (GameState.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case PICK:
                characterPicker.draw(g);
                break;
            case OPTIONS:
                setting.draw(g);
            default:
                break;
        }

    }
    public void render2(Graphics g){
        switch (GameState.state){
            case MENU:
                menu.draw2(g);
                break;
            case PLAYING:
                playing.draw2(g);
                break;
            case PICK:
                characterPicker.draw2(g);
                break;
            case OPTIONS:
                setting.draw2(g);
                break;
            default:
                break;
        }
    }

    public GamePanel getGamePanel(){return this.gamePanel;}
    public Menu getMenu(){return menu;}
    public Playing getPlaying(){return playing;}
    public AudioPlayer getAudioPlayer(){return  audioPlayer;}
    public CharacterPicker getPick(){return characterPicker;}
    public Setting getOption(){return setting;}
    public void setFPS(int value){this.FPS_SET = value;}
}
