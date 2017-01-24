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
public class AdminController implements Initializable {

    @FXML
    private Button createUser;
    @FXML
    private Button editUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @FXML
     private void createButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent new_User = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new_User_scene);
        stage.show();
    }
     @FXML
     private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent del_User = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
        Scene del_User_scene = new Scene(del_User);
        Stage delPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        delPage.setScene(del_User_scene);
        delPage.show();
    }
     @FXML
     private void editButton(ActionEvent event) throws IOException, ClassNotFoundException
     {
        Parent edit_User = FXMLLoader.load(getClass().getResource("EditUsers.fxml"));
        Scene edit_User_scene = new Scene(edit_User);
        Stage editPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editPage.setScene(edit_User_scene);
        editPage.show();
     }
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
