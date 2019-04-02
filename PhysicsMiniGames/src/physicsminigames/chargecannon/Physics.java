/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.chargecannon;

import java.util.ArrayList;

/**
 *
 * @author thund
 */
public class Physics {
    private static final double E_CONSTANT = 9e3;
    
    // Electric force and field
    // TODO: turn parameters into lists to be able to calculate the net electric field
    public static Vector2D calcElectricField(double qSource, Vector2D directional){
        double distance = directional.magnitude();
        directional.normalize();
        double electricFieldMagnitude = (E_CONSTANT * qSource)/(Math.pow(distance, 2));
        
        return directional.mult(electricFieldMagnitude);
    }
    
    public static Vector2D calcNetElectricField(ArrayList<Double> sources, ArrayList<Vector2D> directionals){
        ArrayList<Vector2D> eFields = new ArrayList<>();
        for (int i = 0; i < directionals.size(); i++) {  // sources should have same size as directionals ArrayList
            Vector2D currentDirectional = directionals.get(i);
            double distance = currentDirectional.magnitude();
            currentDirectional.normalize();
            double eFieldMagnitude = (E_CONSTANT * sources.get(i))/(Math.pow(distance, 2));
            Vector2D eField = currentDirectional.mult(eFieldMagnitude);
            eFields.add(eField);
        }
        Vector2D netElectricField = new Vector2D(0.0, 0.0);
        for(Vector2D v: eFields){
            netElectricField = netElectricField.add(v);
        }
        return netElectricField;
    }
    
    public static Vector2D calcElectricForce(double qTest, Vector2D electricField){
        return electricField.mult(qTest);
    }
    
    public static Vector2D calcAcceleration(Vector2D eForce, double mass){
        Vector2D acc = eForce.mult(1/mass);
        return acc;
    }
    
    public static double calcKineticEnergy(Vector2D velocity, double mass){
        double speed = velocity.magnitude();
        return (mass*Math.pow(speed, 2))/2;
    }
}

/*
STEPS TO COMPLETE IN TERMS OF PHYSICS:
    -Make a Grid class that has a method that creates a coordinate system grid (use first quadrant only).
    -Make a method that calculates every net electric field vector for each grid region (squares or point vicinities?).
        For this, I'll need to store each field vector in a map according to which region it belongs to, so I might also need
        an object like GridRegion which will have the coordinates and area it covers as attributes. 
    -Complete the update method in CircleGameObject. 

    Theoretically, the bullet will receive an acceleration the very frame after it spawns with its initial velocity.
    So, on that second/third frame, it will have a new position, and I need to use that position to determine which
    electric field vector to attribute to that position. I will most likely need to compare the bullet position with the coordinates
    of each GridRegion in the map until the coordinates of the bullet fall inside the region coordinates of a GridRegion object.
    Then, from that region I get the associated vector, calculate the acceleration imparted on the bullet using its mass, and follow with 
    the other kinematics calculations to update the position for the next frame, and so on the process continues. 

    Assuming the grid works with square regions, as long as the bullet is in a region it will be affected by the same
    field vector and acceleration. Once it crosses over to another region, it will receive a new field vector and acceleration.

    If I use point vicinities, I would have to take the position of the bullet and find the closest grid point to the bullet. Then, 
    I could avoid using the GridRegion object and directly input the points (coordinates) into the map that contains the field vectors.


*/