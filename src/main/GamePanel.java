package main;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;

import client.Send;
import inputs.KeybroadInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game){
        setPanelSize();
        this.game = game;
        MouseInputs mouse= new MouseInputs(this);
        addKeyListener(new KeybroadInputs(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    public GamePanel(Game game, Send send) throws IOException {

        setPanelSize();
        this.game = game;
        MouseInputs mouse= new MouseInputs(this);
        addKeyListener(new KeybroadInputs(this, send));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    public void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }
    public Game getGame(){return this.game;}

}
