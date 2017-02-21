/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import PartsRecord.logic.parts;
import PartsRecord.logic.partsUsed;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.ButtonType;


/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartsController implements Initializable {
    
    @FXML
    private TextField idNumber;
    @FXML
    private TextField cost;
    @FXML
    private TextField dateOfInstall;
    @FXML
    private TextField dateOfWarrantyExpire ;
    @FXML
    private TextField vehicleRegistrationNumber;
    @FXML
    private TextField customerFullName;
    

    
    @FXML
    private TableView<partsUsed> table;
    @FXML
    private TableColumn<partsUsed, Integer> idCol;
    @FXML
    private TableColumn<partsUsed, Double> costCol; 
    @FXML
    private TableColumn<partsUsed, String> installDateCol;
    @FXML
    private TableColumn<partsUsed, String> expireDateCol;
    @FXML
    private TableColumn<partsUsed, String> registrationNoCol;
    @FXML
    private TableColumn<partsUsed, String> customerNameCol;
    @FXML // Observable list to hold parts object.
    ObservableList<partsUsed> data;
    
   @FXML
    public void backButton(ActionEvent event) throws IOException, ClassNotFoundException // method which goes back to admin page
    {   // When pressed the back button load the Admin.fxml file
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();           
        stage2.setScene(admin_Scene);
        stage2.show();
        
    }
    
    

    
    
    
    
    
    
    
  
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
