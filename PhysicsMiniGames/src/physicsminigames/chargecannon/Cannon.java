/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.chargecannon;

/**
 *
 * @author thund
 */
public class Cannon extends RectangleGameObject{
    public Cannon(Vector2D position){
        super(position, 48.0, 48.0);
        rectangle.setFill(AssetManager.getChargeCannonIdleImage());
    }
    
    public void setFiringImage(){
        rectangle.setFill(AssetManager.getChargeCannonShootingImage());
    }
    
     public void setIdleImage(){
        rectangle.setFill(AssetManager.getChargeCannonIdleImage());
    }
}
