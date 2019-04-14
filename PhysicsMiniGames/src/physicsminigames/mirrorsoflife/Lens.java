package physicsminigames.mirrorsoflife;

import jdk.nashorn.internal.codegen.CompilerConstants;

public class Lens extends GameObject{
    private static Vector2D outputVector;
    private static double inputAngle;
    double focalLength = 40;
    private double rayDistance = 0;
    private boolean calcAgain = true;
    public Lens(double x, double y)
    {
        super(new Vector2D(x,y),5,80);
        rectangle.setFill(AssetManager.getLensImage());
    }     
    
       
      public static Vector2D returnReflectedVector( Vector2D inputVelocity)
    {
        //formula 
            double magnitude = inputVelocity.magnitude();
            if ( inputVelocity.getX() > 0)
            {outputVector = new Vector2D(  magnitude , 0);}
            else{
                outputVector = new Vector2D( - magnitude , 0);
            }

        return outputVector;       
    }
      
      public static Vector2D returnReflectedVector ( Vector2D inputVelocity,Vector2D lensPosition, Vector2D photonPosition,double focalLength, double lensHeight)
      {     
          double yDist = Math.abs(photonPosition.getY() -( lensPosition.getY() + 0.5*lensHeight));
          double xDist = focalLength;
          double angleInput = Math.toDegrees(Math.atan(Math.abs(yDist/xDist)));
          double velocityMagnitude = inputVelocity.magnitude();
          double xComp = Math.cos(Math.toRadians(angleInput));
          double yComp = Math.sin(Math.toRadians(angleInput));
          if ( inputVelocity.getX() < 0 )
              {
                  xComp = -1*xComp;
              }
                 
          if  ( photonPosition.getY() < (lensPosition.getY()+ (4.0/10)*(lensHeight)))
          {
 
              outputVector = new Vector2D ( xComp * velocityMagnitude, yComp * velocityMagnitude);           
          }
          
          if ( photonPosition.getY() > (lensPosition.getY()+ (6.0/10)*(lensHeight)))
          {
              yComp = -1*yComp;
              outputVector = new Vector2D ( xComp * velocityMagnitude, yComp * velocityMagnitude);   
              
          }
       
          return outputVector;
      }

      
      public boolean getCalcCondition(){
            return calcAgain;
        }
        
        public void switchCalcAgain(){
            calcAgain = !calcAgain;
        }
        
        public void resetCalcCondition()
        {
            calcAgain = true;
        }
      
      
      
    
}
