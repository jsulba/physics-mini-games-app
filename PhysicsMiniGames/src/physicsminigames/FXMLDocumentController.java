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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author cstuser
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    AnchorPane rootPane;
    
    @FXML
    VBox box;
    
    @FXML
    Button game1;
    
    @FXML
    Button game2;
    
    @FXML
    Button game3;
    
    @FXML
    private void startMiniGame1(ActionEvent event) throws IOException {
        
        // Start mini game in new window
        Parent newRoot = FXMLLoader.load(getClass().getResource("FXMLMiniGame1.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Charge Cannon");
        stage.setScene(new Scene(newRoot));
        stage.show();
        
        // Hide main menu
        // rootPane.getScene().getWindow().hide();
    }
    
    @FXML
    private void startMiniGame2(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLMiniGame2.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void startMiniGame3(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLMiniGame3.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
