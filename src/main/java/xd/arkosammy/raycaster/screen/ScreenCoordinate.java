package xd.arkosammy.raycaster.screen;

public class ScreenCoordinate {

    private final int xPos;
    private final int yPos;


    public ScreenCoordinate(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }

    public int getXPos(){
        return this.xPos;
    }

    public int getYPos(){
        return this.yPos;
    }

}
