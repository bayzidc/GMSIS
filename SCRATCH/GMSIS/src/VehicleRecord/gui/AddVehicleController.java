/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.gui;

import Authentication.sqlite;
import VehicleRecord.logic.Vehicle;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Hamza Busuri
 */
public class AddVehicleController implements Initializable {

  
    
    // Declaring all observable array lists
    VehicleController cont = new VehicleController();
    ObservableList<String> vehicleBox = FXCollections.observableArrayList("Car","Van","Truck");
    ObservableList<String> quickSelection = FXCollections.observableArrayList();
    ObservableList<String> custN = FXCollections.observableArrayList();
    ObservableList<String> fuelT = FXCollections.observableArrayList("Petrol","Diesel");
    ObservableList<String> warrantyChoice = FXCollections.observableArrayList();
    
    // Declaring FXML buttons, choiceboxes, textfields, tablecolumns, tables etc.
    @FXML
    public AnchorPane pane;
    @FXML 
    public SplitPane split;
    @FXML 
    public JFXButton back;
    @FXML
    public JFXButton log;
    @FXML 
    public Label vID;
    @FXML
    public Label warrantyName;
    @FXML
    public Label warrantyDate;
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
    public TextField custID;
    @FXML
    public Button backToRec;
    @FXML
    public Button clearBtn;
    @FXML
    public Button updateBtn;
    @FXML
    public Button addEntry;
    
    //Creating a vehicle object to be used later on
    Vehicle vec = new Vehicle("","","",0.0,"","","","",0,"","","","",0,0,"");
   
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vehicleChoice.setItems(vehicleBox);
        fuelType.setItems(fuelT);
        id.setEditable(false);
        custID.setEditable(false);
        yesWarranty.setOnAction(e ->{
            warrantyChoice.add(yesWarranty.getText());
            noWarranty.setSelected(false); 
            nameAndAdd.setVisible(true);
            warExpiry.setVisible(true);
            warrantyName.setVisible(true);
            warrantyDate.setVisible(true);
            nameAndAdd.clear();         
            warExpiry.setValue(null);
        });
        
