package main;

import client.Send;
import inputs.KeybroadInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel2 extends JPanel {
    private Game game;

    public GamePanel2(Game game){
        setPanelSize();
        this.game = game;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render2(g);
    }

    public void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }
    public Game getGame(){return this.game;}
}
