
package physicsminigames.mirrorsoflife;

public class Mirror extends GameObject{
    private static Vector2D outputVector;
    public Mirror(double x, double y)
    {
        super(new Vector2D(x,y),5, 70);
        rectangle.setFill(AssetManager.getMirrorImage());
    }  
    
    public static Vector2D returnReflectedVector( Vector2D input)
    {
        //reflect the input vector
        outputVector = new Vector2D(-1*input.getX(), input.getY());
        return outputVector;       
    }
   
}
