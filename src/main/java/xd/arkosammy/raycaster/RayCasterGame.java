package xd.arkosammy.raycaster;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import xd.arkosammy.raycaster.map.GameMap;
import xd.arkosammy.raycaster.player.Player;
import xd.arkosammy.raycaster.renderer.WallRenderer;
import xd.arkosammy.raycaster.screen.GameWindow;

import java.io.IOException;

public class RayCasterGame {

    private static RayCasterGame instance;

    private final GameMap gameMap;

    private final GameWindow gameWindow;

    private final Player player;

    private final WallRenderer wallRenderer;

    private boolean running = true;

    public static RayCasterGame getInstance() throws IOException {
        if(instance == null){
            instance = new RayCasterGame();
        }
        return instance;
    }

    private RayCasterGame() throws IOException {
        this.gameMap = new GameMap();
        this.gameWindow = new GameWindow();
        this.player = new Player();
        this.wallRenderer = new WallRenderer();
    }

    public GameMap getGameMap(){
        return this.gameMap;
    }

    public GameWindow getGameWindow(){
        return this.gameWindow;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void startLoop() throws IOException {

        while(running){

            this.gameWindow.updateSize();
            this.player.tick(this);
            this.gameWindow.submitHudElement(this.player.getPlayerHUD());
            this.wallRenderer.createScreenColumns(this);
            this.gameWindow.submitWorldElement(this.wallRenderer);
            this.gameWindow.refreshWindow(this);
            this.wallRenderer.clearScreenColumns();

        }

    }

    private void terminate() throws IOException {
        this.running = false;
        this.gameWindow.close();
    }

    public static void checkInput(Screen screen) throws IOException {

        KeyStroke keyStroke = screen.pollInput();

        if(keyStroke != null){

            char key = keyStroke.getCharacter();

            Player.Direction direction = switch(key){

              case 'w' -> Player.Direction.FORWARDS;
              case 's' -> Player.Direction.BACKWARDS;
              case 'd' -> Player.Direction.LEFT;
              case 'a' -> Player.Direction.RIGHT;
              default -> null;

            };

            if(direction != null){
                RayCasterGame.getInstance().player.movePlayer(direction, RayCasterGame.getInstance().gameMap);
            }

            switch(key){

                case 'e' -> RayCasterGame.getInstance().player.rotate(3);
                case 'q' -> RayCasterGame.getInstance().player.rotate(-3);
                case '=' -> RayCasterGame.getInstance().player.changeFov(1);
                case '-' -> RayCasterGame.getInstance().player.changeFov(-1);
                case 'p' -> RayCasterGame.getInstance().terminate();
                case 't' -> RayCasterGame.getInstance().getGameWindow().changeViewDistance(1);
                case 'g' -> RayCasterGame.getInstance().getGameWindow().changeViewDistance(-1);

            }

        }

    }

}
