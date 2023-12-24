package xd.arkosammy.raycaster.map;

public class MapCoordinate {

    private final double xPos;
    private final double yPos;

    public MapCoordinate(double x, double y){
        this.xPos = Math.clamp(x, 0, GameMap.MAP_WIDTH);
        this.yPos = Math.clamp(y, 0, GameMap.MAP_LENGTH);
    }

    public double getXPos(){
        return this.xPos;
    }

    public double getYPos(){
        return this.yPos;
    }

}
