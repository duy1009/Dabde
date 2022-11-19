package utilz;

public interface Constants {
    public static final int CATCHING_TEAM = 1;
    public static final int RUNNING_TEAM = 2;
    public static final int RUNNING = 1;
    public static final int IDLE = 0;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final String PLAYER_1_ATLAS = "/pGGbv.png";
    public static final String PLAYER_2_ATLAS = "/pGGbv.png";
    public static final String LEVEL_ATLAS = "/outside_sprites.png";
    public static final String MAP1 = "/map_rlt.png";
    // length outside_sprites.png
    public static final int OUT_SIDE_HEIGHT = 4;
    public static final int OUT_SIDE_WIDTH = 12;
    public static final int X_OFFSET_PLAYER = 18;
    public static final int Y_OFFSET_PLAYER = 4;

    public static final int[] COLLISION_VALUE = {0, 1, 2, 4, 5, 12, 13, 14, 16, 17, 25, 39};
    public static final int[] BLOCKS_CAN_MOVE = {11};
}
