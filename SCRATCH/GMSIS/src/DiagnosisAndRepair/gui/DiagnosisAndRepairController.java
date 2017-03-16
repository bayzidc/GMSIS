/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.gui;

import Authentication.sqlite;
import DiagnosisAndRepair.logic.DiagnosisAndRepairBooking;
import DiagnosisAndRepair.logic.PartsInfo;
import DiagnosisAndRepair.logic.Mechanic;
import Authentication.Home;
import CustomerAccount.gui.BillController;
import DiagnosisAndRepair.logic.Mechanic;
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
import javax.swing.*;


import java.time.DayOfWeek;
import java.net.URL;
import java.time.*;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.util.Calendar;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
/**
 * FXML Controller class
 *
 * @author Bayzid
 */
public class DiagnosisAndRepairController implements Initializable {

    
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
    

    @FXML
    private Button book;
    @FXML
    private Button update;
    @FXML
    private Button exitEditB;
    @FXML 
    private DatePicker datePicked;
    @FXML
    private ComboBox<String> customerCombo;
    @FXML
    private ComboBox<String> vehicleCombo;
    @FXML
    private ComboBox<String> mechanicCombo;
    @FXML
    private ComboBox<String> startTime;
    @FXML
    private ComboBox<String> endTime;
    @FXML
    private TextField searchField;
    @FXML
    private TextField mileage;
    @FXML
    private CheckBox bHour;
    @FXML
    private CheckBox bToday;
    @FXML
    private CheckBox bMonth;
    @FXML
    private CheckBox nBooking;
    @FXML
    private CheckBox pBooking;
    @FXML
    private CheckBox fBooking;
    @FXML
    private CheckBox allBooking;

    

    @FXML 
    private TableView<DiagnosisAndRepairBooking> table;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> bookingIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> custIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> vehicleIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> makeCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> mechanicIDCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, Double> mileageCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> dateCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, Integer> durationCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> startTimeCol;
    @FXML 
    private TableColumn<DiagnosisAndRepairBooking, String> endTimeCol;
    
    private ObservableList<DiagnosisAndRepairBooking> data;
    
    

    
    private ObservableList<DiagnosisAndRepairBooking> tempData = FXCollections.observableArrayList();
    
     
    @FXML 
    private TableView<PartsInfo> partsTable;
    @FXML 
    private TableColumn<PartsInfo, String> partCol;
    @FXML 
    private TableColumn<PartsInfo, Integer> quanCol;
    @FXML 
    private TableColumn<PartsInfo, String> expiryCol;
    
    private ObservableList<PartsInfo> data3;
     
     
    ObservableList<Integer> vehID = FXCollections.observableArrayList();
    private ObservableList<String> custNames = FXCollections.observableArrayList();
    private ObservableList<String> vehicleReg = FXCollections.observableArrayList();
    private ObservableList<String> mechNames = FXCollections.observableArrayList();
    private ObservableList<String> startTimeLs = FXCollections.observableArrayList("09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00");
    private ObservableList<String> endTimeLs = FXCollections.observableArrayList("09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30");

    
    //private ObservableList<String> times = FXCollections.observableArrayList("09:00 to 09:30","09:30 to 10:00","10:00 to 10:30","10:30 to 11:00","11:00 to 11:30","11:30 to 12:00","12:00 to 12:30","12:30 to 13:00","13:00 to 13:30","13:30 to 14:00","14:00 to 14:30","14:30 to 15:00","15:00 to 15:30","15:30 to 16:00","16:00 to 16:30","16:30 to 17:00");
    

    private DiagnosisAndRepairBooking obj = new DiagnosisAndRepairBooking(0,"","","","","",0,0,"","");
     
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        update.setVisible(false);
        exitEditB.setVisible(false);
        allBooking.setSelected(true);
        
        bookingIDCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("bookingID"));         
    vehicleIDCol.setCellValueFactory(                
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("vehicleReg"));
    makeCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("make"));
    custIDCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("custName"));
    mechanicIDCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("mechanicName"));        
    mileageCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,Double>("mileage"));        
    dateCol.setCellValueFactory(                
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("date"));
    durationCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,Integer>("duration"));
    startTimeCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("startTime"));  
    endTimeCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("endTime"));
    
   
    partCol.setCellValueFactory(                
        new PropertyValueFactory<PartsInfo,String>("partName"));
    quanCol.setCellValueFactory(
        new PropertyValueFactory<PartsInfo,Integer>("quantity"));
    expiryCol.setCellValueFactory(
        new PropertyValueFactory<PartsInfo,String>("expiryDate")); 
            
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()) || item.getDayOfWeek() == DayOfWeek.SUNDAY || item.equals(getBankHoliday("gFriday")) || 
                        item.equals(getBankHoliday("easterMonday")) || item.equals(getBankHoliday("earlyMay")) || item.equals(getBankHoliday("spring")) || 
                        item.equals(getBankHoliday("summer")) || item.equals(getBankHoliday("christmas")) || item.equals(getBankHoliday("boxing")))
                {
                    setStyle("-fx-background-color: #fcbabf;");
                    Platform.runLater(() -> setDisable(true));                 
                }
            }
        };
        datePicked.setDayCellFactory(dayCellFactory);
        
        
        
    try
    {
        
        fillCustomerCombo();
        fillMechanicCombo();
        buildBooking();
    }
        catch(ClassNotFoundException e)
    {
        e.printStackTrace(); 
    }
    
    
        if(!Authentication.LoginController.isAdmin)
        {
            users.setVisible(false);
        }
    
    }    

    @FXML 
    private void logout(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Login.fxml"));
        pane.getChildren().setAll(rootPane);
        Authentication.LoginController.isAdmin = false;
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
    
    
    
    
    private boolean checkSaturday(LocalDate item)
    {
            if(item.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                return true;
            }
            return false;
    }
    
    private LocalDate getBankHoliday(String item)
    {    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(item.equals("gFriday"))
        {
            return LocalDate.parse("14/04/2017",formatter);
        }
        else if(item.equals("easterMonday"))
        {
            return LocalDate.parse("17/04/2017",formatter);
        }
        else if(item.equals("earlyMay"))
        {
            return LocalDate.parse("01/05/2017",formatter);
        }
        else if(item.equals("spring"))
        {
            return LocalDate.parse("29/05/2017",formatter);
        }
        else if(item.equals("summer"))
        {
            return LocalDate.parse("28/08/2017",formatter);
        }
        else if(item.equals("christmas"))
        {
            return LocalDate.parse("25/12/2017",formatter);
        }
        else if(item.equals("boxing"))
        {
            return LocalDate.parse("26/12/2017",formatter);
        }
       return null;    
    }
    
    @FXML
    public void backButton(ActionEvent event) throws IOException 
    {
        if (Authentication.LoginController.isAdmin) {
            Parent adminUser = FXMLLoader.load(getClass().getResource("/common/gui/common.fxml"));
            Scene admin_Scene = new Scene(adminUser);
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();
            stage2.setScene(admin_Scene);
            stage2.show();
        } else {
            Parent adminUser = FXMLLoader.load(getClass().getResource("/customer/gui/customer.fxml"));
            Scene admin_Scene = new Scene(adminUser);
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();
            stage2.setScene(admin_Scene);
            stage2.show();
        }
    }
    
    private void fillCustomerCombo() throws ClassNotFoundException
    {
        custNames.clear();
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select customer_fullname,customer_id from customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                custNames.add(rs.getInt("customer_id")+": "+rs.getString("customer_fullname"));    
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
        if(customerCombo.getValue()==null)
        {
            return;
        }
        
        String cust = customerCombo.getValue();
        String[] custArr = cust.split(": ");
        String custID = custArr[0];
        
        vehicleReg.clear();
        
        Connection conn = null;
        try {

            conn = (new sqlite().connect());
            
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
        
        if(vehicleReg.isEmpty())
        {
            alertInfo(null,"There are no vehicle for the selected customer");
        }
    }
    
    private String findCustName(String custID) throws ClassNotFoundException
    {
        String custName = "";
        Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select customer_fullname from customer where customer_id='"+ custID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                custName = rs.getString("customer_fullname");    
            
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

            conn = (new sqlite().connect());

            String SQL = "Select RegNumber from vehicleList where vehicleID='"+ vehID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                vehReg = rs.getString("RegNumber");    
           
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

           conn = (new sqlite().connect());

            String SQL = "Select fullname from mechanic where mechanic_id='"+ mechID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                mechName = rs.getString("fullname");    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mechName;
    }
    
    private String findCustID(String reg) throws ClassNotFoundException
    {
        String custID = "";
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select customerid from vehicleList where RegNumber='"+ reg +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
           
            custID = rs.getString("customerid");    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return custID;
    }
    
    private String findMechID(int bookingID) throws ClassNotFoundException
    {
        String mechID = "";
        Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select mechanic_id from booking where booking_id='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                mechID = rs.getString("mechanic_id");   
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mechID;
    }
    
     private int findVehID(String vehReg) throws ClassNotFoundException
    {
        int vehRegID = 0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select vehicleID from vehicleList where RegNumber='"+ vehReg +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                vehRegID = rs.getInt("vehicleID");   
            
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
        vehicleReg.clear();
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select RegNumber from vehicleList";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while(rs.next())
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

            conn = (new sqlite().connect());

            String SQL = "Select fullname,mechanic_id from mechanic";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                mechNames.add(rs.getInt("mechanic_id")+": "+rs.getString("fullname"));    
            }
            mechanicCombo.setItems(mechNames);
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    
    @FXML   //reset date when new mechanic selected
    private void resetDate(ActionEvent event)
    {
        datePicked.setValue(null);
    }
    
    @FXML 
    private void updateBooking(ActionEvent event) throws ClassNotFoundException
    {
        if(!checkIfCompleted())
        {
            alertError("Try again","Complete all fields");
            return;
        }
        
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to update this booking");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
        
        String cust = customerCombo.getValue();
        String[] custArr = cust.split(": ");
        obj.setCustName(custArr[1]);
        obj.setVehicleReg(vehicleCombo.getValue());
        String mech = mechanicCombo.getValue();
        String[] mechArr = mech.split(": ");
        obj.setMechanicName(mechArr[1]);
        obj.setDate(((TextField)datePicked.getEditor()).getText());
        obj.setDuration(calculateDuration(startTime.getValue(),endTime.getValue()));
        obj.setStartTime(startTime.getValue());
        obj.setEndTime(endTime.getValue());
        obj.setMileage(Double.parseDouble(mileage.getText()));
        
        updateData(obj,custArr,mechArr);
        buildBooking();
        fillCustomerCombo();
        clearFields();
        book.setVisible(true);
        exitEditB.setVisible(false);
        update.setVisible(false);
        customerCombo.setDisable(true);
        vehicleCombo.setDisable(true);
        addMechanicBill(obj.getBookingID(),findVehID(obj.getVehicleReg()),Integer.parseInt(custArr[0]),Integer.parseInt(mechArr[0]));
    }
    
    private void updateData(DiagnosisAndRepairBooking obj,String[] custArr, String[] mechArr) throws ClassNotFoundException
    {
         Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String sql = "UPDATE booking SET vehicleID=?,customer_id=?,mechanic_id=?,scheduled_date=?,duration=?,startTime=?,endTime=?,mileage=? WHERE booking_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
           
                state.setInt(1, findVehID(obj.getVehicleReg()));
                state.setString(2, custArr[0]);
                state.setString(3, mechArr[0]);
                state.setString(4, obj.getDate());
                state.setInt(5, obj.getDuration());
                state.setString(6, obj.getStartTime());
                state.setString(7, obj.getEndTime());
                state.setDouble(8, obj.getMileage());
                state.setInt(9, obj.getBookingID());
                
                state.execute();

                state.close();
                conn.close();
                
                alertInfo(null,"Update Successful");
                
            }
         catch (SQLException e) {
            System.out.println("");
        }

    }
    
    @FXML
    private void booking(ActionEvent event) throws IOException, ClassNotFoundException
    {
        if(!checkIfCompleted())
        {
            alertError("Try again","Complete all fields");
            return;
        }
        
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to submit this booking");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
        
        String cust = customerCombo.getValue();
        String[] custArr = cust.split(": ");
        obj.setCustName(custArr[1]);
        obj.setVehicleReg(vehicleCombo.getValue());
        String mech = mechanicCombo.getValue();
        String[] mechArr = mech.split(": ");
        obj.setMechanicName(mechArr[1]);
        obj.setDate(((TextField)datePicked.getEditor()).getText());
        obj.setDuration(calculateDuration(startTime.getValue(),endTime.getValue()));
        obj.setStartTime(startTime.getValue());
        obj.setEndTime(endTime.getValue());
        obj.setMileage(Double.parseDouble(mileage.getText()));
            
        createBooking(obj,custArr,mechArr);
        buildBooking();
        clearFields();
        addMechanicBill(recentBookingID(),findVehID(obj.getVehicleReg()),Integer.parseInt(custArr[0]),Integer.parseInt(mechArr[0]));
    }
    
    
    @FXML
    private void clearAll(ActionEvent event)
    {
        clearFields();
    }
    
    private void clearFields()
    {
        customerCombo.setValue(null);
        vehicleReg.clear();
        vehicleCombo.setValue(null);
        datePicked.setValue(null);
        ObservableList<String> tempClear = FXCollections.observableArrayList();
        startTime.setItems(tempClear);
        endTime.setItems(tempClear);
        mechanicCombo.setValue(null);
        datePicked.setValue(null);
        mileage.clear();
    }
    
    private boolean checkIfCompleted()
    {   
        if(customerCombo.getValue()!=null && vehicleCombo.getValue()!=null && mechanicCombo.getValue()!=null && datePicked!=null && startTime.getValue()!=null && endTime.getValue()!=null && mileage.getText()!=null)
        {
            return true;
        }
        return false;
    }
    
    @FXML
    private void editBooking(ActionEvent event) throws ClassNotFoundException
    {   
        int id;
        try
        {
            id = table.getSelectionModel().getSelectedItem().getBookingID();
        }
        catch(Exception e)
        {
            alertError(null,"Select a row first");
            return;
        }
        
        customerCombo.setDisable(true);
        vehicleCombo.setDisable(true);
        
        update.setVisible(true);
        book.setVisible(false);
        exitEditB.setVisible(true);
        
        obj.setBookingID(id);
        String reg = table.getSelectionModel().getSelectedItem().getVehicleReg();      
        String custName = table.getSelectionModel().getSelectedItem().getCustName();     
        String mechName = table.getSelectionModel().getSelectedItem().getMechanicName();     
        String date = table.getSelectionModel().getSelectedItem().getDate();
        String sTime = table.getSelectionModel().getSelectedItem().getStartTime();     
        String eTime = table.getSelectionModel().getSelectedItem().getEndTime();
        double mAge = table.getSelectionModel().getSelectedItem().getMileage();
        
        String cust = findCustID(reg)+": "+custName;
        System.out.println(cust);
        customerCombo.setValue(findComboVal(custNames,cust));  
        String mech = findMechID(obj.getBookingID())+": "+mechName;
        mechanicCombo.setValue(findComboVal(mechNames,mech));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
      
        datePicked.setValue(localDate); 
      
        vehicleCombo.setValue(findComboVal(vehicleReg,reg));  
        fillStartTimeCombo(); 
        startTime.setValue(findComboVal(startTimeLs,sTime));
        endTime.setValue(findComboVal(endTimeLs,eTime));
        mileage.setText(Double.toString(mAge));
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
        int id;
        try
        {
            id = table.getSelectionModel().getSelectedItem().getBookingID();
        }
        catch(Exception e)
        {
            alertError(null,"Select a row first");
            return;
        }
        
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to delete this booking");
        
        obj.setBookingID(id);
        if(selected.get() == ButtonType.OK && isBookingDeleted(obj))
        {             
            buildBooking();
            alertInfo(null, "Booking ID " + id + " has been deleted.");
        }
    }
    
    private boolean isBookingDeleted(DiagnosisAndRepairBooking obj) throws ClassNotFoundException
    {        
        Connection conn = null;
        
        try
        {
            conn = (new sqlite().connect());
            String sql = "DELETE FROM booking WHERE booking_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, obj.getBookingID());
            state.executeUpdate();
            
            state.close();
            conn.close();
            
            return true;
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
            
        }
        return false; 
    }

    
    private void createBooking(DiagnosisAndRepairBooking obj, String[] custArr, String[] mechArr) throws ClassNotFoundException
    {
        Connection conn = null;
        
        try
        {
            conn = (new sqlite().connect());
            String sql = "insert into booking(vehicleID,customer_id,mechanic_id,scheduled_date,duration,mileage,startTime,endTime) values(?,?,?,?,?,?,?,?)";
           
            PreparedStatement state = conn.prepareStatement(sql);
          
            state.setInt(1, findVehID(obj.getVehicleReg()));
            state.setString(2, custArr[0]);
            state.setString(3, mechArr[0]);
            state.setString(4, obj.getDate());
            state.setInt(5, obj.getDuration());
            state.setDouble(6, obj.getMileage());
            state.setString(7, obj.getStartTime());
            state.setString(8, obj.getEndTime());
            
            state.execute(); 
            
           
            state.close();
            conn.close();
            
            alertInfo(null,"Booking Successful");
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
    }
    
    @FXML
    private void exitEdit(ActionEvent event) throws ClassNotFoundException  
    {
        update.setVisible(false);
        book.setVisible(true);
        exitEditB.setVisible(false); 
        customerCombo.setDisable(true);
        vehicleCombo.setDisable(true);
        fillCustomerCombo(); 
        clearFields();
        
    }
    
    
    private void buildBooking() throws ClassNotFoundException
    {
        bHour.setSelected(false);
        bToday.setSelected(false);
        bMonth.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        allBooking.setSelected(true);
        
        data = FXCollections.observableArrayList();
        tempData.clear();
        
    Connection conn = null;
    try{      
        
        conn = (new sqlite().connect());
        String SQL = "Select * from booking";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next())
        {
            
            String make = findMake(rs.getString(2),conn);
         
            data.add(new DiagnosisAndRepairBooking(rs.getInt(1), findVehReg(rs.getString(2)), make, findCustName(rs.getString(3)), findMechName(rs.getString(4)) ,rs.getString(5), rs.getInt(6), rs.getDouble(7), rs.getString(8), rs.getString(9)) );
          
            
        }
        tempData.addAll(data);
        table.setItems(data);
        
        rs.close();
        conn.close();
        
    }
    catch(SQLException e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
    }
    
    private String findMake(String vehID, Connection conn)
    {
        String make = "";
  
        try {

            String SQL = "Select Make from vehicleList where vehicleID='"+ vehID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
               
                make = rs.getString("Make");    
       
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return make;
    }
    
    
    @FXML
    private void fillStartTimeOnChange(ActionEvent event) throws ClassNotFoundException
    {
        fillStartTimeCombo();
    }
    
    private void fillStartTimeCombo() throws ClassNotFoundException
    {   
        if(datePicked.getValue()==null)
        {
            return;
        }
        if (mechanicCombo.getValue()==null)
        {
            alertError(null,"select a mechanic first");
            return;
        }
        
         ObservableList<String> temp = FXCollections.observableArrayList(startTimeLs);
      
        
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String date = ((TextField)datePicked.getEditor()).getText();
         LocalDate localDate = LocalDate.parse(date, formatter);
         formatter.format(localDate);
         if(checkSaturday(localDate))
         {
             int index = temp.indexOf("12:00");
             int s = temp.size();
             for(int i=index; i<s; i++)
             {       
                temp.remove(index); //remove after 12 saturday
             }
         }
        Connection conn = null;
    try{      
        conn = (new sqlite().connect());
      
        String[] mech = mechanicCombo.getValue().split(": ");
        
        String SQL = "Select startTime, endTime ,booking_id from booking where scheduled_date='"+((TextField)datePicked.getEditor()).getText()+ "' and mechanic_id='"+mech[0]+"' ORDER BY startTime ASC";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next())
        {
            
            if(!book.isVisible() && rs.getInt("booking_id")==obj.getBookingID())
            {
                if(!rs.next())
                {
                    break;
                }
            }
            
            int i = temp.indexOf(rs.getString("startTime"));
            int j = temp.indexOf(rs.getString("endTime"));
            System.out.println(i+"  "+j);
            if(j == -1) //17:30
            {
                j=temp.size(); //reached latest time
            }
                for(int z=0; z<j-i; z++)
                {
                    System.out.println(temp.get(i));
                    temp.remove(i); //removes taken time slot    
                }   
        }  
        rs.close();
        
        if(temp.isEmpty())
        {
            alertInfo("Time slots fully booked","Choose another date or mechanic");
        }  
        startTime.setItems(temp);
        
    }
    
    catch(SQLException e){
          e.printStackTrace();
          System.out.println("Error");            
    }
    }
    
    @FXML 
    private void fillEndTimeCombo(ActionEvent event) throws ClassNotFoundException
    {
        
         String start = startTime.getValue();
         
         int closestTime = 1000;
         String closestTimeString = "";
         
        Connection conn = null;
    try{      
        conn = (new sqlite().connect());
      
        String[] mech = mechanicCombo.getValue().split(": ");
        String SQL = "Select startTime,booking_id from booking where scheduled_date='"+((TextField)datePicked.getEditor()).getText()+ "' and mechanic_id='"+mech[0]+"' and startTime>'"+start+"'";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next())
        {             
            
            if(!book.isVisible() && rs.getInt("booking_id")==obj.getBookingID())
            {
                if(!rs.next())
                {
                    break;
                }
            }
            
            String tempTime =  rs.getString("startTime");
            int t = calculateDuration(start,tempTime);

            if(t < closestTime)//store closest start time
            {
                    closestTimeString=tempTime;
                    closestTime=t;
            }     
        } 
        fillComboEndTimes(start,closestTimeString);
        rs.close();
    }
    catch(SQLException e){
          e.printStackTrace();
          System.out.println("Error");            
    }
    }
    
    private void fillComboEndTimes(String start, String closestTimeString)
    {
        if(start==null)
        {
            return;
        }
        
            ObservableList<String> temp = FXCollections.observableArrayList();
             
            int i = endTimeLs.indexOf(start);
            int j = endTimeLs.indexOf(closestTimeString);
            
            //if there is no start time after the selected start time
            if(j == -1)
            {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String date = ((TextField)datePicked.getEditor()).getText();
                    LocalDate localDate = LocalDate.parse(date, formatter);
                    formatter.format(localDate);
                    if(checkSaturday(localDate))
                    {
                        int MaxIndex = endTimeLs.indexOf("12:00");
                        for(int z=++i; z<=MaxIndex; z++)
                        {
                            temp.add(endTimeLs.get(z)); //add until 12 for saturday
                        }
                        
                    }
                    else
                    {
                        for(int z=++i; z<endTimeLs.size(); z++)
                        {
                            temp.add(endTimeLs.get(z)); //add until end
                        }
                    }
            }
            else
            {
                    for(int z=++i; z<=j; z++)
                    {
                        temp.add(endTimeLs.get(z)); //add until closest start time
                    }
            }
             
        endTime.setItems(temp);
    }
    
    private int fillVehID() throws ClassNotFoundException
    {
        vehID.clear();
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select vehicleID from booking";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                int id = rs.getInt("vehicleID");   
                if(!vehID.contains(id))
                {
                    vehID.add(id);
                }
            }
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }  
        
        return vehID.size();
    }
    
    @FXML
    private void filterByNextBooking() throws ClassNotFoundException
    {
        if(!nBooking.isSelected())
        {
            return;
        }
        
        bHour.setSelected(false); 
        bToday.setSelected(false);
        bMonth.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        
        
        
        int size = fillVehID();
        
        data = FXCollections.observableArrayList();
        //tempData.clear();
  
        String nextDate="";
        int bookingID=0;
        String vReg="";
        String custID="";
        String mechID="";
        String startTime="";
        String endTime="";
        double mileage = 0;
        int duration = 0;
        
        LocalDateTime dateToday = LocalDateTime.now();
        
        try 
        {
    
    for(int i=0; i<size; i++)
    {    
        int vID = vehID.get(i);
 
         Connection conn = (new sqlite().connect());

            String SQL = "Select * from booking where vehicleID='"+ vID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            
            String date = rs.getString("scheduled_date");   
            String dt = date +" "+rs.getString("startTime");
                
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");     
            LocalDateTime dateTemp = LocalDateTime.parse(dt,formatter);
            LocalDateTime nextDT = dateTemp;
     
                           nextDate=date;
                           bookingID=rs.getInt("booking_id");
                           custID=rs.getString("customer_id");
                           mechID=rs.getString("mechanic_id");
                           startTime=rs.getString("startTime");
                           endTime=rs.getString("endTime");
                           mileage = rs.getDouble("mileage");
                           duration = rs.getInt("duration");   
            rs.next();
            
            while (rs.next()) 
            {          
                 
                date = rs.getString("scheduled_date");   
                dt = date +" "+rs.getString("startTime");      
                dateTemp = LocalDateTime.parse(dt,formatter);
            
                if((dateToday.isAfter(nextDT)) || (nextDT.isAfter(dateTemp) && dateToday.isBefore(dateTemp)))
                {
                           nextDT = dateTemp;
                           nextDate=date;
                           bookingID=rs.getInt("booking_id");
                           custID=rs.getString("customer_id");
                           mechID=rs.getString("mechanic_id");
                           startTime=rs.getString("startTime");
                           endTime=rs.getString("endTime");
                           mileage = rs.getDouble("mileage");
                           duration = rs.getInt("duration");                 
                }      
            }
            String make = findMake(Integer.toString(vID),conn);
            
            rs.close();
            conn.close();
            if(dateToday.isBefore(nextDT))
            {
                data.add(new DiagnosisAndRepairBooking(bookingID, findVehReg(Integer.toString(vID)), make, findCustName(custID), findMechName(mechID), nextDate, duration, mileage, startTime, endTime));
            }
       } //end for loop
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }  
       // tempData.addAll(data);
        table.setItems(data);
       
    }
    
    
    @FXML
    private void buildPartsInfo(ActionEvent event) throws ClassNotFoundException
    {
        
        data3 = FXCollections.observableArrayList();
        
        int id;
        if(table.getSelectionModel().getSelectedItem() == null)
        {
            alertError(null,"Please select a row");
            return;
        }
        
        id = table.getSelectionModel().getSelectedItem().getBookingID();
        
        Connection conn = null;
    try{      
        
        conn = (new sqlite().connect());
        
        String SQL = "select name,cost,dateOfWarrantyExpire from vehiclePartsUsed where bookingID='"+id+"'";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next())
        {     
            data3.add(new PartsInfo(rs.getString("name"), rs.getInt("cost"), rs.getString("dateOfWarrantyExpire")));
        }
        
        rs.close();
        conn.close();
        
    }
    catch(SQLException e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
 
        partsTable.setItems(data3);
    }

    
    private int recentBookingID() throws ClassNotFoundException
    {
        int id=0;
        
         Connection conn = null;
    try{      
        
        conn = (new sqlite().connect());
        
        String SQL = "select booking_id from booking order by booking_id desc";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        
        id = rs.getInt("booking_id");
        
        rs.close();
        conn.close();
        
    }
    catch(SQLException e){
          e.printStackTrace();          
    }
        return id;
    }

    
    private void addMechanicBill(int bID, int vID, int cID, int mID) throws ClassNotFoundException {
        
      /*  int bookingID = bID; 
        int vehicleID = vID;
        int customerID = cID;
        int mechanicID = mID;
        
        try {
            
              Mechanic mech = new Mechanic(mechanicID,getMechanicHourlyRate(bookingID),getMechanicHoursWorked(bookingID));
             // BillController.showBill.addMechanicCostToBill(BillController.showBill, mech); //create a addMechanicCostToBill method in bill class
                //Double totalCost = BillController.showBill.getTotalCost(); //create total mech cost

                Connection conn = new sqlite().connect();

                String sql = "insert into bill(customerID, bookingID,vehicleID, totalCost, settled) values(?,?,?,?,?)";
                PreparedStatement state = conn.prepareStatement(sql);
                state.setInt(1, customerID);
                state.setInt(2, bookingID);
                state.setInt(3, vehicleID);

                state.setDouble(4, totalCost);
 
                String exists = IfWarranty(vehicleID, conn);
                if(exists.equals("Yes"))
                {
                    state.setBoolean(5, true);
                }
                else if(exists.equals("No"))
                {
                    state.setBoolean(5, false);
                }

                BillController.showBill.setTotalCost(0);
                state.execute();

                state.close();
                conn.close();
           
        } 
        catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
    public String IfWarranty(int vehicleID, Connection conn) throws ClassNotFoundException{
        
        String check= "";

        try {

            String SQL = "Select Warranty from vehicleList where vehicleID ='" + vehicleID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
               
            check = rs.getString("Warranty");

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;*/
    }
    
    @FXML
    private void searchFunc()
    {
        FilteredList<DiagnosisAndRepairBooking> filteredData = new FilteredList<>(data, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observableValue, oldValue2, newValue2) -> {
                filteredData.setPredicate((Predicate<? super DiagnosisAndRepairBooking>) Booking -> {
                    if (newValue2 == null || newValue2.isEmpty()) {
                        return true;
                    }
                    String newValLow = newValue2.toLowerCase();
                    if (Booking.getCustName().toLowerCase().contains(newValLow)) {
                        return true;
                    } else if (Booking.getVehicleReg().toLowerCase().contains(newValLow)) {
                        return true;
                    }
                    else if (Booking.getMake().toLowerCase().contains(newValLow)) {
                        return true;
                    }

                    return false;
                }); 
            });
                SortedList<DiagnosisAndRepairBooking> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
        });
    }
    
     @FXML
    private void filterByHour() throws ClassNotFoundException
    {
         if(!bHour.isSelected())
        {
            return;
        }
        
         
        bToday.setSelected(false);
        bMonth.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        
        data = FXCollections.observableArrayList(tempData);
     
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
        LocalDateTime today = LocalDateTime.now();
        
        String dateTimeTemp = "";
        
        for(int i=0; i<data.size(); i++)
        {
            dateTimeTemp = data.get(i).getDate() +" "+data.get(i).getStartTime();
            LocalDateTime dateTemp = LocalDateTime.parse(dateTimeTemp ,formatter);
        
            int minutes = (int)ChronoUnit.MINUTES.between(today, dateTemp);
            
            if(minutes > 60 || minutes<0)
            {
                data.remove(i);
                i--;  
            }
           
        }
       table.setItems(data);
    }
    
    @FXML
    private void filterByToday() throws ClassNotFoundException
    {
        if(!bToday.isSelected())
        {
            return;
        }
        
        bHour.setSelected(false);
        bMonth.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        
        
        data = FXCollections.observableArrayList(tempData);
     
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String today = LocalDate.now().format(formatter);
        
        for(int i=0; i<data.size(); i++)
        {
            if(!today.equals(data.get(i).getDate()))
            {
                data.remove(i);
                i--;  
            }
           
        }
       table.setItems(data);
    } 

    @FXML
    private void filterByMonth() throws ClassNotFoundException
    {
        if(!bMonth.isSelected())
        {
            return;
        }
        
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
          
        data = FXCollections.observableArrayList(tempData);
        
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = now.format(formatter);
        String[] dateNow = date.split("/");
        
        for(int i=0; i<data.size(); i++)
        {
            String[] tempDate = data.get(i).getDate().split("/");
            if(!dateNow[1].equals(tempDate[1]) || !dateNow[2].equals(tempDate[2])) //different month or different year
            {
                data.remove(i);
                i--;
            }
        }
        table.setItems(data); 
    }
    
    @FXML
    private void filterByPast() throws ClassNotFoundException
    {
       if(!pBooking.isSelected())
        {
            return;
        }
        
         bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        bMonth.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        
        data = FXCollections.observableArrayList(tempData);
     alertInfo(data.size()+"",null);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for(int i=0; i<data.size(); i++)
        {
            LocalDate tempDate = LocalDate.parse(data.get(i).getDate(),formatter);
            if(now.isBefore(tempDate)) //past dates
            {
                data.remove(i);
                i--;
                alertInfo(null,null);
            }
        }
        table.setItems(data);
    }
    
    @FXML
    private void filterByFuture() throws ClassNotFoundException
    {
        if(!fBooking.isSelected())
        {
            return;
        }
   
        pBooking.setSelected(false);
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        bMonth.setSelected(false);
        nBooking.setSelected(false);
        
        data = FXCollections.observableArrayList(tempData);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for(int i=0; i<data.size(); i++)
        {
            LocalDate tempDate = LocalDate.parse(data.get(i).getDate(),formatter);
            if(now.isAfter(tempDate) || !now.equals(tempDate)) //past dates
            {
                data.remove(i);
                i--;
            }
        }
        table.setItems(data);    
    }
    
    @FXML
    private void showAllBooking() throws ClassNotFoundException
    {
        if(!allBooking.isSelected())
        {
            return;
        }
        
        bHour.setSelected(false);
        bToday.setSelected(false); 
        bMonth.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);

        
        buildBooking();
    }
    
    
    private int getMechanicHoursWorked(int bookingID) throws ClassNotFoundException
    {
        int hour = 0;
        
         Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select duration from booking where booking_id='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                hour = rs.getInt("duration") / 60;    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        
        return hour;
    }
    
    private double getMechanicHourlyRate(int bookingID) throws ClassNotFoundException
    {
        double rate = 0;
        
         Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select hourlyRate from mechanic,booking where booking.mechanic_id=mechanic.mechanic_id and booking.booking_id='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                rate = rs.getDouble("hourlyRate");    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        
        return rate;
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


    private void alertInfo(String header, String information) 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(information);
        alert.showAndWait();
    }

    private void alertError(String header, String error) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(error);
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