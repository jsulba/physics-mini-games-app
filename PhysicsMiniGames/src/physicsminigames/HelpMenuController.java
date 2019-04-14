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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import physicsminigames.mirrorsoflife.AssetManager;

/**
 * FXML Controller class
 *
 * @author thund
 */
public class HelpMenuController implements Initializable {

    @FXML
    private ScrollPane sp;
    
    @FXML
    private AnchorPane pane;
    
    @FXML
    private VBox vbox;
    
    @FXML
    private ImageView imageViewHelpTop;
    
    @FXML
    private ImageView imageViewHelpMid;
    
    @FXML
    private ImageView imageViewHelpBottom;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        
    }    
    
}
