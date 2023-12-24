package xd.arkosammy.raycaster;

import xd.arkosammy.raycaster.player.PlayerInputHandler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] ags) throws IOException {

        RayCasterGame game = RayCasterGame.getInstance();
        PlayerInputHandler playerInputHandler = new PlayerInputHandler(game.getGameWindow().getTerminalScreen());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(playerInputHandler);
        game.startLoop();
        executorService.shutdownNow();

    }

}
