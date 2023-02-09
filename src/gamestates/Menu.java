package gamestates;

import audio.AudioPlayer;
import main.Game;
import ui.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utilz.Constants.*;
import static utilz.Constants.UI.Buttons.B_MENU_HEIGHT;
import static utilz.Constants.UI.Buttons.B_MENU_WIDTH;

public class Menu extends State implements StateMethods{
    private Button[] buttons = new Button[3];
    public Menu(Game game) {
        super(game);
        loadButton();
        audioPlayer.playMenuSong();
    }

    private void loadButton() {

        buttons[0] = new Button(Game.GAME_WIDTH/2,(int)(150*Game.SCALE), B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON1,GameState.PICK );
        buttons[1] = new Button(Game.GAME_WIDTH/2,(int)(220*Game.SCALE),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON2,GameState.OPTIONS );
        buttons[2] = new Button(Game.GAME_WIDTH/2,(int)(290*Game.SCALE),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON3, GameState.QUIT );
    }

    @Override
    public void update() {

        for(Button mb: buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {

        for(Button mb: buttons)
            mb.draw(g);
    }
    @Override
    public void draw2(Graphics g){}
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Button mb: buttons){
            if(isIn(e, mb)){
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(Button mb: buttons){
            if(isIn(e, mb)){
                if(mb.isMousePressed()){
                    mb.applyGameState();
                    game.getPlaying().initClass();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(Button mb: buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(Button mb: buttons)
            mb.setMouseOver(false);
        for(Button mb: buttons)
            if(isIn(e, mb)){
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
