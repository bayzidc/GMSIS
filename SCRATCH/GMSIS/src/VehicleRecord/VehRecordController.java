/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VehRecordController implements Initializable {

    ObservableList<String> vehicleBox = FXCollections.observableArrayList("Car","Van","Truck");
    @FXML
    private ChoiceBox vehicleChoice;
    @FXML
    private CheckBox yesWarranty;
    @FXML
    private CheckBox noWarranty;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vehicleChoice.setItems(vehicleBox);
    }    
    @FXML
    private void handleYesBox()
    {
        if(yesWarranty.isSelected())
        {
            noWarranty.setSelected(false); 
        }
    }
    @FXML
    private void handleNoBox()
    {
        if(noWarranty.isSelected())
        {
            yesWarranty.setSelected(false);
        }
    }
}
