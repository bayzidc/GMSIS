/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Fabiha
 */
public class partsController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private TextField idNumber;
    @FXML
    private Button backButton;
    
    @FXML
    private void backButton(ActionEvent event) throws IOException
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();           
        stage2.setScene(admin_Scene);
        stage2.show();
        
    }
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
