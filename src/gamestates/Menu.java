package gamestates;

import main.Game;
import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.Constants.UI.Buttons.B_MENU_HEIGHT;
import static utilz.Constants.UI.Buttons.B_MENU_WIDTH;

public class Menu extends State implements StateMethods{
    private Button[] buttons = new Button[3];
    private BufferedImage bgMenu, dabde;
    private int xOffsetBg = 0;
    private int BG_WIDTH;
    private int BG_HEIGHT;
    private final int BG_SPEED_DEFAULT = 1;
    private int bgSpeed=BG_SPEED_DEFAULT;
    private float scaleBg;
    private final int TICK_MAX = 8;
    private int tick = 0;
    private int xTitle,yTitle, wTitle, hTitle;


    public Menu(Game game) {
        super(game);
        loadButton();
        loadBg();
    }

    private void loadButton() {

        buttons[0] = new Button(Game.GAME_WIDTH/2,(int)(220*Game.SCALE), B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON1,GameState.PICK );
        buttons[1] = new Button(Game.GAME_WIDTH/2,(int)(290*Game.SCALE),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON2,GameState.OPTIONS );
        buttons[2] = new Button(Game.GAME_WIDTH/2,(int)(360*Game.SCALE),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON3, GameState.QUIT );
    }
    private void loadBg(){
        bgMenu = LoadSave.GetSpriteAtlas(BG_MENU);
        scaleBg = Game.GAME_HEIGHT/(float)(bgMenu.getHeight());
        BG_HEIGHT = Game.GAME_HEIGHT;
        BG_WIDTH = (int)(bgMenu.getWidth()*scaleBg);

        dabde = LoadSave.GetSpriteAtlas(DABDE);
        wTitle = (int)(Game.GAME_WIDTH*0.8);
        hTitle = (int)(Game.GAME_HEIGHT*0.25);
        xTitle = (Game.GAME_WIDTH - wTitle)/2;
        yTitle = (int)(0.05*Game.GAME_HEIGHT);

    }
    private void drawBg(Graphics g){
        g.drawImage(bgMenu, xOffsetBg, 0,BG_WIDTH,BG_HEIGHT,null);
        g.drawImage(dabde, xTitle, yTitle, wTitle, hTitle,null);
    }
    private void updateBGPos(){
        tick++;
        if(tick >= TICK_MAX){
            if(xOffsetBg == 0){
                bgSpeed = -BG_SPEED_DEFAULT;
            }else if(Game.GAME_WIDTH-xOffsetBg >= BG_WIDTH){
                bgSpeed = BG_SPEED_DEFAULT;
            }
            xOffsetBg+=bgSpeed;
            tick = 0;
        }



    }
    @Override
    public void update() {
        for(Button mb: buttons)
            mb.update();
        updateBGPos();
    }


    @Override
    public void draw(Graphics g) {
        drawBg(g);
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
