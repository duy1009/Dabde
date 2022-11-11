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
    public static final String MAP1 = "/level_one_data.png";
    // length outside_sprites.png
    public static final int OUT_SIDE_HEIGHT = 4;
    public static final int OUT_SIDE_WIDTH = 12;
    public static final int OUT_SIDE_ONE_BLOCK = 32; // pixel

    public static final int[] COLLISION_VALUE = {1,2, 4, 5, 12, 13, 14, 16, 17, 25, 39};

}
