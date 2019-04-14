package physicsminigames.mirrorsoflife;

public class LightPhoton extends GameObject {

    private Vector2D lightSourceVector;
    public LightPhoton(double x, double y) {     
        super(new Vector2D(x, y), 10);
        this.velocity = new Vector2D(0, 0 );
        circle.setFill(AssetManager.getLightPhotonImage());
    }
  
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

}
