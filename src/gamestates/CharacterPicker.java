package gamestates;

import characters.Fighter;
import characters.Pirate;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import characters.Character;
import utilz.HelpMethods;
import utilz.LoadSave;

import ui.Button;
import static utilz.Constants.*;
import static utilz.Constants.PlayerCharacter.FIGHTER;
import static utilz.Constants.PlayerCharacter.PIRATE;
import static utilz.Constants.UI.Buttons.B_MENU_HEIGHT;
import static utilz.Constants.UI.Buttons.B_MENU_WIDTH;
import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class CharacterPicker extends State implements StateMethods{
    private Button[] buttons = new Button[5];
    private BufferedImage pickTable;
    private BufferedImage[] BG_chr ;
    private BufferedImage bgP1, bgP2;

    private int TITLE_TABLE = (int)(50*Game.SCALE);
    private int TABLE_WIDTH = 2;
    private int TABLE_HEIGHT = 1;
    private int TABLE_SIZE_W= TABLE_WIDTH*TITLE_TABLE;
    private int TABLE_SIZE_H = TABLE_HEIGHT*TITLE_TABLE;
    private int X_OFFSET_TABLE = (Game.GAME_WIDTH - TABLE_SIZE_W)/2;
    private int Y_OFFSET_TABLE = (int)(0.3*Game.GAME_HEIGHT);

    Character[] player;
    private int p1=0, p2=0, pTemp = 0;
    private int xPick = 0, yPick = 0;

    public CharacterPicker(Game game) {
        super(game);
        loadButton();
        loadBGCharacter();
        pickTable = LoadSave.GetSpriteAtlas(TABLE_PICK);
        player = game.getPlaying().getPlayer();
    }

    private void loadButton() {
        buttons[0] = new Button((int)(Game.GAME_WIDTH/2 - 150*Game.SCALE),(int)(0.7*Game.GAME_HEIGHT),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON4,GameState.PICK);
        buttons[1] = new Button((int)(Game.GAME_WIDTH/2 + 150*Game.SCALE),(int)(0.7*Game.GAME_HEIGHT),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON5,GameState.PICK);
        buttons[2] = new Button((int)(Game.GAME_WIDTH/2 ),(int)(0.85*Game.GAME_HEIGHT),B_MENU_WIDTH, B_MENU_HEIGHT, MENU_BUTTON6,GameState.PLAYING);

        buttons[3] = new Button((int)(Game.GAME_WIDTH*0.1),(int)(0.85*Game.GAME_HEIGHT),URM_SIZE, URM_SIZE, HOME_BUTTON,GameState.MENU );
        buttons[4] = new Button((int)(Game.GAME_WIDTH*0.9),(int)(0.85*Game.GAME_HEIGHT),URM_SIZE, URM_SIZE, BACK_BUTTON,GameState.MENU );

    }
    private void initPlayer(){
        int[] keyBroad_player_1 = {
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_C,
                KeyEvent.VK_V,
                KeyEvent.VK_B,
                KeyEvent.VK_N};
        int[] keyBroad_player_2 = {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_U,
                KeyEvent.VK_I,
                KeyEvent.VK_O,
                KeyEvent.VK_P};

        player[0] = pick(p1);
        player[1] = pick(p2);

        player[0].setParameter( 450f,100f,0, keyBroad_player_1);
        player[1].setParameter(1000f,10f, 1,keyBroad_player_2);

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
    private void loadBGCharacter(){
        BG_chr = new BufferedImage[2];
        BG_chr[FIGHTER] = LoadSave.GetSpriteAtlas(BG_FIGHTER);
        BG_chr[PIRATE] = LoadSave.GetSpriteAtlas(BG_PIRATE);

        bgP1 = BG_chr[FIGHTER];
        bgP2 = BG_chr[FIGHTER];
        System.out.println(bgP1);
    }
    private boolean isInTable(int x, int y){
        if(x>X_OFFSET_TABLE && x <X_OFFSET_TABLE + TABLE_SIZE_W)
            if(y>Y_OFFSET_TABLE && y< Y_OFFSET_TABLE + TABLE_SIZE_H)
                return true;
        return false;
    }

    @Override
    public void update() {
        for(Button mb: buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bgP1, 0,0, (int)(Game.GAME_WIDTH*0.4), Game.GAME_HEIGHT,null);
        g.drawImage(bgP2, Game.GAME_WIDTH,0, (int)(Game.GAME_WIDTH*-0.4), Game.GAME_HEIGHT,null);
        for(Button mb: buttons)
            mb.draw(g);
        g.drawImage(pickTable, X_OFFSET_TABLE, Y_OFFSET_TABLE, TABLE_SIZE_W, TABLE_SIZE_H, null);
        g.setColor(Color.RED);
//        g.drawRect(xPick*TITLE_TABLE + X_OFFSET_TABLE, yPick*TITLE_TABLE + Y_OFFSET_TABLE, TITLE_TABLE,TITLE_TABLE);

        HelpMethods.drawRect(g,xPick*TITLE_TABLE + X_OFFSET_TABLE, yPick*TITLE_TABLE + Y_OFFSET_TABLE, TITLE_TABLE, TITLE_TABLE, 3);
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
        int x = e.getX();
        int y = e.getY();
        if(isInTable(x,y)){
            xPick = (x - X_OFFSET_TABLE)/TITLE_TABLE;
            yPick = (y - Y_OFFSET_TABLE)/TITLE_TABLE;
            pTemp = yPick*TABLE_SIZE_W + xPick;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(isIn(e, buttons[0])) {
            if (buttons[0].isMousePressed()) {
                p1 = pTemp;
                bgP1 = BG_chr[pTemp];
            }
        }
        if(isIn(e, buttons[1])) {
            if (buttons[1].isMousePressed()) {
                p2 = pTemp;
                bgP2 = BG_chr[pTemp];
            }
        }

        if(isIn(e, buttons[2])) {
            if (buttons[2].isMousePressed()) {
                initPlayer();
//                player
                buttons[2].applyGameState();
            }
        }
        for(int i = 3;i<5;i++){
            if(isIn(e, buttons[i])) {
                if (buttons[i].isMousePressed()) {
                    buttons[i].applyGameState();
                }
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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
