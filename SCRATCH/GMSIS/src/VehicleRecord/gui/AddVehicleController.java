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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author Hamza Busuri
 */
public class AddVehicleController implements Initializable {

  
    
    // Declaring all observable array lists
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
    public ComboBox customerNames;
    @FXML
    public ComboBox vehicleChoice;
    @FXML
    public CheckBox yesWarranty;
    @FXML
    public CheckBox noWarranty;
    @FXML
    public TextArea nameAndAdd;
    @FXML
    public ComboBox quickSel;
    @FXML
    public TextField regNumber;
    @FXML
    public TextField make;
    @FXML
    public TextField model;
    @FXML
    public TextField engSize;
    @FXML
    public ComboBox fuelType;
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
    public static Vehicle vec = new Vehicle("","","",0.0,"","","","",0,"","","","",0,0,"");
   
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            buildData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vehicleChoice.setOnAction(e -> {
            try {
                fillQuickSelection();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddVehicleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        warExpiry.setEditable(false);
        motRenDate.setEditable(false);
        lastService.setEditable(false);
        vec.restrictDecimal(engSize);
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
        
        /**
         * Method callbacks which restricts the datepicker ~ referenced from stackoverflow
         */
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

        Callback<DatePicker, DateCell> dayCellFactory2 = dp2 -> new DateCell() 
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
        warExpiry.setDayCellFactory(dayCellFactory2);
        
        try {
            
             customerNames.setOnAction(e ->{ //Customer names show up on the database into a choicebox for the user to select
                Connection conn5 = null;
                ResultSet rs5 = null;
                try
                {
                    conn5 = (new sqlite().connect());
                    String query = "select customer_id, customer_fullname from customer where customer_fullname = '" + (String) customerNames.getSelectionModel().getSelectedItem() + "'";
                    rs5 = conn5.createStatement().executeQuery(query);

                    while(rs5.next())
                    {
                        custID.setText(String.valueOf(rs5.getInt("customer_id")));
                    }
                    conn5.close();
                    rs5.close();
                }
                
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
        });
        quickSel.setOnAction(e ->{ // Lists vehicles so that the user can quick select a vehicle by make and model
            
                Connection conn = null;
                ResultSet rs = null;
                try
                {
                    conn = (new sqlite().connect());
                    String query = "select make,model,engSize,fuelType from quickSelection where template = '"+ quickSel.getSelectionModel().getSelectedItem() + "'";
                    rs = conn.createStatement().executeQuery(query);
                   // rs = ps.executeQuery();
                    
                    while(rs.next())
                    {
                        make.setText(rs.getString("make"));
                        model.setText(rs.getString("model"));
                        engSize.setText(rs.getString("engSize"));
                        fuelType.setValue(rs.getString("fuelType"));
                    }
    
                    conn.close();
                    rs.close();
                }
                
                catch(Exception ex)
                {
                     ex.printStackTrace();
                }
            
        });
   
        }
        catch(Exception e)
        {
            alertError("Error on building data.");
        }
    }
    
    @FXML 
    private void logout(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Login.fxml"));
        pane.getChildren().setAll(rootPane);
        Authentication.LoginController.isAdmin = false;
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
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to clear all?");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
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
        if(id.isEditable())
        {
           id.clear();
        }
        if(!customerNames.isDisable())
        {
            customerNames.setValue(null);
        }
        if(custID.isEditable())
        {
           custID.clear();
        }
        if(!(yesWarranty.isSelected() && noWarranty.isSelected()))
        {
            nameAndAdd.setVisible(true);
            warExpiry.setVisible(true);
            warrantyName.setVisible(true);
            warrantyDate.setVisible(true);
        }
    }
    
    
    // Method which allows the user to add a vehicle for a specified customer.
    @FXML
    public void addEntry(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to add vehicle
    {
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to add this vehicle?");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }

        if(!checkIndividual())
        {      
            return;
        }


        
        if(!vec.decimalPlaces(Double.parseDouble(engSize.getText())))
        {
            return;
        }
        if(checkIfVehicleAlreadyExists())
        {
            alertInf("Vehicle already exists with registration number " + vec.getRegNumber());
            return;
        }
        if(!(regNumber.getText().trim().isEmpty() || colour.getText().trim().isEmpty() || make.getText().trim().isEmpty() || model.getText().trim().isEmpty() || yesWarranty.isSelected() && nameAndAdd.getText().trim().isEmpty() ))
        {
            double eSize = Double.parseDouble(engSize.getText());
            vec.setRegNumber(regNumber.getText().trim());
            vec.setColour(colour.getText().trim());
            vec.setEngSize(eSize);
            vec.setFuelType((String) fuelType.getValue());
            vec.setLastService(((TextField) lastService.getEditor()).getText());
            vec.setMake(make.getText().trim());
            vec.setMileage(Integer.parseInt(mileage.getText()));
            vec.setModel(model.getText().trim());
            vec.setMotRenewal(((TextField)motRenDate.getEditor()).getText());
            vec.setVehicleType((String) vehicleChoice.getValue());
            vec.setWarNameAndAdd(nameAndAdd.getText().trim());
            vec.setWarrantyExpDate(((TextField) warExpiry.getEditor()).getText());
            vec.setCustID(Integer.parseInt(custID.getText()));
            vec.setCustName((String) customerNames.getValue());
            createData(vec);
            alertInf("Vehicle ID: " + getVehicleID() + " has been added for " + customerNames.getSelectionModel().getSelectedItem());
            buildData();
            Parent vecRecords = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
            Scene vec_Scene = new Scene(vecRecords);
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();           
            stage2.setScene(vec_Scene);
            stage2.show();
        }
        
        else
        {
            alertInf("No whitespaces allowed.");
        
        }
        
    }   
    
    //Method which allows the user to make changes to the vehicle they created
    @FXML
    public void updateButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to edit this vehicle?");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
        
        if(!checkIndividual())
        {      
            return;
        }

        
        if(!vec.decimalPlaces(Double.parseDouble(engSize.getText())))
        {
            return;
        }
        
        if(!(regNumber.getText().trim().isEmpty() || colour.getText().trim().isEmpty() || make.getText().trim().isEmpty() || model.getText().trim().isEmpty() || yesWarranty.isSelected() && nameAndAdd.getText().trim().isEmpty() ))
        {
            
            vec.setRegNumber(regNumber.getText().trim());
            vec.setColour(colour.getText().trim());
            vec.setEngSize(Double.parseDouble(engSize.getText()));
            vec.setFuelType((String) fuelType.getValue());
            vec.setLastService(((TextField) lastService.getEditor()).getText());
            vec.setMake(make.getText().trim());
            vec.setMileage(Integer.parseInt(mileage.getText()));
            vec.setModel(model.getText().trim());
            vec.setMotRenewal(((TextField)motRenDate.getEditor()).getText());
            vec.setVehicleType((String) vehicleChoice.getValue());
            vec.setWarNameAndAdd(nameAndAdd.getText().trim());
            vec.setWarrantyExpDate(((TextField) warExpiry.getEditor()).getText());
            vec.setCustID(Integer.parseInt(custID.getText()));
            vec.setCustName((String) customerNames.getValue());
            vec.setVecID(Integer.parseInt(id.getText()));
        
            editVehicle(vec);
            alertInf("Vehicle ID: " + getVehicleID() + " has been updated for " + customerNames.getSelectionModel().getSelectedItem());
            Parent vecRecords = FXMLLoader.load(getClass().getResource("Vehicle.fxml"));
            Scene vec_Scene = new Scene(vecRecords);
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();           
            stage2.setScene(vec_Scene);
            stage2.show();
        }
        else
        {
            alertInf("No whitespaces allowed.");
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
    public void createData(Vehicle vec) throws ClassNotFoundException
    {    
        Connection conn = null;      
        try
        {
            conn = (new sqlite().connect());
            
            String sql = "insert into vehicleList(RegNumber,Make,Model,EngSize,FuelType,Colour,MOTDate,LastServiceDate,Mileage,VehicleType,Warranty,WarrantyNameAndAdd,WarrantyExpDate,customerid ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, vec.getRegNumber());
            state.setString(2, vec.getMake());
            state.setString(3, vec.getModel());
            state.setDouble(4, vec.getEngSize());
            state.setString(5, vec.getFuelType());
            state.setString(6, vec.getColour());
            state.setString(7, vec.getMotRenewal());
            state.setString(8, vec.getLastService());
            state.setInt(9, vec.getMileage());
            state.setString(10, vec.getVehicleType());
            if(yesWarranty.isSelected())
            {
            state.setString(11, "Yes");
            }
            else
            {
                state.setString(11, "No");
            }
            state.setString(12, vec.getWarNameAndAdd());
            state.setString(13, vec.getWarrantyExpDate());
            state.setInt(14, vec.getCustID());
            state.execute();
            
            state.close();
            conn.close();
            
        }
        catch(SQLException e)
        {
            alertError("Error on creating vehicle");
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
            stmt = conn.prepareStatement("SELECT Count(RegNumber) from vehicleList WHERE RegNumber=?");
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
                    conn.close();
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
                     conn.close();
                 }
                 catch(SQLException e) 
                 {
                     e.printStackTrace();
                 }
            }     
             
        }  
         
    }
    
    //Method which updates the vehicle changes into the database
    public void editVehicle(Vehicle vec) throws ClassNotFoundException
    {
        Connection conn = null;

        try 
        {
            
            conn = (new sqlite().connect());

            String sql = "UPDATE vehicleList SET RegNumber=?,Make=?,Model=?,EngSize=?,FuelType=?,Colour=?,MOTDate=?, LastServiceDate=?,Mileage=?,VehicleType=?,Warranty=?,WarrantyNameAndAdd=?,WarrantyExpDate=? WHERE vehicleID=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, vec.getRegNumber());
            state.setString(2, vec.getMake());
            state.setString(3, vec.getModel());
            state.setDouble(4, vec.getEngSize());
            state.setString(5, vec.getFuelType());
            state.setString(6, vec.getColour());
            state.setString(7, vec.getMotRenewal());
            state.setString(8, vec.getLastService());
            state.setInt(9, vec.getMileage());
            state.setString(10, vec.getVehicleType());
            if(yesWarranty.isSelected())
            {
            state.setString(11, "Yes");
            }
            else
            {
                state.setString(11, "No");
            }
            state.setString(12, vec.getWarNameAndAdd());
            state.setString(13, vec.getWarrantyExpDate());
            state.setInt(14, vec.getVecID());

            state.execute();

            state.close();
            conn.close();

        } 
        catch (SQLException e) 
        {
            alertInf("Error on editing vehicle");
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
            alertError("Error on getting vehicle ID.");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return vecid;
        
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
            alertError("Error filling customer names");
        }
    }
    
    //Method which fills the quick selection combo box with vehicles from the database
    public void fillQuickSelection() throws ClassNotFoundException
    {
        quickSelection.clear();
        Connection conn=null;
        try
        {
            conn = (new sqlite().connect());
            String query = "Select template from quickSelection where vecType = '" + vehicleChoice.getValue() + "'";

            ResultSet rs = conn.createStatement().executeQuery(query);
            
            
            while(rs.next())
            {
                quickSelection.add(rs.getString("template"));
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
  
    
    public boolean checkIndividual()
    {
        
        if(vehicleChoice.getValue()==null)
        {
            alertInf("Please complete all fields.");
            return false;
        }
  
        if(regNumber.getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(make.getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(model.getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(engSize.getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(fuelType.getValue()==null)
        {
            alertInf("Please complete all fields.");
            return false;         
        }
        
        if(colour.getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(motRenDate.getEditor().getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(lastService.getEditor().getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(mileage.getText().equals(""))
        {
            alertInf("Please complete all fields.");
            return false;
        }
        
        if(yesWarranty.isSelected() && nameAndAdd.getText().equals(""))
        {
            alertInf("Please enter the name and address for the warranty.");
            return false;
        }
        
        if(yesWarranty.isSelected() && nameAndAdd.getText().equals("") && warExpiry.getEditor().getText().equals(""))
        {
            alertInf("Please enter the name, address and expiry date for the warranty.");
            return false;
        }
        
        if(yesWarranty.isSelected() && warExpiry.getEditor().getText().equals(""))
        {
            alertInf("Please enter the expiry date for the warranty.");
            return false;
        }
        
        if(noWarranty.isSelected() && nameAndAdd.getText().equals(""))
        {
            return true;
        }
        
        if(noWarranty.isSelected() && warExpiry.getEditor().getText().equals(""))
        {
            return true;
        }
        
        if(!(yesWarranty.isSelected() || noWarranty.isSelected()))
        {
            alertInf("Please select if the vehicle is under warranty or not.");
            return false;
        }
        return true;
    }
    
    private boolean validatePartName(){
        Pattern p  = Pattern.compile("^[ A-z]+$");
        Matcher m = p.matcher(regNumber.getText());
        if(m.find() && m.group().equals(regNumber.getText())){
            return true;
        }
            return false;
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
    
    private Optional<ButtonType> alertConfirm(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
	alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
    
    
    
}
