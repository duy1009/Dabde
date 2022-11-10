package main;

import characters.Character;
import inputs.KeybroadInputs;
import inputs.MouseInputs;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GamePanel extends JPanel {
    private Game game;

    private int aniTick = 0, aniIndex = 0, aniSpeed = 15;   // frame update an animation
    public GamePanel(Game game) throws IOException {
        setPanelSize();
        this.game = game;
        MouseInputs mouse= new MouseInputs(this);
        addKeyListener(new KeybroadInputs(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }



    public void paintComponent(Graphics g){

        super.paintComponent(g);
        game.render(g);

    }

    public void setPanelSize(){
        Dimension size = new Dimension(960,640);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }
    public Game getGame(){return this.game;}

}
