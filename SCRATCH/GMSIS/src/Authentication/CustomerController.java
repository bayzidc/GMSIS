/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerController implements Initializable {

    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @FXML
     private void logoutButton(ActionEvent event) throws IOException, ClassNotFoundException
     {
        Parent logoutPage = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logout_Scene = new Scene(logoutPage);
        Stage stageLogout = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageLogout.setScene(logout_Scene);
        stageLogout.show();
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
