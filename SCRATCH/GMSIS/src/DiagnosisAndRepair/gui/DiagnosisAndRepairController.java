/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.gui;

import DiagnosisAndRepair.logic.DiagnosisAndRepairBooking;
import Authentication.Home;
import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Bayzid
 */
public class DiagnosisAndRepairController implements Initializable {

    
    @FXML 
    private DatePicker datePicked;
    @FXML
    private ComboBox<String> customerCombo;
    @FXML
    private ComboBox<String> vehicleCombo;
    @FXML
    private ComboBox<String> mechanicCombo;
    @FXML
    private ComboBox<String> partsCombo;
    @FXML
    private ComboBox<String> startTime;
    @FXML
    private ComboBox<String> endTime;
    
    @FXML
    private TextArea selectedParts;
    
    @FXML 
    private TableView<DiagnosisAndRepairBooking> table;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> bookingIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> custIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> vehicleIDCol;
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
    
    private ObservableList<DiagnosisAndRepairBooking> data;
    
    ObservableList<String> custNames = FXCollections.observableArrayList();
    ObservableList<String> vehicleReg = FXCollections.observableArrayList();
    ObservableList<String> parts = FXCollections.observableArrayList();
    ObservableList<String> mechNames = FXCollections.observableArrayList();
    ObservableList<String> startTimeLs ;//FXCollections.observableArrayList();
    ObservableList<String> endTimeLs ; //FXCollections.observableArrayList();
    
     ArrayList<String> listPart = null;
    
     DiagnosisAndRepairBooking obj = new DiagnosisAndRepairBooking("","","","","",0,"",0);
     
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
  
