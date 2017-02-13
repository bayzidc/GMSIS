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
public class PartStockController implements Initializable {
    @FXML
    private TextField idNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField description ;
    @FXML
    private TextField stockLevels;
    @FXML
    private TextField cost;
    @FXML
    private TableView<parts> table;
    @FXML
    private TableColumn<parts, Integer> partsID;
    @FXML
    private TableColumn<parts, String> nameOfParts;
    @FXML
    private TableColumn<parts, String> descriptionOfParts;
    @FXML
    private TableColumn<parts, Integer> stockLevelsOfParts ;
    @FXML
    private TableColumn<parts, Integer> costOfParts;
    @FXML
    private Button back;
    
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
