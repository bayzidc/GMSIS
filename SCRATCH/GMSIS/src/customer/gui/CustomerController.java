/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer.gui;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private MenuBar menu;
    @FXML
    private Menu users;
    @FXML
    private MenuItem user;
    @FXML
    private Menu customerAcc;
    @FXML
    private MenuItem customers;
    @FXML
    private Menu vecRecord;
    @FXML
    private MenuItem records;
    @FXML
    private MenuItem vecEntry;
    @FXML
    private Menu diagAndRep;
    @FXML
    private MenuItem booking;
    @FXML
    private Menu partsRecord;
    @FXML
    private MenuItem partsStock;
    @FXML
    private MenuItem partsUsed;
    @FXML
    private Menu logout;
    @FXML
    private MenuItem home;

    /**
     * Initializes the controller class.
     */
    
    @FXML 
    private void logout(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Login.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void users(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void cus(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/gui.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML
    private void vehicleRecord(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML
    private void vehicleEntry(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/AddVehicle.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void diag(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void pStock(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/partStock.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void pUsed(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        users.setDisable(false);
        users.setVisible(false);
    }    
    
     public void alertInf(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
