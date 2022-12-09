package utilz;

public interface Constants {
    public static final int CATCHING_TEAM = 1;
    public static final int RUNNING_TEAM = 2;

    // Player actions
    public static final int IDLE = 0;
    public static final int MOVING_LEFT = 1;
    public static final int MOVING_RIGHT = 2;
    public static final int JUMP = 3;

    // player speed
    public static final float PLAYER_SPEED_DEFAULT = 1.5f;



    public static final String PLAYER_1_ATLAS = "/pGGbv.png";
    public static final String PLAYER_2_ATLAS = "/pGGbv.png";
    public static final String LEVEL_ATLAS = "/outside_sprites.png";
    public static final String MAP1 = "/Map-long.png";
    public static final String BACKGROUND = "/playing_bg_img.png";
    // length outside_sprites.png
    public static final int OUT_SIDE_HEIGHT = 4;
    public static final int OUT_SIDE_WIDTH = 12;
    public static final int X_OFFSET_PLAYER = 18;
    public static final int Y_OFFSET_PLAYER = 4;

    public static final int[] COLLISION_VALUE = {0, 1, 2, 4, 5, 12, 13, 14, 16, 17, 25, 39};
    public static final int[] BLOCKS_CAN_MOVE = {11};

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
