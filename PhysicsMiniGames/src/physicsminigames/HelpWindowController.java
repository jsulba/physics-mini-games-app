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
import javafx.scene.image.ImageView;
import physicsminigames.projectileshooter.AssetManager;

/**
 * FXML Controller class
 *
 * @author Family Desktop
 */
public class HelpWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView imageViewHelpTop;
    
    @FXML
    private ImageView imageViewHelpBottom;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        AssetManager.preloadAllAssets();
        
        //imageViewHelpTop = new ImageView();
        imageViewHelpTop.setImage(AssetManager.getImageHelpTop());           
        
        //imageViewHelpBottom = new ImageView();        
        imageViewHelpBottom.setImage(AssetManager.getImageHelpBottom());

    }    
    
}
