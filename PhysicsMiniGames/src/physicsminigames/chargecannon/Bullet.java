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
public class Bullet extends CircleGameObject{
    
    private double charge;
    private double mass;
    
    
    public Bullet(Vector2D position, Vector2D velocity, double charge, double mass){
        super(position, velocity, new Vector2D(0.0,0.0), 8.0);  // initial acceleration 0. 
        
        if (charge < 0.0){
            circle.setFill(AssetManager.getBulletImage(2));  // set image to tegan bullet
        }
        else if(charge > 0.0){
            circle.setFill(AssetManager.getBulletImage(1));  // set image to posit bullet
        }
        else{
            circle.setFill(AssetManager.getBulletImage(0));  // set image to neutra bullet
        }
        this.charge = charge;
        this.mass = mass;
    }
    
    public double getCharge(){
        return charge;
    }
    
    public double getMass(){
        return mass;
    }
    
    public boolean isDestroyed(){
        return destroyed;
    }
    
    public void destroy(){
        this.setVelocity(new Vector2D(0, 0));
        this.setAcceleration(new Vector2D(0, 0));
        this.removeFromGame(0);
    }
    
    public boolean isNeutralized(){
        return neutralized;
    }
    
    public void neutralize(){
        this.setAcceleration(new Vector2D(0, 0));
        this.getCircle().setFill(AssetManager.getBulletImage(0));
        this.charge = 0.0;
    }
}
