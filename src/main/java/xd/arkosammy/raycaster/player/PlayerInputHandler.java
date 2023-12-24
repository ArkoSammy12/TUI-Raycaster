package xd.arkosammy.raycaster.player;

import com.googlecode.lanterna.screen.Screen;
import xd.arkosammy.raycaster.RayCasterGame;

import java.io.IOException;


public class PlayerInputHandler implements Runnable{

    private final Screen screen;

    public PlayerInputHandler(Screen screen){
        this.screen = screen;
    }

    @Override
    public void run(){
        while(true){
            try {
                RayCasterGame.checkInput(this.screen);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

}
