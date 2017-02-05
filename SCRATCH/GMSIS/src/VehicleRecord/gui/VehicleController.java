/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.gui;

import VehicleRecord.logic.Vehicle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VehicleController implements Initializable {

    ObservableList<String> vehicleBox = FXCollections.observableArrayList("Car","Van","Truck");
    ObservableList<String> quickSelection = FXCollections.observableArrayList("Honda Jazz", "Ford Focus", "Audi A5", "Toyota Yaris", "Vauxhall Corsa");
    ObservableList<String> fuelT = FXCollections.observableArrayList("Petrol","Diesel");
    @FXML
    private ChoiceBox vehicleChoice;
    @FXML
    private CheckBox yesWarranty;
    @FXML
    private CheckBox noWarranty;
    @FXML
    private TextArea nameAndAdd;
    @FXML
    private ChoiceBox quickSel;
    @FXML
    private TextField regNumber;
    @FXML
    private TextField make;
    @FXML
    private TextField model;
    @FXML
    private TextField engSize;
    @FXML
    private ChoiceBox fuelType;
    @FXML
    private TextField colour;
    @FXML
    private DatePicker motRenDate;
    @FXML
    private DatePicker lastService;
    @FXML
    private TextField mileage;
    @FXML
    private Button queryList;
    @FXML
    private Button searchVehicles;
    @FXML
    private Button backBtn;
     @FXML private TableView<Vehicle> table;
    @FXML private TableColumn<Vehicle, String> regCol;
    @FXML private TableColumn<Vehicle, String> makeCol;
    @FXML private TableColumn<Vehicle, String> modelCol;
    @FXML private TableColumn<Vehicle, Double> engSizeCol;
    @FXML private TableColumn<Vehicle, String> fuelTypeCol;
    @FXML private TableColumn<Vehicle, String> colourCol;
    @FXML private TableColumn<Vehicle, String> motRenewalCol;
    @FXML private TableColumn<Vehicle, String> lastServiceCol;
    @FXML private TableColumn<Vehicle, Integer> mileageCol;
    
   
    /**
     * Initializes the controller class.
     */
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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vehicleChoice.setItems(vehicleBox);
        quickSel.setItems(quickSelection);
        fuelType.setItems(fuelT);
        
        regCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("Reg Number"));        
    makeCol.setCellValueFactory(                
        new PropertyValueFactory<Vehicle,String>("Make"));
    modelCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("Model"));        
    engSizeCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,Double>("Engine Size"));
    fuelTypeCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("Fuel Type"));        
    colourCol.setCellValueFactory(                
        new PropertyValueFactory<Vehicle,String>("Colour"));
    motRenewalCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("M.O.T Date"));        
    lastServiceCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("Date of Last Serv"));
    mileageCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,Integer>("Mileage"));
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
