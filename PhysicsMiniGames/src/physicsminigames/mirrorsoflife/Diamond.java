package physicsminigames.mirrorsoflife;

public class Diamond extends GameObject{
    private static Vector2D outputVector;
    private static double inputAngle;
    private static double outputAngle;
    private static final double n2Value = 2.417;
    private boolean calcAgain = true;
    
    public Diamond(double x, double y)
    {
        super(new Vector2D(x,y),30);
        circle.setFill(AssetManager.getDiamondImage());       
    }   
    
    
    public static Vector2D returnReflectedVector( Vector2D input)
    {  
        Vector2D photonVelocity = input;
        inputAngle = input.angle();
        double sinAngle = Math.sin(Math.toRadians(inputAngle));
        double angleDividedByN = sinAngle / n2Value;
        double angleASin = Math.toDegrees(Math.asin((angleDividedByN)));
        double xAbsComp = Math.cos(Math.toRadians(angleASin));
        double yAbsComp = Math.sin(Math.toRadians(angleASin));
        
        double photonMagnitude = photonVelocity.magnitude();
           
        if (photonVelocity.getX() < 0)
        {
            xAbsComp = -1*xAbsComp;
        }
        if (photonVelocity.getY() < 0)
        {
            yAbsComp = -1*yAbsComp;
        }
        
        outputVector = new Vector2D ( xAbsComp*photonMagnitude , yAbsComp*photonMagnitude);     
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
