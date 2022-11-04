package main;

import characters.Character;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private final int FPS_SET = 120;
    private Thread gameThread;

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
//        gamePanel.requestFocus();
        startGameLoop();
//        char[] ctrl_NV1 = {'w','s','a','f'};
//        NV1 = new Character(200,100, Character.CATCHING_TEAM, ctrl_NV1);

    }
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        double timePerFrame = 1000000000.0/FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frame = 0;
        long lastCheck = System.currentTimeMillis();
        while (true){
            now = System.nanoTime();
            if(now- lastFrame >= timePerFrame){
                gamePanel.repaint();
                lastFrame = now;
                frame++;
            }

            if(System.currentTimeMillis() - lastCheck>=1000){
                System.out.println("FPS: " + frame);
                frame = 0;
                lastCheck = System.currentTimeMillis();
            }
        }
    }
}
