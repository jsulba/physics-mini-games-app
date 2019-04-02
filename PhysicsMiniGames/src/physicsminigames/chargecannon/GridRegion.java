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
public class GridRegion {
    private Vector2D topLeftCornerPosition;
    private double sideLength;
    private Vector2D electricFieldVector;
    
    public GridRegion(double x, double y, double s){
        topLeftCornerPosition = new Vector2D(x, y);
        sideLength = s;
    }
    
    public Vector2D getCenter(){
        double xCenter = topLeftCornerPosition.getX() + sideLength/2;
        double YCenter = topLeftCornerPosition.getY() + sideLength/2;
        
        return new Vector2D(xCenter, YCenter);
    }
    
    public void setElectricFieldVector(double sourceCharge, Vector2D sourcePosition, Vector2D gridCenter){
        Vector2D directional = new Vector2D(gridCenter.getX()-sourcePosition.getX(), gridCenter.getY()-sourcePosition.getY());
        electricFieldVector = Physics.calcElectricField(sourceCharge, directional);
    }
    
    public void setNetElectricFieldVector(ArrayList<Double> sourceCharges, ArrayList<Vector2D> sourcePositions, Vector2D gridCenter){
        ArrayList<Vector2D> directionals = new ArrayList<>();
        for(Vector2D sp: sourcePositions){
            Vector2D directional = new Vector2D(gridCenter.getX()-sp.getX(), gridCenter.getY()-sp.getY());
            directionals.add(directional);
        }
        electricFieldVector = Physics.calcNetElectricField(sourceCharges, directionals);
        //System.out.println("Xdir: " + directionals.get(0).getX() + "\tYdir: " + directionals.get(0).getY());
    }
    
    public Vector2D getElectricFieldVector(){
        return electricFieldVector;
    }
    
    public Vector2D getCornerPosition(){
        return topLeftCornerPosition;
    }
    
    public double getSideLength(){
        return sideLength;
    }
}
