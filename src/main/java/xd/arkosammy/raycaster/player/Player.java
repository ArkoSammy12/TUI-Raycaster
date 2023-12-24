package xd.arkosammy.raycaster.player;

import xd.arkosammy.raycaster.RayCasterGame;
import xd.arkosammy.raycaster.map.GameMap;
import xd.arkosammy.raycaster.map.MapCoordinate;


public class Player {

    private static final double distanceMultiplier = 1.5;
    private MapCoordinate mapCoordinate;
    private double angle;
    private int fov = 100;

    private final PlayerHUD playerHUD;

    public Player(){
        this.mapCoordinate = new MapCoordinate(5, 5);
        this.angle = 0;
        this.playerHUD = new PlayerHUD();
    }

    public double getPlayerAngle(){
        return this.angle;
    }

    public int getFov(){
        return this.fov;
    }

    public PlayerHUD getPlayerHUD(){
        return this.playerHUD;
    }

    public void rotate(double angleDelta){
        this.angle += angleDelta;
    }

    public void changeFov(int delta){
        this.fov = Math.clamp(this.fov + delta, 20, 180);
    }

    public void tick(RayCasterGame game){
        this.playerHUD.updateMapHud(this, game.getGameMap());
    }

    public void movePlayer(Direction direction, GameMap gameMap) {

        double moveDirection = switch (direction) {
            case FORWARDS -> this.angle;
            case BACKWARDS -> this.angle + 180;
            case RIGHT -> this.angle - 90;
            case LEFT -> this.angle + 90;
        };

        double vecX = Math.sin(Math.toRadians(moveDirection)) * distanceMultiplier;
        double vecY = Math.cos(Math.toRadians(moveDirection)) * distanceMultiplier;

        int newX = (int) Math.floor(this.mapCoordinate.getXPos() + vecX);
        int newY = (int) Math.floor(this.mapCoordinate.getYPos() + vecY);

        MapCoordinate newMapCoordinate = new MapCoordinate(newX, newY);
        char element = gameMap.getMapElementAt(newMapCoordinate);

        if (element != '#') {
            this.mapCoordinate = newMapCoordinate;
        }
    }


    public MapCoordinate getMapCoordinate(){
        return this.mapCoordinate;
    }

    public enum Direction {
        RIGHT,
        LEFT,
        FORWARDS,
        BACKWARDS
    }

}
