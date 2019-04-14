package physicsminigames.mirrorsoflife;

import javafx.geometry.Point2D;



public class LightTarget extends GameObject{
     
    public LightTarget(double x, double y)
    {
        super(new Vector2D(x,y),20);
        circle.setFill(AssetManager.getLightTargetImage());
    }   
    
    
    
    
    
    
}