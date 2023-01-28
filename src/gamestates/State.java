package gamestates;

import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    protected AudioPlayer audioPlayer;
    public State(Game game){
        this.game = game;
        audioPlayer = game.getAudioPlayer();
    }
    public Game getGame(){
        return game;
    }
    public boolean isIn(MouseEvent e, MenuButton mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
