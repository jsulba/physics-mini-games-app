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
    private AnchorPane pane; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set content of help window
        screenshot.setImage(AssetManager.getGameplayScreenShot().getImage());
    }     
    
}
