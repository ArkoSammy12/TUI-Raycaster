package xd.arkosammy.raycaster.player;

import com.googlecode.lanterna.TextCharacter;
import xd.arkosammy.raycaster.map.GameMap;
import xd.arkosammy.raycaster.map.MapCoordinate;
import xd.arkosammy.raycaster.screen.Drawable;
import xd.arkosammy.raycaster.screen.GameWindow;
import xd.arkosammy.raycaster.screen.ScreenCoordinate;
import xd.arkosammy.raycaster.screen.ScreenElement;

import java.util.ArrayList;
import java.util.List;

public class PlayerHUD implements Drawable {

    private final ScreenCoordinate screenCoordinate = new ScreenCoordinate(0, 0);
    private final char[][] mapHud = new char[GameMap.MAP_LENGTH][GameMap.MAP_WIDTH];

    @Override
    public List<ScreenElement> getScreenElements(GameWindow gameWindow) {

        List<ScreenElement> screenElements = new ArrayList<>();

        for(int i = this.screenCoordinate.getYPos(); i < GameMap.MAP_LENGTH + this.screenCoordinate.getYPos(); i++) {
            for (int j = this.screenCoordinate.getXPos(); j < GameMap.MAP_WIDTH + this.screenCoordinate.getXPos(); j++) {
                screenElements.add(new ScreenElement(new ScreenCoordinate(j, i), new TextCharacter(mapHud[i][j])));
            }
        }
        return screenElements;
    }

    public void updateMapHud(Player player, GameMap map){

        for(int i = 0; i < GameMap.MAP_WIDTH; i++){
            for(int j = 0; j < GameMap.MAP_LENGTH; j++){

                mapHud[j][i] = map.getMapElementAt(new MapCoordinate(i, j));

            }

        }
        int playerX = (int) player.getMapCoordinate().getXPos();
        int playerY = (int) player.getMapCoordinate().getYPos();
        mapHud[playerY][playerX] = 'P';
    }

}
