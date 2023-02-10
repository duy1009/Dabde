package utilz;

import gamestates.GameState;
import main.Game;

public interface Constants {

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_MENU_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_MENU_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }

    public class PlayerCharacter{
        public static final int FIGHTER = 0;
        public static final int PIRATE = 1;

    }



    // player speed
    public static final float PLAYER_SPEED_DEFAULT = 1.5f;
    public static final int ANI_SPEED_DEFAULT = 15;
    public static final int TICK_BOX_SIZE = 51;
    public static final int TICK_BOX_DISTANCE = 21;

    public static final String PLAYER_1_ATLAS = "/player_1_4.png";
    public static final String PLAYER_2_ATLAS = "/player_2_3.png";
    public static final String LEVEL_ATLAS = "/outside_sprites_v4.png";
    public static final String MAP1 = "/Map2.png";
    public static final String BACKGROUND = "/bg_2.png";
    public static final String STATUS_BAR = "/health_power_bar.png";
    public static final String TRAP_ATLAS = "/Bomb.png";
    public static final String MENU_BUTTON = "/button_atlasv2.png";
    public static final String MENU_BUTTON1 = "/buttons/b1.png";
    public static final String MENU_BUTTON2 = "/buttons/b2.png";
    public static final String MENU_BUTTON3 = "/buttons/b3.png";
    public static final String MENU_BUTTON4 = "/buttons/b4.png";
    public static final String MENU_BUTTON5 = "/buttons/b5.png";
    public static final String MENU_BUTTON6 = "/buttons/b6.png";

    public static final String URM_BUTTON = "/urm_buttons.png";
    public static final String HOME_BUTTON = "/buttons/urm_b3.png";
    public static final String BACK_BUTTON = "/buttons/urm_b2.png";
    public static final String VOLUME_BUTTON = "/buttons/volume_buttons.png";
    public static final String VOLUME_BAR = "/buttons/volume_bar.png";
    public static final String SETTING_BG = "/bg_optionv2.jpg";
    public static final String TABLE_PICK = "/choose_charactersv2.png";
    public static final String TICK_BUTTON = "/buttons/tick.png";
    public static final String BOX_BUTTON = "/buttons/box.png";
    public static final String BG_FIGHTER = "/fighterBg.png";
    public static final String BG_PIRATE = "/pirateBg.png";
    public static final String BG_MENU = "/Bg_menu.jpg";
    public static final String DABDE = "/Dabde.png";
    public static final String P1_IMAGE = "/player1.png";
    public static final String P2_IMAGE = "/Player2.png";
    public static final String WIN_IMAGE = "/win.png";
    public static final String LOSE_IMAGE = "/lose.png";
    // length outside_sprites.png
    public static final int OUT_SIDE_HEIGHT = 4;
    public static final int OUT_SIDE_WIDTH = 16;


    public static final int[] BLOCKS_CAN_MOVE = {10,11,26,27,63};

//    Socket packet
    public static final int SC_START_GAME = 1;
    public static final int SC_KEY_INPUT = 2;
    public static final int SC_POS = 3;
    public static final int SC_IS_DEAD = 4;

    public static final int SC_LENGTH_START_GAME = 2;
    public static final int SC_LENGTH_KEY_INPUT= 5;
    public static final int SC_LENGTH_POS= 3;
    public static final int SC_LENGTH_IS_DEAD= 2;

//    Socket start game (index of packet)
    public static final int ST_MAIN_PLAYER = 0;

//    Socket key input (index of packet)
    public static final int IP_UP = 1;
    public static final int IP_DOWN = 2;
    public static final int IP_LEFT = 3;
    public static final int IP_RIGHT = 4;

    public static final int IP_NOT_KEY = 0;
    public static final int IP_PRESSED = 1;
    public static final int IP_RELEASED = 0;
    public static final int IP_TYPE = 3;
}
