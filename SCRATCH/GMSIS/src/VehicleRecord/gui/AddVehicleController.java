/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddVehicleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    VehicleController cont = new VehicleController();
    ObservableList<String> vehicleBox = FXCollections.observableArrayList("Car","Van","Truck");
    ObservableList<String> quickSelection = FXCollections.observableArrayList();
    ObservableList<Integer> custID = FXCollections.observableArrayList();
    ObservableList<String> fuelT = FXCollections.observableArrayList("Petrol","Diesel");
    ObservableList<String> warrantyChoice = FXCollections.observableArrayList();
    
    @FXML 
    private ChoiceBox customerNames;
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
    private DatePicker warExpiry;
    @FXML
    private TextField id;
    @FXML
    private Button backToRec;
    @FXML
    private Button clearBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button addEntry;
    
    @FXML
    private void backButton(ActionEvent event) throws IOException // method which goes back to admin page
    {
        Parent vecRecords = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
        Scene vec_Scene = new Scene(vecRecords);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();           
        stage2.setScene(vec_Scene);
        stage2.show();
        
    }
    @FXML
    private void clearButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        fuelType.setValue(null);
        colour.clear();
        motRenDate.setValue(null);
        motRenDate.getEditor().setText(null);
        lastService.setValue(null);
        lastService.getEditor().setText(null);
        mileage.clear();
        vehicleChoice.setValue(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
        customerNames.setValue(null);
    }
    
    @FXML
    private void addEntry(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to add vehicle
    {
        
        createData();
        System.out.println("Vehicle Added to Database");
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        fuelType.setValue(null);
        colour.clear();
        motRenDate.setValue(null);
        motRenDate.getEditor().setText(null);
        lastService.setValue(null);
        lastService.getEditor().setText(null);
        mileage.clear();
        vehicleChoice.setValue(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
        
    }
    
    @FXML
    private void updateButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {
        editVehicle();
        System.out.println("Edited on db");
        JOptionPane.showMessageDialog(null,"Updated");
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        fuelType.setValue(null);
        colour.clear();
        mileage.clear();
        vehicleChoice.setValue(null);
        motRenDate.setValue(null);
        motRenDate.getEditor().setText(null);
        lastService.setValue(null);
        lastService.getEditor().setText(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
    }
    
    
    private void createData() throws ClassNotFoundException
    {
        
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            
            String sql = "insert into vehicleList(RegNumber,Make,Model,EngSize,FuelType,Colour,MOTDate,LastServiceDate,Mileage,VehicleType,Warranty,WarrantyNameAndAdd,WarrantyExpDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //String sql = "insert into Login(Username,Password) values(?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, regNumber.getText());
            state.setString(2,make.getText());
            state.setString(3, model.getText());
            state.setString(4, engSize.getText());
            state.setString(5, (String) fuelType.getValue());
            state.setString(6, colour.getText());
            state.setString(7, ((TextField) motRenDate.getEditor()).getText());
            state.setString(8, ((TextField) lastService.getEditor()).getText());
            state.setString(9, mileage.getText());
            state.setString(10, (String) vehicleChoice.getValue());
            state.setString(11, warrantyChoice.toString());
            state.setString(12, nameAndAdd.getText());
            state.setString(13, ((TextField) warExpiry.getEditor()).getText());
            state.execute();
            
            state.close();
            conn.close();
            
            //submit=true;
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        //return submit;       
            
        }
    
     private void editVehicle() throws ClassNotFoundException
     {
         Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehicleList SET RegNumber=?,Make=?,Model=?,EngSize=?,FuelType=?,Colour=?,MOTDate=?, LastServiceDate=?,Mileage=?,VehicleType=?,Warranty=?,WarrantyNameAndAdd=?,WarrantyExpDate=? WHERE vehicleID=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, regNumber.getText());
            state.setString(2,make.getText());
            state.setString(3, model.getText());
            state.setString(4, engSize.getText());
            state.setString(5, (String) fuelType.getValue());
            state.setString(6, colour.getText());
            state.setString(7, ((TextField) motRenDate.getEditor()).getText());
            state.setString(8, ((TextField) lastService.getEditor()).getText());
            state.setString(9, mileage.getText());
            state.setString(10, (String) vehicleChoice.getValue());
            state.setString(11, warrantyChoice.toString());
            state.setString(12, nameAndAdd.getText());
            state.setString(13, ((TextField) warExpiry.getEditor()).getText());
            state.setString(14, id.getText());

            state.execute();

            state.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
     }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vehicleChoice.setItems(vehicleBox);
        fuelType.setItems(fuelT);
        
        quickSel.setOnAction(e ->{
            
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                    System.out.println("Opened Database Successfully");
                    String query = "select * from vehicleList where Make = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1,(String) quickSel.getSelectionModel().getSelectedItem());
                    rs = ps.executeQuery();
                    
                    while(rs.next())
                    {
                        regNumber.setText(rs.getString("RegNumber"));
                        make.setText(rs.getString("Make"));
                        model.setText(rs.getString("Model"));
                        engSize.setText(rs.getString("EngSize"));
                        fuelType.setValue(rs.getString("FuelType"));
                        colour.setText(rs.getString("Colour"));
                        ((TextField) motRenDate.getEditor()).setText(rs.getString("MOTDate"));
                        ((TextField) lastService.getEditor()).setText(rs.getString("LastServiceDate"));
                        mileage.setText(rs.getString("Mileage"));   
                        vehicleChoice.setValue(rs.getString("VehicleType"));
                        nameAndAdd.setText(rs.getString("WarrantyNameAndAdd"));
                        ((TextField) warExpiry.getEditor()).setText(rs.getString("WarrantyExpDate"));
                        id.setText(rs.getString("vehicleID"));
                    }
                    
                    conn.close();
                    ps.close();
                    rs.close();
                }
                
                catch(Exception ex)
                {
                    
                }
            
        });
        
    }  
    
    public void fillQuickSelection() throws ClassNotFoundException
    {
        Connection conn=null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");
            String query = "Select Make from vehicleList";

            ResultSet rs = conn.createStatement().executeQuery(query);
            
            
            while(rs.next())
            {
                quickSelection.add(rs.getString("Make"));
                quickSel.setItems(quickSelection);
                
            }

            rs.close();
            conn.close();
        }
        
        catch(SQLException e)
        {
            
        }
    }
   
     
    @FXML
    private void checkBox1(MouseEvent e)
    {
        warrantyChoice.add(yesWarranty.getText());
    }
    @FXML
    private void checkBox2(MouseEvent e)
    {
        warrantyChoice.add(noWarranty.getText());
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
