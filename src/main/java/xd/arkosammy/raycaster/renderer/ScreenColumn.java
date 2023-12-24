package xd.arkosammy.raycaster.renderer;

import com.googlecode.lanterna.TextCharacter;
import xd.arkosammy.raycaster.screen.Drawable;
import xd.arkosammy.raycaster.screen.GameWindow;
import xd.arkosammy.raycaster.screen.ScreenCoordinate;
import xd.arkosammy.raycaster.screen.ScreenElement;

import java.util.ArrayList;
import java.util.List;

public record ScreenColumn(int xScreenCoordinate, int ceiling, int floor, double distance) implements Drawable {
    @Override
    public List<ScreenElement> getScreenElements(GameWindow gameWindow) {
        List<ScreenElement> elements = new ArrayList<>();


        for (int yScreenCoordinate = 0; yScreenCoordinate < gameWindow.getHeight(); yScreenCoordinate++) {
            ScreenCoordinate screenCoordinate = new ScreenCoordinate(this.xScreenCoordinate, yScreenCoordinate);
            if (yScreenCoordinate > ceiling && yScreenCoordinate < floor) {
                char character = getWallCharacter(gameWindow);
                elements.add(new ScreenElement(screenCoordinate, new TextCharacter(character)));
            } else if (yScreenCoordinate > floor) {
                char character = getFloorCharacter(yScreenCoordinate, gameWindow);
                elements.add(new ScreenElement(screenCoordinate, new TextCharacter(character)));
            } else {
                elements.add(new ScreenElement(screenCoordinate, new TextCharacter(' ')));
            }
        }

        return elements;
    }

    private char getWallCharacter(GameWindow window) {
        char[] characters = {' ','░', '▒', '▓', '█'};
        int renderDistance = window.getViewDistance();

        if(distance <= (double) renderDistance / 4){
            return characters[4];
        } else if (distance < (double) renderDistance / 3){
            return characters[3];
        } else if (distance < (double) renderDistance / 2){
            return characters[2];
        } else if (distance < (double) renderDistance){
            return characters[1];
        } else {
            return characters[0];
        }

    }

    private char getFloorCharacter(int yScreenCoordinate, GameWindow window){
        char[] characters = {' ','-', '.', 'X', '#'};
        double floorDepth = 1 - (double) (yScreenCoordinate - window.getHeight() / 2) / ((double) window.getHeight() / 2);

        if(floorDepth < 0.25){
            return characters[4];
        } else if (floorDepth < 0.5){
            return characters[3];
        } else if (floorDepth < 0.75){
            return characters[2];
        } else if (floorDepth < 0.9){
            return characters[1];
        } else {
            return characters[0];
        }

    }

}

