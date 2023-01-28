package gamestates;

import characters.Fighter;
import characters.Pirate;
import main.Game;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import characters.Character;
import utilz.Constants;

import static utilz.Constants.PlayerCharacter.FIGHTER;
import static utilz.Constants.PlayerCharacter.PIRATE;

public class CharacterPicker extends State implements StateMethods{
    private MenuButton[] buttons = new MenuButton[3];
    Character[] player;
    private int p1=0, p2=0, pTemp = 0;

    public CharacterPicker(Game game) {
        super(game);
        loadButton();
        player = game.getPlaying().getPlayer();
        audioPlayer.playMenuSong();
    }

    private void loadButton() {
        buttons[0] = new MenuButton((int)(Game.GAME_WIDTH/2 - 150*Game.SCALE),(int)(0.7*Game.GAME_HEIGHT),4,GameState.PICK);
        buttons[1] = new MenuButton((int)(Game.GAME_WIDTH/2 + 150*Game.SCALE),(int)(0.7*Game.GAME_HEIGHT),5,GameState.PICK);
        buttons[2] = new MenuButton((int)(Game.GAME_WIDTH/2 ),(int)(0.85*Game.GAME_HEIGHT),6,GameState.PLAYING);
    }
    private void initPlayer(){
        int[] keyBroad_player_1 = {
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_G,
                KeyEvent.VK_T,
                KeyEvent.VK_Y,
                KeyEvent.VK_U};
        int[] keyBroad_player_2 = {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_NUMPAD1,
                KeyEvent.VK_NUMPAD4,
                KeyEvent.VK_NUMPAD5,
                KeyEvent.VK_NUMPAD6};

        player[0] = pick(p1);
        player[1] = pick(p2);

        player[0].setParameter(1000f,10f, 0, keyBroad_player_1);
        player[1].setParameter(450f,100f, 1,keyBroad_player_2);
//        player[0] = new Pirate(1000f,10f, 0,keyBroad_player_1, player, game.getPlaying().getObj());
//        player[1] = new Fighter(450f,100f, 1,keyBroad_player_2, player);
        player[1].setStatusBarFlip(true);
    }
    private Character pick(int character){
        Character chr;
        switch (character){
            case FIGHTER:
                chr = new Fighter(player);
                break;
            case PIRATE:
                chr = new Pirate(player, game.getPlaying().getObj());
                break;
            default:
                chr = new Fighter(player);
                break;
        }
        return chr;
    }
    @Override
    public void update() {
        for(MenuButton mb: buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        for(MenuButton mb: buttons)
            mb.draw(g);
    }
    @Override
    public void draw2(Graphics g){}
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb: buttons){
            if(isIn(e, mb)){
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        for(MenuButton mb: buttons){
//            if(isIn(e, mb)){
//                if(mb.isMousePressed()){
//                    mb.applyGameState();
//                }
//                break;
//            }
//        }
        if(isIn(e, buttons[0])) {
            if (buttons[0].isMousePressed()) {
                p1 = pTemp;
            }
        }
        if(isIn(e, buttons[1])) {
            if (buttons[1].isMousePressed()) {
                p2 = pTemp;
            }
        }

        if(isIn(e, buttons[2])) {
            if (buttons[2].isMousePressed()) {
                initPlayer();
//                player
                buttons[2].applyGameState();
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb: buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb: buttons)
            mb.setMouseOver(false);
        for(MenuButton mb: buttons)
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
