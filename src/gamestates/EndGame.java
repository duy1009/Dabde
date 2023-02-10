package gamestates;

import main.Game;
import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class EndGame extends State implements StateMethods{
    private BufferedImage p1,p2;
    private BufferedImage win,lose;
    private boolean p1Win = true;
    private final int X_OFFSET_LABEL = (int)(90*Game.SCALE);
    private final int Y_OFFSET_LABEL = (int)(20*Game.SCALE);
    private final int X_OFFSET = (int)(90*Game.SCALE);
    private final int Y_OFFSET = (int)(150*Game.SCALE);
    private Button home;

    public EndGame(Game game){
        super(game);
        loadImg();
        initButton();
    }
    private void loadImg(){
        p1 = LoadSave.GetSpriteAtlas(P1_IMAGE);
        p2 = LoadSave.GetSpriteAtlas(P2_IMAGE);
        win = LoadSave.GetSpriteAtlas(WIN_IMAGE);
        lose = LoadSave.GetSpriteAtlas(LOSE_IMAGE);
    }
    private void initButton(){
        home = new ui.Button((int)(Game.GAME_WIDTH*0.1),(int)(0.85*Game.GAME_HEIGHT),URM_SIZE, URM_SIZE, HOME_BUTTON,GameState.MENU );

    }
    @Override
    public void update() {
        home.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(p1, X_OFFSET_LABEL, Y_OFFSET_LABEL, (int)(500*Game.SCALE), (int)(120*Game.SCALE), null);
        if (p1Win)
            g.drawImage(win, X_OFFSET, Y_OFFSET, (int)(500*Game.SCALE), (int)(270*Game.SCALE), null);
        else
            g.drawImage(lose, X_OFFSET, Y_OFFSET, (int)(500*Game.SCALE), (int)(270*Game.SCALE), null);
        home.draw(g);
    }

    @Override
    public void draw2(Graphics g) {
        g.drawImage(p2, X_OFFSET_LABEL, Y_OFFSET_LABEL, (int)(500*Game.SCALE), (int)(120*Game.SCALE), null);
        if (p1Win)
            g.drawImage(lose, X_OFFSET, Y_OFFSET, (int)(500*Game.SCALE), (int)(270*Game.SCALE), null);
        else
            g.drawImage(win, X_OFFSET, Y_OFFSET, (int)(500*Game.SCALE), (int)(270*Game.SCALE), null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e, home)){
            home.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e, home)){
            if(home.isMousePressed())
                home.applyGameState();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        home.setMouseOver(false);
        if(isIn(e, home)){
            home.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
