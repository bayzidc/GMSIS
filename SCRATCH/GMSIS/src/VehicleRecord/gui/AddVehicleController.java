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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    ObservableList<String> custN = FXCollections.observableArrayList();
    ObservableList<String> fuelT = FXCollections.observableArrayList("Petrol","Diesel");
    ObservableList<String> warrantyChoice = FXCollections.observableArrayList();
    
    @FXML 
    public ChoiceBox customerNames;
    @FXML
    public ChoiceBox vehicleChoice;
    @FXML
    public CheckBox yesWarranty;
    @FXML
    public CheckBox noWarranty;
    @FXML
    public TextArea nameAndAdd;
    @FXML
    public ChoiceBox quickSel;
    @FXML
    public TextField regNumber;
    @FXML
    public TextField make;
    @FXML
    public TextField model;
    @FXML
    public TextField engSize;
    @FXML
    public ChoiceBox fuelType;
    @FXML
    public TextField colour;
    @FXML
    public DatePicker motRenDate;
    @FXML
    public DatePicker lastService;
    @FXML
    public TextField mileage;
    @FXML
    public DatePicker warExpiry;
    @FXML
    public TextField id;
    @FXML
    public Button backToRec;
    @FXML
    public Button clearBtn;
    @FXML
    public Button updateBtn;
    @FXML
    public Button addEntry;
    
    @FXML
    public void backButton(ActionEvent event) throws IOException // method which goes back to admin page
    {
        Parent vecRecords = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
        Scene vec_Scene = new Scene(vecRecords);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();           
        stage2.setScene(vec_Scene);
        stage2.show();
        
    }
    @FXML
    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException
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
    public void addEntry(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to add vehicle
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
    public void updateButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("Vehicle.fxml"));
        VehicleController c = (VehicleController) loader.getController();
        editVehicle();
        buildData();
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
    public void buildData() throws ClassNotFoundException
    {
        Connection conn = null;
    try{      
        
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
 
        String SQL = "select RegNumber, Make, Model, Engsize, FuelType, Colour, MOTDate, LastServiceDate, Mileage, VehicleType, Warranty, WarrantyNameAndAdd, WarrantyExpDate, vehicleID, customer_id from vehicleList, customer where vehicleList.vehicleID = customer.customer_id";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        
        rs.close();
        conn.close();
    }
    
    catch(Exception e)
    {
        
    }
    
    fillQuickSelection();
    fillCustomerNames();
    }
    
    public void createData() throws ClassNotFoundException
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
    
     public void editVehicle() throws ClassNotFoundException
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
        
        yesWarranty.setOnAction(e ->{
            warrantyChoice.add(yesWarranty.getText());
        });
        
        noWarranty.setOnAction(e ->{
            warrantyChoice.add(noWarranty.getText());
        });
        try {
            buildData();
             customerNames.setOnAction(e ->{
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                    System.out.println("Opened Database Successfully");
                    String query = "select customer_fullname from customer, vehicleList where customer_fullname = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1,(String) customerNames.getSelectionModel().getSelectedItem());
                    rs = ps.executeQuery();
                    conn.close();
                    ps.close();
                    rs.close();
                }
                
                catch(Exception ex)
                {
                    
                }
        });
        quickSel.setOnAction(e ->{
            
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                    System.out.println("Opened Database Successfully");
                    String query = "select * from vehicleList,customer where Make = ?";
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
                        customerNames.setValue(rs.getString("customer_fullname"));
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
        catch(Exception e)
        {
            
        }
    }
    
   /* public void showText() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("Vehicle.fxml").openStream());    
        VehicleController c = (VehicleController) loader.getController();
    }*/
    public void fillCustomerNames() throws ClassNotFoundException
    {
        Connection conn = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String query = "Select customer_fullname from customer";

            ResultSet rs = conn.createStatement().executeQuery(query);
            
            
            while(rs.next())
            {
                custN.add(rs.getString("customer_fullname"));
                customerNames.setItems(custN);
                
            }

            rs.close();
            conn.close();
        }
        
        catch(SQLException e)
        {
            
        }
    }
    public void fillQuickSelection() throws ClassNotFoundException
    {
        Connection conn=null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
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
