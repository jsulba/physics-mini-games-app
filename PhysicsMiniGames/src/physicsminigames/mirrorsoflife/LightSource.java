package physicsminigames.mirrorsoflife;

import javafx.geometry.Point2D;



public class LightSource extends GameObject{
     
    public LightSource(double x, double y)
    {
        super(new Vector2D(x,y),20);
        circle.setFill(AssetManager.getLightSourceImage());
    }  
   
    
    
}
