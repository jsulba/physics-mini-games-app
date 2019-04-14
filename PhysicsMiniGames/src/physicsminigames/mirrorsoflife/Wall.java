package physicsminigames.mirrorsoflife;

import javafx.geometry.Point2D;

public class Wall extends GameObject{
         
    private Vector2D vectorOutput;
    public Wall(double x, double y)
    {
        super(new Vector2D(x,y), 5, 50);
        rectangle.setFill(AssetManager.getWallImage());
    }   
    
    //wall stops the light photon
    //vector's movement and makes it restart from the source
}