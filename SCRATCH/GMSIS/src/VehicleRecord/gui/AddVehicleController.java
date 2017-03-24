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
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
        // TODO
        
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
        restrictDecimal(engSize);
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
                    String query = "select make,model,engSize,fuelType from quickSelection where template = ?";
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
        id.clear();
        customerNames.setValue(null);
        custID.clear();
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

        if(!checkTextFields())
        {      
            return;
        }
        
        if(!checkForWhiteSpace())
        {
            return;
        }
        
        if(!decimalPlaces(Double.parseDouble(engSize.getText())))
        {
            return;
        }
        if(checkIfVehicleAlreadyExists())
        {
            alertInf("Vehicle already exists with registration number " + vec.getRegNumber());
            return;
        }
        
            double eSize = Double.parseDouble(engSize.getText());
            vec.setRegNumber(regNumber.getText());
            vec.setColour(colour.getText());
            vec.setEngSize(eSize);
            vec.setFuelType((String) fuelType.getValue());
            vec.setLastService(((TextField) lastService.getEditor()).getText());
            vec.setMake(make.getText());
            vec.setMileage(Integer.parseInt(mileage.getText()));
            vec.setModel(model.getText());
            vec.setMotRenewal(((TextField)motRenDate.getEditor()).getText());
            vec.setVehicleType((String) vehicleChoice.getValue());
            vec.setWarNameAndAdd(nameAndAdd.getText());
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
        
    
    
    //Method which allows the user to make changes to the vehicle they created
    @FXML
    public void updateButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to edit this vehicle?");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
        
        if(!checkTextFieldEdit())
        {      
            return;
        }
        
        if(!checkForWhiteSpace())
        {
            return;
        }
        
        if(!decimalPlaces(Double.parseDouble(engSize.getText())))
        {
            return;
        }
            vec.setRegNumber(regNumber.getText());
            vec.setColour(colour.getText());
            vec.setEngSize(Double.parseDouble(engSize.getText()));
            vec.setFuelType((String) fuelType.getValue());
            vec.setLastService(((TextField) lastService.getEditor()).getText());
            vec.setMake(make.getText());
            vec.setMileage(Integer.parseInt(mileage.getText()));
            vec.setModel(model.getText());
            vec.setMotRenewal(((TextField)motRenDate.getEditor()).getText());
            vec.setVehicleType((String) vehicleChoice.getValue());
            vec.setWarNameAndAdd(nameAndAdd.getText());
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
            System.out.println("Opened Database Successfully");
            
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
    public void editVehicle(Vehicle vec) throws ClassNotFoundException
    {
        Connection conn = null;

        try 
        {
            
            conn = (new sqlite().connect());

            System.out.println("Opened Database Successfully");

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
    public boolean checkTextFields()
    {
        if(vehicleChoice.getValue()==null || regNumber.getText().equals("") ||  make.getText().equals("") || model.getText().equals("") || engSize.getText().equals("") || fuelType.getValue()==null || colour.getText().equals("") || motRenDate.getEditor().getText().equals("") || lastService.getEditor().getText().equals("") || mileage.getText().equals(""))
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
        
        if(noWarranty.isSelected() && nameAndAdd.getText().equals("") && warExpiry.getEditor().getText().equals(""))
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
    
    public boolean checkTextFieldEdit()
    {
        System.out.println(vehicleChoice.getValue()==null);
        System.out.println(regNumber.getText().equals(""));
        System.out.println(make.getText().equals(""));
        System.out.println(model.getText().equals(""));
        System.out.println(engSize.getText().equals(""));
        System.out.println(fuelType.getValue()==null);
        System.out.println(colour.getText().equals(""));
        System.out.println(motRenDate.getEditor().getText().equals(""));
        System.out.println(lastService.getEditor().getText().equals(""));
        System.out.println(mileage.getText().equals(""));
        
        if(vehicleChoice.getValue()==null || regNumber.getText().equals("") ||  make.getText().equals("") || model.getText().equals("") || engSize.getText().equals("") || fuelType.getValue()==null || colour.getText().equals("") || motRenDate.getEditor().getText().equals("") || lastService.getEditor().getText().equals("") || mileage.getText().equals(""))
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
    
    public boolean checkForWhiteSpace()
    {
        if(regNumber.getText().trim().isEmpty() || make.getText().trim().isEmpty() || model.getText().trim().isEmpty() || engSize.getText().trim().isEmpty() || colour.getText().trim().isEmpty() || yesWarranty.isSelected() && nameAndAdd.getText().trim().isEmpty())
         {
             alertInf("You cannot have a white space at the start of the textfield");
             return false;
         }
        return true;
    }
    
    public boolean decimalPlaces(double num) 
    {
        String numstr = Double.toString(num);
        String[] strarray = numstr.split("[.]");
        if (strarray.length == 2)
        {
            if (strarray[1].length() > 2)
            {
                alertInf("Only enter upto 2 decimal places.");
                return false;
            }
        }
        return true;
    }
    private void restrictDecimal(TextField field)
    {
        DecimalFormat format = new DecimalFormat( "#.0" );
        field.setTextFormatter( new TextFormatter<>(input ->
        {
        if ( input.getControlNewText().isEmpty() )
        {
            return input;
        }
        ParsePosition parsePosition = new ParsePosition( 0 );
        Object object = format.parse( input.getControlNewText(), parsePosition );
        if ( object == null || parsePosition.getIndex() < input.getControlNewText().length() )
        {
            return null;
        }
        else
        {
            return input;
        }
        }));
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
    
    public LocalDate convert(String string)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(string, formatter);
        return localDate;
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
