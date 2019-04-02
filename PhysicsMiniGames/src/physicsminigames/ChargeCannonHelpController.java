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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author thund
 */
public class ChargeCannonHelpController implements Initializable {

    @FXML
    private Label instructions;
    
    @FXML
    private AnchorPane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pane.setPrefSize(700, 600);
        instructions.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());
        instructions.setText("Instructions:\n\n"
                + "1) Objective: Shoot electrically modified bullets into the generator on the right side of the game area to ultimately charge it up with enough energy.\n"
                + "2) Controls: You have four sliders at your disposition to determine the MASS, the CHARGE, the INITIAL SPEED, and ANGLE of your bullet every turn. Once you have set your parameters, you shoot your bullet by pressing the Fire button.\n"
                + "3) Game Elements: GROUND WALLS (yellow rods) will stop the bullet and destroy it. GROUND GATES (orange ovals) will only make your bullet's charge neutral. FIELD SOURCES (red and blue spheres) show where the electric field is emanating from. The GENERATOR (rightmost grey machine with wires) is the target. It charges a certain amount (visible in top left corner) that depends on the mass and speed of the bullet on every impact. The CANNON (leftmost grey machine) is the fixed point from which your bullets will shoot from."
                + " The bullets are color coded: RED for POSITS (positive bullets), BLUE for TEGANS (negative) and GREEN for NEUTRAS (neutral).\n"
                + "4) Game Rules: You have 7 bullets every round to complete your objective. If you run out, it's game over. If you charge the generator fully, you pass to the next level. There are 3 levels.\n\n"
                + "Theoretical Tips:\n\n"
                + "1) Increasing mass will charge the cannon faster but will also cause the bullet to be less affected by the electric field due to Newton's Second Law (it will accelerate slower).\n"
                + "2) Increasing charge magnitude will cause the bullet to be affected more by the field due to the principle of Electric Force.\n"
                + "3) Increasing speed will charge the cannon faster but will reduce the effects of the field because of the principle of Impulse (the forces act on the bullet for less time).");
    }     
    
}
