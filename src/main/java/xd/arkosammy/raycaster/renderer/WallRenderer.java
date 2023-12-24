package xd.arkosammy.raycaster.renderer;

import xd.arkosammy.raycaster.RayCasterGame;
import xd.arkosammy.raycaster.map.GameMap;
import xd.arkosammy.raycaster.map.MapCoordinate;
import xd.arkosammy.raycaster.player.Player;
import xd.arkosammy.raycaster.screen.Drawable;
import xd.arkosammy.raycaster.screen.GameWindow;
import xd.arkosammy.raycaster.screen.ScreenElement;

import java.util.ArrayList;
import java.util.List;

public class WallRenderer implements Drawable {

    private static final int MAX_DEPTH = 30;

    private final List<ScreenColumn> screenColumns = new ArrayList<>();

    public void clearWallElements(){
        this.screenColumns.clear();
    }

    public void createWallElements(RayCasterGame game){

        Player player = game.getPlayer();
        GameMap map = game.getGameMap();

        double fov = player.getFov();
        double playerAngle = player.getPlayerAngle();

        for(int x = 0; x < game.getGameWindow().getWidth(); x++){

             double rayAngle = (playerAngle - fov / 2) + ((double) x / game.getGameWindow().getWidth()) * fov;
             double distanceToWall = 0;
             boolean hitWill = false;
             double vecX = Math.sin(Math.toRadians(rayAngle));
             double vecY = Math.cos(Math.toRadians(rayAngle));

             while(!hitWill && distanceToWall < MAX_DEPTH){

                 distanceToWall += 0.01;

                 int testX = (int)(player.getMapCoordinate().getXPos() + vecX * distanceToWall);
                 int testY = (int)(player.getMapCoordinate().getYPos() + vecY * distanceToWall);

                 if(testX < 0 || testX >= GameMap.MAP_WIDTH || testY < 0 || testY >= GameMap.MAP_LENGTH){

                     hitWill = true;
                     distanceToWall = MAX_DEPTH;

                 } else {

                     char element = map.getMapElementAt(new MapCoordinate(testX, testY));
                     if(element == '#'){

                         hitWill = true;
                     }

                 }

             }

            //Fix fisheye distortion
            distanceToWall *= Math.cos(Math.toRadians(playerAngle - rayAngle));
            int ceiling = (int) ((game.getGameWindow().getHeight() / 2) - game.getGameWindow().getHeight() / distanceToWall);
            int floor = game.getGameWindow().getHeight() - ceiling;
            this.screenColumns.add(new ScreenColumn(x, ceiling, floor));

        }

    }

    @Override
    public List<ScreenElement> getScreenElements(GameWindow gameWindow){
        return this.screenColumns.stream().flatMap(screenColumn -> screenColumn.getScreenElements(gameWindow).stream()).toList();
    }

}
