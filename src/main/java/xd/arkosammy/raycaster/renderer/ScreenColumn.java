package xd.arkosammy.raycaster.renderer;

import com.googlecode.lanterna.TextCharacter;
import xd.arkosammy.raycaster.screen.Drawable;
import xd.arkosammy.raycaster.screen.GameWindow;
import xd.arkosammy.raycaster.screen.ScreenCoordinate;
import xd.arkosammy.raycaster.screen.ScreenElement;

import java.util.ArrayList;
import java.util.List;

public record ScreenColumn(int xScreenCoordinate, int ceiling, int floor) implements Drawable {
    @Override
    public List<ScreenElement> getScreenElements(GameWindow gameWindow) {
        List<ScreenElement> elements = new ArrayList<>();
        char character = getDepthCharacter();

        for (int yScreenCoordinate = 0; yScreenCoordinate < gameWindow.getHeight(); yScreenCoordinate++) {
            ScreenCoordinate screenCoordinate = new ScreenCoordinate(this.xScreenCoordinate, yScreenCoordinate);
            if (yScreenCoordinate > ceiling && yScreenCoordinate < floor) {
                elements.add(new ScreenElement(screenCoordinate, new TextCharacter(character)));
            } else {
                elements.add(new ScreenElement(screenCoordinate, new TextCharacter(' ')));
            }
        }

        return elements;
    }

    private char getDepthCharacter() {
        char[] characters = {'.','░', '▒', '▓', '█'};
        int maxDepth = floor - ceiling;
        int index = Math.clamp((int) ((double) maxDepth / (characters.length - 1)), 0, 4);
        return characters[index];
    }

}