        noWarranty.setOnAction(e ->{
            yesWarranty.setSelected(false);
            warrantyChoice.add(noWarranty.getText());
            nameAndAdd.clear();
            warExpiry.setValue(null);
            nameAndAdd.setVisible(false);
            warExpiry.setVisible(false);
            warrantyName.setVisible(false);
            warrantyDate.setVisible(false);
        });
        
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isAfter(LocalDate.now()))
                {
                    
                    setStyle("-fx-background-color: #fcbabf;");
                    Platform.runLater(() -> setDisable(true));                 
                }
            }
        };
        lastService.setDayCellFactory(dayCellFactory);
        
        Callback<DatePicker, DateCell> dayCellFactory1 = dp1 -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()))
                {
                    
                    setStyle("-fx-background-color: #fcbabf;");
                    Platform.runLater(() -> setDisable(true));                 
                }
            }
        };
        motRenDate.setDayCellFactory(dayCellFactory1);

        try {
            buildData();
             customerNames.setOnAction(e ->{ //Customer names show up on the database into a choicebox for the user to select
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try
                {
                    conn = (new sqlite().connect());
                    System.out.println("Opened Database Successfully");
                    String query = "select customer_id, customer_fullname from customer where customer_fullname = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1,(String) customerNames.getSelectionModel().getSelectedItem());
                    rs = ps.executeQuery();
                    while(rs.next())
                    {
                        custID.setText(String.valueOf(rs.getInt("customer_id")));
                    }
                    conn.close();
                    ps.close();
                    rs.close();
                }
                
                catch(Exception ex)
                {
                    
                }
        });
        quickSel.setOnAction(e ->{ // Lists vehicles so that the user can quick select a vehicle by make and model
            
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try
                {
                    conn = (new sqlite().connect());
                    System.out.println("Opened Database Successfully");
                    String query = "select * from quickSelection where make = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1,(String) quickSel.getSelectionModel().getSelectedItem());
                    rs = ps.executeQuery();
                    
                    while(rs.next())
                    {
                        make.setText(rs.getString("make"));
                        model.setText(rs.getString("model"));
                        engSize.setText(rs.getString("engSize"));
                        fuelType.setValue(rs.getString("fuelType"));
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
    
    @FXML 
    private void logout(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Login.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    
    // Method which allows the user to go back to the Vehicle Records page
    @FXML
    public void backButton(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
        pane.getChildren().setAll(rootPane);
        
    }
    
    //Method which allows the user to clear all the data in the textfields
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
        quickSel.setValue(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
        customerNames.setValue(null);
        custID.clear();
    }
    
    // Method which allows the user to add a vehicle for a specified customer.
    @FXML
    public void addEntry(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to add vehicle
    {
        if(checkTextFields() && checkForWhiteSpace())
        {
            createData();
            alertInf("Vehicle ID: " + getVehicleID() + " has been added for " + customerNames.getSelectionModel().getSelectedItem());
            buildData();
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
            quickSel.setValue(null);
            customerNames.setValue(null);
            Parent vecRecords = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
            Scene vec_Scene = new Scene(vecRecords);
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();           
            stage2.setScene(vec_Scene);
            stage2.show();
        }
        
    }
    
    //Method which allows the user to make changes to the vehicle they created
    @FXML
    public void updateButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {
        if(checkTextFields() && checkForWhiteSpace())
        {
        vec.setRegNumber(regNumber.getText());
        vec.setColour(colour.getText());
        vec.setEngSize(Double.parseDouble(engSize.getText()));
        vec.setFuelType(fuelType.getValue().toString());
        vec.setLastService(lastService.getEditor().getText());
        vec.setMake(make.getText());
        vec.setMileage(Integer.parseInt(mileage.getText()));
        vec.setModel(model.getText());
        vec.setMotRenewal(motRenDate.getEditor().getText());
        vec.setVehicleType(vehicleChoice.getValue().toString());
        vec.setWarNameAndAdd(nameAndAdd.getText());
        vec.setWarranty(yesWarranty.getText());
        vec.setWarranty(noWarranty.getText());
        vec.setWarrantyExpDate(warExpiry.getEditor().getText());
        
            editVehicle();
            alertInf("Vehicle ID: " + getVehicleID() + " has been updated for " + customerNames.getSelectionModel().getSelectedItem());

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
            customerNames.setValue(null);
            custID.clear();
            Parent vecRecords = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
            Scene vec_Scene = new Scene(vecRecords);
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();           
            stage2.setScene(vec_Scene);
            stage2.show();
        }
    }
    
    //Method which builds and refreshes the data for the vehicle
    public void buildData() throws ClassNotFoundException
    {
        Connection conn = null;
        try
        {      
            conn = (new sqlite().connect());
 
            String SQL = "select RegNumber, Make, Model, Engsize, FuelType, Colour, MOTDate, LastServiceDate, Mileage, VehicleType, Warranty, WarrantyNameAndAdd, WarrantyExpDate, vehicleID, customer_id from vehicleList, customer where vehicleList.vehicleID = customer.customer_id";            
            ResultSet rs = conn.createStatement().executeQuery(SQL);  
        
            rs.close();
            conn.close();
        }
    
        catch(Exception e)
        {
            alertInf("Error on building data");
        }
    
        fillQuickSelection();
        fillCustomerNames();
    }
    
    //Method which inserts data into the vehiceList table into the database
    public void createData() throws ClassNotFoundException
    {    
        Connection conn = null;      
        try
        {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            
            String sql = "insert into vehicleList(RegNumber,Make,Model,EngSize,FuelType,Colour,MOTDate,LastServiceDate,Mileage,VehicleType,Warranty,WarrantyNameAndAdd,WarrantyExpDate,customerid ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            if(yesWarranty.isSelected())
            {
            state.setString(11, "Yes");
            }
            else
            {
                state.setString(11, "No");
            }
            state.setString(12, nameAndAdd.getText());
            state.setString(13, ((TextField) warExpiry.getEditor()).getText());
            state.setString(14, custID.getText());
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
    
    //Method to check whether any vehicles exist in the database
    public boolean checkIfVehicleAlreadyExists() throws ClassNotFoundException, SQLException
    {
         int count = 0;
         
         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rset = null;
  
         try
         {
            conn = (new sqlite().connect());
            stmt = conn.prepareStatement("SELECT Count(vehicleID) from vehicleList WHERE RegNumber=?");
            stmt.setString(1, regNumber.getText());
            rset = stmt.executeQuery();
            if (rset.next())
                count = rset.getInt(1);
            return count > 0;
         } 
         finally 
         {
             if(rset != null) 
             {
                 try 
                 {
                    rset.close();
                 } 
                 catch(SQLException e)
                 {
                     e.printStackTrace();
                 }
             }        
            
             if(stmt != null) 
             {
                 try 
                 {
                     stmt.close();
                 }
                 catch(SQLException e) 
                 {
                     e.printStackTrace();
                 }
            }        
        }    
    }
    
    //Method which updates the vehicle changes into the database
    public void editVehicle() throws ClassNotFoundException
    {
        Connection conn = null;

        try 
        {
            
            conn = (new sqlite().connect());

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehicleList SET RegNumber=?,Make=?,Model=?,EngSize=?,FuelType=?,Colour=?,MOTDate=?, LastServiceDate=?,Mileage=?,VehicleType=?,Warranty=?,WarrantyNameAndAdd=?,WarrantyExpDate=? WHERE vehicleID=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, regNumber.getText());
            state.setString(2, make.getText());
            state.setString(3, model.getText());
            state.setDouble(4, Double.parseDouble(engSize.getText()));
            state.setString(5, fuelType.getValue().toString());
            state.setString(6, colour.getText());
            state.setString(7, motRenDate.getEditor().getText());
            state.setString(8, lastService.getEditor().getText());
            state.setInt(9, Integer.parseInt(mileage.getText()));
            state.setString(10, vehicleChoice.getValue().toString());
            if(yesWarranty.isSelected())
            {
            state.setString(11, "Yes");
            }
            else
            {
                state.setString(11, "No");
            }
            state.setString(12, nameAndAdd.getText());
            state.setString(13, warExpiry.getEditor().getText());
            state.setInt(14, Integer.parseInt(id.getText()));

            state.execute();

            state.close();
            conn.close();

        } 
        catch (SQLException e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
     }
     
    //Method which retrieves the vehicle ID from the database
    private int getVehicleID() throws ClassNotFoundException
    {
         int vecid=0; 
         Connection conn = null;
         java.sql.Statement state = null;
         
         try
         {
            conn = (new sqlite().connect());
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT * FROM vehicleList WHERE RegNumber= " + "'" + regNumber.getText() + "'");
            while(rs.next())
            {
                 vecid = rs.getInt("vehicleID");
            }
            rs.close();
            state.close();
            conn.close();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return vecid;
        
    }
    
    //Method which retrieves the customer names from the database
    private String getCustomerName() throws ClassNotFoundException
    {
        String name = "";
        Connection conn = null;
 
        java.sql.Statement state = null;
        try
        {
            conn = (new sqlite().connect());
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT customer_fullname FROM customer,vehicleList WHERE customer.customer_id = vehicleList.vehicleID");
            while(rs.next())
            {
                 name = rs.getString("customer_fullname");
            }
            rs.close();
            state.close();
            conn.close();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return name;
    }
    
    //Method which fills the combo box with customer names from the database
    public void fillCustomerNames() throws ClassNotFoundException
    {
        Connection conn = null;
        try
        {
            conn = (new sqlite().connect());
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
    
    //Method which fills the quick selection combo box with vehicles from the database
    public void fillQuickSelection() throws ClassNotFoundException
    {
        Connection conn=null;
        try
        {
            conn = (new sqlite().connect());
            String query = "Select make from quickSelection";

            ResultSet rs = conn.createStatement().executeQuery(query);
            
            
            while(rs.next())
            {
                quickSelection.add(rs.getString("make"));
                quickSel.setItems(quickSelection);
                
            }

            rs.close();
            conn.close();
        }
        
        catch(SQLException e)
        {
            alertError("Error on loading vehicles for quick selection.");
        }
    }
   
    //Method which error checks the textfields and makes sure that there are no empty fields.
    public boolean checkTextFields()
    {
        boolean checked = true;
        if(vehicleChoice.getSelectionModel().isEmpty() || make.getText().equals("") || model.getText().equals("") || engSize.getText().equals("") || fuelType.getSelectionModel().isEmpty() || colour.getText().equals("") || motRenDate.getEditor().getText().equals("") || lastService.getEditor().getText().equals("") || mileage.getText().equals(""))
        {
            checked = false;
            alertInf("Please complete all fields.");
        }
        
        if(customerNames.getSelectionModel().isEmpty())
        {
            checked = false;
            alertInf("Please specify the name associated with this vehicle.");
        }
        
        if(yesWarranty.isSelected() && nameAndAdd.getText().equals("") && warExpiry.getEditor().getText().equals(""))
        {
            alertInf("Please enter the name, address and expiry date for the warranty.");
            checked = false;
        }
        
        if(!(yesWarranty.isSelected() || noWarranty.isSelected()))
        {
            alertInf("Please select if the vehicle is under warranty or not.");
            checked = false;
        }
        return checked;
    }
    
    public boolean checkForWhiteSpace()
    {
        boolean checked = true;
        if(regNumber.getText().trim().isEmpty() || make.getText().trim().isEmpty() || model.getText().trim().isEmpty() || engSize.getText().trim().isEmpty() || colour.getText().trim().isEmpty() || nameAndAdd.getText().trim().isEmpty())
         {
             alertInf("You cannot have a white space at the start of the textfield");
             checked = false;
         }
        return checked;
    }
    //Method which sets the no checkbox to unselected when yes checkbox is checked
    @FXML
    private void handleYesBox()
    {
        if(yesWarranty.isSelected())
        {
            noWarranty.setSelected(false); 
        }
    }
    
    //Method which sets the yes checkbox to unselected when no checkbox is checked
    @FXML
    private void handleNoBox()
    {
        if(noWarranty.isSelected())
        {
            yesWarranty.setSelected(false);
        }
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
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
