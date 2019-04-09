
package physicsminigames.chargecannon;

/**
 *
 * @author thund
 */
public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters
    public double getX(){ return x; }
    public double getY(){ return y; }
    
    public void  set(Vector2D other) { x = other.x; y=other.y;}
    public void  setX(double value){ x=value; }
    public void  setY(double value){ y=value; }
    
    // Operations and Calculations
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D sub(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D mult(double multiplier) {
        return new Vector2D(x * multiplier, y * multiplier);
    }
    
    public double magnitude()
    {
        return Math.sqrt(this.dot(this));
    }
    
    public double dot(Vector2D other){
        return x*other.x + y*other.y;
    }
    
    public void normalize() // unit vector
    {
        this.set(this.mult(1.0/this.magnitude()));
    }
}
