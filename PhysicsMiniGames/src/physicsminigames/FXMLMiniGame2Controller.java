/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class FXMLMiniGame2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    Button quit;
    
    @FXML
    Label title;
    
    @FXML
    AnchorPane rootPane;
    
    @FXML
    private void quitAction(ActionEvent ae) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
