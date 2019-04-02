/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.chargecannon;

import javafx.scene.shape.Ellipse;  // use Bounds? 

/**
 *
 * @author thund
 */
public class GroundGate extends RectangleGameObject{
    
    public GroundGate(Vector2D position){
        super(position, 40.0, 70.0);
        rectangle.setFill(AssetManager.getGroundGateImage());
    }
    
    
    public GroundGate(Vector2D position, double height, double width){
        super(position, height, width);
        rectangle.setFill(AssetManager.getGroundGateImage());
    }
}
