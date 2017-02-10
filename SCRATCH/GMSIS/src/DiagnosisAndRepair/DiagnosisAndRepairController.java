/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import VehicleRecord.logic.Vehicle;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
 * @author Bayzid
 */
public class DiagnosisAndRepairController implements Initializable {

    @FXML 
    private TextField mileage;
    @FXML 
    private DatePicker date;
    
    
    @FXML 
    private TableView<DiagnosisAndRepairBooking> table;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> custIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> regNumberCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> mechanicIDCol;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, String> partsRequiredCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, Double> mileageCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> dateCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, Integer> durationCol;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    custIDCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,String>("CustID"));        
    regNumberCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,String>("regNumber"));
    mechanicIDCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,String>("MechanicID"));
    partsRequiredCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,String>("partsRequired"));        
    mileageCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,Double>("Mileage"));
    dateCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,String>("Date"));        
    durationCol.setCellValueFactory(new PropertyValueFactory<DiagnosisAndRepairBooking,Integer>("Duration in minutes"));
   
    }    
    
    @FXML
    private void book(ActionEvent event) throws IOException, ClassNotFoundException
    {
        createBooking();
        buildBooking();
    }
    
    private void createBooking() throws ClassNotFoundException
    {
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            String sql = "insert into Booking(custID,regNumber,MechanicID,mileage,partsRequired,date,duration) values(?,?,?,?,?,?,?)";
           
            PreparedStatement state = conn.prepareStatement(sql);
           // state.setString(1, custID.getText());
           // state.setString(2, regNumber.getText());
           // state.setString(3, mechanicID.getText());
            state.setString(4, mileage.getText());
           // state.setString(4, partsRequired.getText());
            //state.setString(6, ((TextField)date.getEditor()).getText());
            
            int duration = calculateDuration();
            
           // state.setString(7, duration);
            
            state.execute();
            
            state.close();
            conn.close();
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    private void buildBooking() throws ClassNotFoundException
    {
        
    }
    
    private int calculateDuration()
    {
        return 0;
    }
}
