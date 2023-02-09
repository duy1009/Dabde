package gamestates;

import audio.AudioPlayer;
import main.Game;
import ui.Button;


import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    public State(Game game){
        this.game = game;
    }
    public Game getGame(){
        return game;
    }

    public boolean isIn(MouseEvent e, Button mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