       fillStartTimeCombo();
       fillEndTimeCombo(); 
    try
    {
        fillCustomerCombo();
        fillMechanicCombo();
        fillPartsCombo();
        
    }
        catch(ClassNotFoundException e)
    {
        e.printStackTrace(); 
    }
   
    
    
    }    
    
    @FXML
    public void backButton(ActionEvent event) throws IOException 
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();      
    }
    
    private void fillCustomerCombo() throws ClassNotFoundException
    {
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select customer_fullname from customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                custNames.add(rs.getString("customer_fullname"));    
            }
            customerCombo.setItems(custNames);
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    
    @FXML
    private void findVehicleOnChange(ActionEvent event) throws ClassNotFoundException
    {
        String custID = findCustID(customerCombo.getValue());
        
        vehicleReg.clear();
        vehicleCombo.setItems(vehicleReg);
        
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select RegNumber from vehicleList where customerid='" + custID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next())
            {       
                vehicleReg.add(rs.getString("RegNumber"));    
            }
            vehicleCombo.setItems(vehicleReg);
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    
    private String findCustName(String custID) throws ClassNotFoundException
    {
        String custName = "";
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select customer_fullname from customer where customer_id='"+ custID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                custName = rs.getString("customer_fullname");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return custName;
    }
    
    private String findVehReg(String vehID) throws ClassNotFoundException
    {
        String vehReg = "";
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select RegNumber from vehicleList where vehicleID='"+ vehID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                vehReg = rs.getString("RegNumber");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehReg;
    }
    
    private String findMechName(String mechID) throws ClassNotFoundException
    {
        String mechName = "";
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select fullname from mechanic where mechanic_id='"+ mechID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                mechName = rs.getString("fullname");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mechName;
    }
    
    private String findCustID(String custName) throws ClassNotFoundException
    {
        String custID = "";
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select customer_id from customer where customer_fullname='"+ custName +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                custID = rs.getString("customer_id");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return custID;
    }
    
    private String findMechID(String mechName) throws ClassNotFoundException
    {
        String mechID = "";
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select mechanic_id from mechanic where fullname='"+ mechName +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                mechID = rs.getString("mechanic_id");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mechID;
    }
    
     private String findVehID(String vehReg) throws ClassNotFoundException
    {
        String vehRegID = "";
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select vehicleID from vehicleList where RegNumber='"+ vehReg +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                vehRegID = rs.getString("vehicleID");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehRegID;
    }
    
    
    private void fillVehicleCombo() throws ClassNotFoundException
    {
        
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select RegNumber from vehicleList";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next())
            {       
                vehicleReg.add(rs.getString("RegNumber"));    
            }
            vehicleCombo.setItems(vehicleReg);
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    
    private void fillMechanicCombo() throws ClassNotFoundException
    {
          Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select fullname from mechanic";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                mechNames.add(rs.getString("fullname"));    
            }
            mechanicCombo.setItems(mechNames);
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    
    private void fillPartsCombo() throws ClassNotFoundException
    {
         Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select nameofPart from vehiclePartsStock";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                parts.add(rs.getString("nameofPart"));    
            }
            partsCombo.setItems(parts);
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    
    }
    
    @FXML 
    private void updateBooking(ActionEvent event) throws ClassNotFoundException
    {
        
    }
    
    @FXML
    private void booking(ActionEvent event) throws IOException, ClassNotFoundException
    {
        obj.setCustName(customerCombo.getValue());
        obj.setVehicleReg(vehicleCombo.getValue());
        obj.setMechanicName(mechanicCombo.getValue());
       // obj.setPartsRequired(parts);
        obj.setDate(((TextField)datePicked.getEditor()).getText());
        obj.setDuration(calculateDuration(startTime.getValue(),endTime.getValue()));
        createBooking(obj);
        buildBooking();
        clearFields();
    }
    
    private void clearFields()
    {
        customerCombo.setValue(null);
        vehicleCombo.setValue(null);
        mechanicCombo.setValue(null);
        partsCombo.setValue(null);
        startTime.setValue(null);
        endTime.setValue(null);
        selectedParts.clear();
        ((TextField)datePicked.getEditor()).clear();
    }
    
    @FXML 
    private void partsChanged(ActionEvent event) throws IOException, ClassNotFoundException
    {
       String ls = "";
        
        if(!listPart.contains(partsCombo.getValue()) && listPart.size()<10)
        {
            listPart.add(partsCombo.getValue());
        }
        
        for(String i: listPart)
        {
            ls = ls + i+", ";
        }
        selectedParts.setText(ls);
    }
    
    @FXML
    private void loadBookings(ActionEvent event) throws ClassNotFoundException
    {
        buildBooking();
    }
    
    @FXML
    private void editBooking(ActionEvent event)
    {
        String ID = table.getSelectionModel().getSelectedItem().getBookingID();       
        String reg = table.getSelectionModel().getSelectedItem().getVehicleReg();      
        String custName = table.getSelectionModel().getSelectedItem().getCustName();     
        String mechName = table.getSelectionModel().getSelectedItem().getMechanicName();     
        //String parts = table.getSelectionModel().getSelectedItem().getVehicleReg();
        String date = table.getSelectionModel().getSelectedItem().getDate();
        
        customerCombo.setValue(findComboVal(custNames,custName));
        mechanicCombo.setValue(findComboVal(mechNames,mechName));
        vehicleCombo.setValue(findComboVal(vehicleReg,reg));
       // datePicked.((TextField)datePicked.getEditor()).getText();
    }
    
    private String findComboVal(ObservableList<String> list, String target)
    {
        for(String i: list)
        {
            if(i.equals(target))
            {
                return i;
            }
        }
        return null;
    }
    
    @FXML 
    private void deleteBooking(ActionEvent event) throws ClassNotFoundException
    {
        String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this booking? (Yes or No) ");
        String id = table.getSelectionModel().getSelectedItem().getBookingID();
        if(confirmDelete.equalsIgnoreCase("Yes") && isBookingDeleted(obj))
        {            
            JOptionPane.showMessageDialog(null, "Booking ID " + id + " has been deleted."); 
            buildBooking();
        }
    }
    
    private boolean isBookingDeleted(DiagnosisAndRepairBooking obj) throws ClassNotFoundException
    {
        boolean bookingDeleted = false;
        
        String ID = table.getSelectionModel().getSelectedItem().getBookingID();
        
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM booking WHERE booking_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, obj.getBookingID());
            state.executeUpdate();
            
            state.close();
            conn.close();
            
            bookingDeleted = true;
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return bookingDeleted;   
            
    }

    
    private void createBooking(DiagnosisAndRepairBooking obj) throws ClassNotFoundException
    {
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            String sql = "insert into booking(vehicleID,customer_id,mechanic_id,scheduled_date,duration,partsRequired,mileage) values(?,?,?,?,?,?,?)";
           
            PreparedStatement state = conn.prepareStatement(sql);
          
            state.setString(1, findVehID(obj.getVehicleReg()));
            state.setString(2, findCustID(obj.getCustName()));
            state.setString(3, findMechID(obj.getMechanicName()));
              
            state.setString(4, obj.getDate());
           
             state.setInt(5, obj.getDuration());
             state.setString(6, selectedParts.getText());
             
             String getMileage =  "Select Mileage from vehicleList where vehicleID='"+ findVehID(obj.getVehicleReg()) +"'";
             ResultSet rs = conn.createStatement().executeQuery(getMileage);
             state.setString(7, rs.getString("Mileage"));
             
  
            state.execute();
            
            rs.close();
            state.close();
            conn.close();
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
    }
    
    private void buildBooking() throws ClassNotFoundException
    {
        data = FXCollections.observableArrayList();
        
    Connection conn = null;
    try{      
        
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        
        String SQL = "Select * from booking";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next())
        {
            
            data.add(new DiagnosisAndRepairBooking(rs.getString(1), findVehReg(rs.getString(2)), findCustName(rs.getString(3)), findMechName(rs.getString(4)) ,rs.getString(5),rs.getInt(6),rs.getString(7),rs.getDouble(8)));
          
        }
        table.setItems(data);
        
        rs.close();
        conn.close();
        
          bookingIDCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("bookingID"));         
    vehicleIDCol.setCellValueFactory(                
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("vehicleReg"));
    custIDCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("custName"));
    mechanicIDCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("mechanicName"));        
    partsRequiredCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("partsRequired"));
    mileageCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,Double>("mileage"));        
    dateCol.setCellValueFactory(                
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("date"));
    durationCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,Integer>("duration"));
        
        
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
    }
    
    private void fillStartTimeCombo()
    {
        startTimeLs = FXCollections.observableArrayList("09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30");
        startTime.setItems(startTimeLs);
    }
    
    private void fillEndTimeCombo()
    {
        endTimeLs = FXCollections.observableArrayList("09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00");
        endTime.setItems(endTimeLs);
    }
    
    private int calculateDuration(String sTime, String eTime)
     {
        
        String[] arr1 = new String[2];
        String[] arr2 = new String[2];
       
        arr1 = sTime.split(":"); 
        arr2 = eTime.split(":");
        
        int duration=0;
        
        int startHour = Integer.parseInt(arr1[0]);
        int endHour = Integer.parseInt(arr2[0]);
  
        int startMin = Integer.parseInt(arr1[1]);
        int endMin = Integer.parseInt(arr2[1]);
        
        if(startMin == endMin)
        {
            duration += (endHour-startHour) * 60;
        }
        else
        {   
            duration += ((endHour-startHour)-1) * 60;                     
            duration +=(60-startMin);        
            duration += endMin;  
        }
        return duration;
    }  
}
