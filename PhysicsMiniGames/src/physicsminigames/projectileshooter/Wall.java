/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.projectileshooter;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author cstuser
 */
public class Wall extends GameObject{
    
    Line lineTop;
    Bounds boundTop;
    
    Line lineBottom;
    Bounds boundBottom;
    
    Line lineLeft;
    Bounds boundLeft;    
    
    Line lineRight;
    Bounds boundRight;
    
    
    
    public Wall(Vector position, double width, double height)
    {        
        super(position, width, height);
        
        //Setting the edges of the walls using lines        
        lineTop = new Line(position.getX(), position.getY(), position.getX() + width, position.getY());
        boundTop = lineTop.getBoundsInParent();
       
        lineBottom = new Line(position.getX(), position.getY() + height, position.getX() + width, position.getY() + height);
        boundBottom = lineBottom.getBoundsInParent();
        
        lineLeft = new Line(position.getX(), position.getY(), position.getX(), position.getY() + height);
        boundLeft = lineLeft.getBoundsInParent();
        
        lineRight = new Line(position.getX() + width, position.getY(), position.getX() + width, position.getY() + height);
        boundRight = lineRight.getBoundsInParent();
        

        /*        
        lineRight.setStartY(position.getY());
        lineRight.setEndY(position.getY() + height);
        lineRight.setStartX(position.getX() + width);
        //lineRight.setEndX(position.getX() + width);
        
        
        
        lineLeft.setStartY(position.getY());
        lineLeft.setEndY(position.getY() + height);
        lineLeft.setStartX(position.getX());
        //lineLeft.setEndX(position.getX());
                
        lineTop.setStartX(position.getX());
        lineTop.setEndX(position.getX() + width);        
        lineTop.setStartY(position.getY());        
        //lineTop.setEndY(position.getY());        
        
        lineBottom.setStartX(position.getX());
        lineBottom.setEndX(position.getX() + width);
        lineBottom.setStartY(position.getY());
        //lineBottom.setEndY(position.getY());
        
        */

        
        
        
        //bound = rectangle.getBoundsInParent();
        //rectangle.setFill(Color.BLACK);
    }
    
    public Bounds getTopBound() {
       return boundTop;
    }
    
    public Bounds getBottomBound() {
       return boundBottom;
    }
    
    public Bounds getLeftBound() {
       return boundLeft;
    }
    
    public Bounds getRightBound() {
       return boundRight;
    }

    /*
    public void setBounds(Bounds b){
        this.bound = b;
    }
    */
}
