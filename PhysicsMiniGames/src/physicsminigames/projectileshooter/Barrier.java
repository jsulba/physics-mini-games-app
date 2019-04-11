/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.projectileshooter;

import javafx.geometry.Bounds;

/**
 *
 * @author cstuser
 */
public class Barrier extends GameObject {
    Bounds bound;
    public Barrier(Vector position, double width, double height, String type)
    {
        super(position, height, width, type);
        {
            bound = rectangle.getBoundsInParent();
        }
        
        if(type.equalsIgnoreCase("fire"));{
            rectangle.setFill(AssetManager.getBarrierFire());
        }
        
        if(type.equalsIgnoreCase("icefull"))
        {
            rectangle.setFill(AssetManager.getBarrierIce2());
        }
        
        if(type.equalsIgnoreCase("iceHit"))
        {
            rectangle.setFill(AssetManager.getBarrierIce1());
        }
    }
    public Bounds getBounds(){return bound;}
}
