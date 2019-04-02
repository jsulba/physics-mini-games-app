/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.chargecannon;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author thund
 */
public class RectangleGameObject {
    protected Rectangle rectangle;
    private Vector2D position;
    private boolean stop = false;
    
    public RectangleGameObject(Vector2D position,double height, double width)
    {
        this.position = position;
        
        rectangle = new Rectangle(height, width);
        rectangle.setX(position.getX());  // upper left corner is the point defined by the set function
        rectangle.setY(position.getY());
    }
    
    public void removeFromGame(int opacity){
        rectangle.setOpacity(opacity);
        stop = true;
    }
    
    public void revive(){
        stop = false;
    }
    
    public boolean isStopped(){
        return stop;
    }
    
    public double getWidth(){
        return rectangle.getWidth();
    }
    
    public double getHeight(){
        return rectangle.getHeight();
    }
    
    public Vector2D getCenter(){
        Vector2D corner = this.getPosition();
        return new Vector2D(corner.getX()+this.getWidth()/2, corner.getY()+this.getHeight()/2);
    }
    
    public Rectangle getRectangle()
    {
        return rectangle;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
    
}
