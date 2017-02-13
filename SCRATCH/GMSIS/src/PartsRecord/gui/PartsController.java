/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import PartsRecord.logic.parts;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartsController implements Initializable {
    
    @FXML
    private TextField idNumber;
    @FXML
    private TextField dateOfInstall;
    @FXML
    private TextField dateOfWarrantyExpire ;
    @FXML
    private TextField customer_id;
    @FXML
    private TextField vehicle_id;
    @FXML
    private TextField booking_id ;
    @FXML
    private TextField bill_id;
    @FXML
    private Button back;
    @FXML
    private TableView<parts> table;
    @FXML
    private TableColumn<parts, Integer> partsID;
    @FXML
    private TableColumn<parts, String> installDate;
    @FXML
    private TableColumn<parts, String> WarrantyExpireDate;
    @FXML
    private TableColumn<parts, Integer> vehicleId;
    @FXML
    private TableColumn<parts, Integer> customerId;
    @FXML
    private TableColumn<parts, Integer> billingId;
    @FXML
    private TableColumn<parts, Integer> bookingId;
    
   
    
    
    
    
    
    

    
    @FXML
    private void backButton(ActionEvent event) throws IOException // method which goes back to admin page
    {
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
