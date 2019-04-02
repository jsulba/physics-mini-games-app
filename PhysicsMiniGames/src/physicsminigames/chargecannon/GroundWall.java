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
public class GroundWall extends RectangleGameObject{
    public GroundWall(Vector2D position){
        super(position, 40.0, 70.0);
        rectangle.setFill(AssetManager.getGroundWallImage());
    }
    public GroundWall(Vector2D position, double height, double width){
        super(position, height, width);
        rectangle.setFill(AssetManager.getGroundWallImage());
    }
}
