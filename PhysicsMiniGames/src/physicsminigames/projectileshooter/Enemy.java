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
public class Enemy extends GameObject{   
    
    Bounds bound;
    Vector initialPosition;
    Boolean isBurning = false;
    
    public Enemy(Vector position, Vector velocity, Vector acceleration, double height, double width)
    {
        super(position, velocity, acceleration, height, width);     
        {
            bound = rectangle.getBoundsInParent();
        }
        
        initialPosition = position;
        
        rectangle.setFill(AssetManager.getEnemyPattern());
    }    
    public Bounds getBounds() {return bound;}
    
    public Vector getInitialPosition() {return initialPosition;}
    
    public void setInitialPosition(Vector tempInitialPosition){
        initialPosition = tempInitialPosition;
    }
    
    public Boolean getIsBurning(){
        return isBurning;
    }
    
    public void setIsBurning(Boolean isBurning) {
        this.isBurning = isBurning;
    }
}

