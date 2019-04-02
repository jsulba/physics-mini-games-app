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
public class Generator extends RectangleGameObject{
    public Generator(Vector2D position){
        super(position, 48.0, 48.0);
        rectangle.setFill(AssetManager.getGeneratorImage());
    }
}
