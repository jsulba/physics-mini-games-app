/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import physicsminigames.chargecannon.AssetManager;

/**
 * FXML Controller class
 *
 * @author thund
 */
public class ChargeCannonHelpController implements Initializable {

    @FXML
    private ImageView screenshot;
    
    @FXML
    private Label instructions;
    
    @FXML
    private Label tips;
    
    @FXML
    private AnchorPane pane; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set content of help window
        screenshot.setImage(AssetManager.getGameplayScreenShot().getImage());
        instructions.setText("How to Play:\n\n"
                + "1) Objective: Shoot electrically modified bullets into the generator to ultimately charge it up with enough energy.\n"
                + "2) Controls: You have four sliders at your disposition to determine the MASS, the CHARGE, the INITIAL SPEED, and ANGLE of your bullet every turn. Once you have set your parameters, you shoot your bullet by pressing the \"Fire!\" button.\n"
                + "3) Game Elements: GROUND WALLS will stop the bullet and destroy it. GROUND GATES will only make your bullet's charge neutral. FIELD SOURCES show where the electric field is emanating from. The GENERATOR is the target. The CANNON is the fixed point from which your bullets will shoot from."
                + " The bullets are color coded: RED for POSITS (positive bullets), BLUE for TEGANS (negative) and GREEN for NEUTRAS (neutral).\n"
                + "4) Game Rules: You have 12 bullets every round to complete your objective. If you run out, it's game over. If you charge the generator fully, you pass to the next level. There are 3 levels.\n\n");
        tips.setText("Tips:\n\n"
                + "1) Increasing mass will charge the cannon faster but will also cause the bullet to be less affected by the electric field due to Newton's Second Law (it will accelerate slower).\n"
                + "2) Increasing charge magnitude will cause the bullet to be affected more by the field due to the principle of Electric Force.\n"
                + "3) Increasing speed will charge the cannon faster but will reduce the effects of the field because of the principle of Impulse (the forces act on the bullet for less time).\n"
                + "4) Stay aware of how many bullets there are left in the top as well as the level and the current charge level of the generator (circled red) compared to the amount required for the level.");
    }     
    
}
