package gamestates;

import main.Game;
import ui.Button;
import ui.ListTickBox;
import ui.ScrollBar;
import ui.VolumeButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.Constants.UI.URMButtons.URM_SIZE;
import static utilz.Constants.UI.VolumeButtons.*;

public class Setting extends State implements StateMethods{
    private Button[] buttons = new Button[2];
    private ScrollBar volumeButton;
    private BufferedImage bg;
    private ListTickBox listTickBox;

    private int xBGPos, yBGPos, bGWidth, bGHeight;

    public Setting(Game game) {
        super(game);
        loadButton();
        loadBG();
        audioPlayer.playMenuSong();
    }

    private void loadButton() {
        buttons[0] = new Button((int)(Game.GAME_WIDTH*0.1),(int)(0.85*Game.GAME_HEIGHT),URM_SIZE, URM_SIZE, HOME_BUTTON,GameState.MENU );
        buttons[1] = new Button((int)(Game.GAME_WIDTH*0.9),(int)(0.85*Game.GAME_HEIGHT),URM_SIZE, URM_SIZE, BACK_BUTTON,GameState.MENU );
        volumeButton = new ScrollBar((int)(Game.GAME_WIDTH*0.5), (int)(0.68*Game.GAME_HEIGHT), SLIDER_WIDTH, VOLUME_HEIGHT);
        listTickBox = new ListTickBox((int)(Game.GAME_WIDTH*0.52), (int)(Game.GAME_HEIGHT*0.4), 3,TICK_BOX_SIZE, TICK_BOX_DISTANCE, false);
        listTickBox.Tick(1);
    }
    private void loadBG(){
        bg = LoadSave.GetSpriteAtlas(SETTING_BG);
        bGWidth = (int)(bg.getWidth()*Game.SCALE);
        bGHeight = (int)(bg.getHeight()*Game.SCALE);
        xBGPos = (Game.GAME_WIDTH-bGWidth)/2;
        yBGPos = (Game.GAME_HEIGHT-bGHeight)/2;
    }

    @Override
    public void update() {
        for(Button mb: buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bg,xBGPos,yBGPos,bGWidth,bGHeight,null );
        for(Button mb: buttons)
            mb.draw(g);
        volumeButton.draw(g);
        listTickBox.draw(g);

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
        volumeButton.updateBar(e);

        game.getAudioPlayer().setVolume(volumeButton.getValue());
        listTickBox.mousePressed(e);

        switch (listTickBox.getElement()){
            case 0:
                game.setFPS(30);
                break;
            case 1:
                game.setFPS(60);
                break;
            case 2:
                game.setFPS(120);
                break;
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
