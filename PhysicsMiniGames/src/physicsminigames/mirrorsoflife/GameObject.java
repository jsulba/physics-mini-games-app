package physicsminigames.mirrorsoflife;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameObject {

    protected Circle circle;
    protected Rectangle rectangle;
    protected Vector2D velocity;
    protected Vector2D position;
    protected double radius;

    public GameObject(Vector2D position, double radius) {
        this.position = position;
        circle = new Circle(0.0, 0.0, radius);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }

    public GameObject(Vector2D position, double width, double height) {
        this.position = position;
        rectangle = new Rectangle(position.getX(), position.getY(), width, height);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Rectangle getRectangle()
    {
        return rectangle;
    }
    
    
    public Circle getCircle() {
        return circle;
    }

//      
    public void update(double dt) {
        velocity = getVelocity();
        position = position.add(velocity.mult(dt));
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }

    public Vector2D getVelocity() {
        return velocity;
    }

}
