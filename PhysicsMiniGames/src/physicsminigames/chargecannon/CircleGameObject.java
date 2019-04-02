/*
 *Authors: Jose Fernandez et Lin Xiao Zheng
 */
package physicsminigames.chargecannon;

import javafx.scene.shape.Circle;

/**
 *
 * @author thund
 */
public class CircleGameObject {
    protected Circle circle;
    private Vector2D position;  // acts like a Point object, but making it as a Vector2D makes it easier to update. 
    private Vector2D velocity;
    private Vector2D acceleration;
    protected boolean destroyed = false;
    protected boolean neutralized = false;
    private boolean moving = true;
    
    public CircleGameObject(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius)
    {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration; 
        
        circle = new Circle(0.0, 0.0, radius);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }
    
    public void removeFromGame(int opacity){
        circle.setOpacity(opacity);
        destroyed = true;
    }
    
    public Circle getCircle()
    {
        return circle;
    }
    
    public double getRadius(){
        return circle.getRadius();
    }
    
    public boolean isMoving(){
        return moving;
    }
    
    public void update(double dt)
    {
        // Update velocity
        Vector2D frameAcceleration = getAcceleration().mult(dt);  // instead getAcceleration(), it will be the calculated acceleration
        velocity = getVelocity().add(frameAcceleration);
        if(velocity.magnitude() == 0 || position.getX() > 700 || position.getX() < 0){
            moving = false;
        }
        else{
            moving = true;
        }

        // Update position
        position = getPosition().add(getVelocity().mult(dt));
        circle.setCenterX(getPosition().getX());
        circle.setCenterY(getPosition().getY());        
    }
    
    public void update(double dt, double qTest, Vector2D eField, double mass)
    {
        // New acceleration using physics formulas
        Vector2D force = Physics.calcElectricForce(qTest, eField);
        acceleration = Physics.calcAcceleration(force, mass);
        
        // Update velocity
        Vector2D frameAcceleration = acceleration.mult(dt);  
        velocity = getVelocity().add(frameAcceleration);
        if(velocity.magnitude() == 0 || position.getX() > 700 || position.getX() < 0){
            moving = false;
        }
        else{
            moving = true;
        }

        // Update position
        position = getPosition().add(getVelocity().mult(dt));
        circle.setCenterX(getPosition().getX());
        circle.setCenterY(getPosition().getY());        
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }
    
    public void setAcceleration(Vector2D acc) {
        acceleration = acc;
    }  
    
    public void setVelocity(Vector2D v){
        velocity = v;
    }
}
