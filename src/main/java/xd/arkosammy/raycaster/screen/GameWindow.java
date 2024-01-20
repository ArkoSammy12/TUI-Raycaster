package xd.arkosammy.raycaster.screen;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger;
import xd.arkosammy.raycaster.RayCasterGame;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;

public class GameWindow implements AutoCloseable {

    private final Screen terminalScreen;
    private final Set<ScreenElement> worldElements = new LinkedHashSet<>();
    private final Set<ScreenElement> hudElements = new LinkedHashSet<>();
    private int viewDistance = 20;
    private int frames;
    private long lastTime;


    public GameWindow() throws IOException {
        Terminal terminal = new DefaultTerminalFactory(System.out, System.in, Charset.defaultCharset())
                .setInitialTerminalSize(new TerminalSize(104, 100))
                .setTerminalEmulatorFrameAutoCloseTrigger(TerminalEmulatorAutoCloseTrigger.CloseOnEscape)
                .setTerminalEmulatorTitle("Raycaster")
                .setTerminalEmulatorFontConfiguration(AWTTerminalFontConfiguration.newInstance(new Font("Monospaced", Font.BOLD, 1)))
                .setPreferTerminalEmulator(true)
                .createTerminal();

        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
        terminal.setCursorVisible(false);
        terminalScreen = new TerminalScreen(terminal);
        this.terminalScreen.startScreen();
        this.frames = 0;
        this.lastTime = System.nanoTime();
    }

    public void changeViewDistance(int delta){
        this.viewDistance = Math.clamp(this.viewDistance + delta, 5, 40);
    }

    public int getViewDistance(){
        return this.viewDistance;
    }

    public void updateSize() {
        this.terminalScreen.doResizeIfNecessary();
    }

    public Screen getTerminalScreen() {
        return this.terminalScreen;
    }

    public int getWidth() {
        return this.terminalScreen.getTerminalSize().getColumns();
    }

    public int getHeight() {
        return this.terminalScreen.getTerminalSize().getRows();
    }

    private void clearAll() {
        this.hudElements.clear();
        this.worldElements.clear();
    }

    public void submitWorldElement(Drawable drawable) {
        this.worldElements.addAll(drawable.getScreenElements(this));
    }

    public void submitHudElement(Drawable drawable) {
        this.hudElements.addAll(drawable.getScreenElements(this));
    }

    public void refreshWindow(RayCasterGame game) throws IOException {
        TextGraphics stats = this.terminalScreen.newTextGraphics();
        long now = System.nanoTime();
        double elapsedSeconds = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;
        double fps = frames / elapsedSeconds;

        stats.putString(0, 0, String.format("x=%.2f, y=%.2f, angle=%.2f, FOV=%d, viewDistance=%d, FPS=%.2f", game.getPlayer().getMapCoordinate().getXPos(), game.getPlayer().getMapCoordinate().getYPos(),game.getPlayer().getPlayerAngle() % 360, game.getPlayer().getFov(), this.viewDistance, fps));

        for (ScreenElement e : this.worldElements) {
            this.terminalScreen.setCharacter(e.screenCoordinate().getXPos(), e.screenCoordinate().getYPos() + 1, e.graphic());
        }

        //Overlay HUD on top world screen elements
        for (ScreenElement e : this.hudElements) {
            this.terminalScreen.setCharacter(e.screenCoordinate().getXPos(), e.screenCoordinate().getYPos() + 1, e.graphic());
        }

        this.terminalScreen.refresh();
        this.clearAll();
        frames++;
    }

    public void close() throws IOException {
        this.terminalScreen.stopScreen();
        this.terminalScreen.close();
        this.clearAll();
    }

}
