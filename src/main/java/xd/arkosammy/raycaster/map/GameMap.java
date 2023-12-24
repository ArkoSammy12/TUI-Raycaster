package xd.arkosammy.raycaster.map;

public class GameMap {

    public static final int MAP_LENGTH = 17;

    public static final int MAP_WIDTH = 32;

    private final String map =
                    "################################" +
                    "#..............................#" +
                    "#..............................#" +
                    "#..............................#" +
                    "#..........#####...............#" +
                    "#..............................#" +
                    "#.......#......................#" +
                    "#.......#......................#" +
                    "#.....#####....................#" +
                    "#.......#......................#" +
                    "#.......#......................#" +
                    "#..............................#" +
                    "#..............................#" +
                    "#..............................#" +
                    "#####..........................#" +
                    "#..............................#" +
                    "################################";

    public char getMapElementAt(MapCoordinate mapCoordinate){

        int x = (int) mapCoordinate.getXPos();
        int y = (int) mapCoordinate.getYPos();

        int mapOffset = y * MAP_WIDTH + x;
        return this.map.charAt(mapOffset);

    }

}