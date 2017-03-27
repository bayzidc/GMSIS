/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.gui;

import Authentication.sqlite;
import DiagnosisAndRepair.logic.DiagnosisAndRepairBooking;
import DiagnosisAndRepair.logic.Mechanic;
import Authentication.User;
import CustomerAccount.gui.GuiController;
import DiagnosisAndRepair.logic.Mechanic;
import java.util.*;
import java.io.IOException;
import java.sql.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.DayOfWeek;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.*;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextFormatter.Change;
/**
 * FXML Controller class
 *
 * @author Bayzid
 */
public class DiagnosisAndRepairController implements Initializable {

    
    @FXML
    private AnchorPane pane;
    
    @FXML
    private JFXButton users;
    @FXML
    private JFXButton customers;
    @FXML
    private JFXButton vehicles;
    @FXML
    private JFXButton bookings;
    @FXML
    private JFXButton parts;
    @FXML
    private JFXButton logout;
    

    @FXML
    private Label textLabel;
    @FXML
    private Button book;
    @FXML
    private Button update;
    @FXML
    private Button exitEditB;
    @FXML 
    private DatePicker datePicked;
    @FXML
    private DatePicker anyDayPicker;
    @FXML
    private ComboBox<String> customerCombo;
    @FXML
    private ComboBox<String> vehicleCombo;
    @FXML
    private ComboBox<String> mechanicCombo;
    @FXML
    private ComboBox<String> startTime;
    @FXML
    private ComboBox<String> searchCombo;
    @FXML
    private ComboBox<String> endTime;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private TextField searchField;
    @FXML
    private TextField mileage;
    @FXML
    private JFXCheckBox bHour;
    @FXML
    private JFXCheckBox bToday;
    @FXML
    private JFXCheckBox bMonth;
    @FXML
    private JFXCheckBox nBooking;
    @FXML
    private JFXCheckBox pBooking;
    @FXML
    private JFXCheckBox fBooking;
    @FXML
    private JFXCheckBox allBooking;

    

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
    private TableColumn<DiagnosisAndRepairBooking, Integer> mileageCol;
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
    
 
    ObservableList<Integer> vehID = FXCollections.observableArrayList();
    private ObservableList<String> custNames = FXCollections.observableArrayList();
    private ObservableList<String> vehicleReg = FXCollections.observableArrayList();
    private ObservableList<String> mechNames = FXCollections.observableArrayList();
    private ObservableList<String> startTimeLs = FXCollections.observableArrayList();
    private ObservableList<String> endTimeLs = FXCollections.observableArrayList();
    
    private ObservableList<String> searchLs = FXCollections.observableArrayList();
    
    private ObservableList<String> monthLs = FXCollections.observableArrayList();

    public static DiagnosisAndRepairBooking BookingObj = new DiagnosisAndRepairBooking(0,"","","","","",0,0,"","","");
     
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        startTimeLs = FXCollections.observableArrayList("09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00");
        endTimeLs = FXCollections.observableArrayList("09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30");
    
        searchLs = FXCollections.observableArrayList("Customer Name","Vehicle registration","Make");
    
        monthLs = FXCollections.observableArrayList("01:January","02:February","03:March","04:April","05:May","06:June","07:July","08:August","09:September","10:October","11:November","12:December");
        
        searchCombo.setItems(searchLs);
        searchCombo.setValue("Customer Name");
        monthCombo.setItems(monthLs);
        
        //restrict to only integer mileage
        UnaryOperator<Change> intInput = change -> {
        String input = change.getText();
        if (input.matches("[0-9]*")) { 
            return change;
        }
        return null;
        };
        mileage.setTextFormatter(new TextFormatter<>(intInput));
        
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
        new PropertyValueFactory<DiagnosisAndRepairBooking,Integer>("mileage"));        
    dateCol.setCellValueFactory(                
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("date"));
    durationCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,Integer>("duration"));
    startTimeCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("startTime"));  
    endTimeCol.setCellValueFactory(
        new PropertyValueFactory<DiagnosisAndRepairBooking,String>("endTime"));
    
 
        //restrict date picker   
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
        //fill combo boxes and tableview
        fillCustomerCombo();
        fillMechanicCombo();
        buildBooking();
    }
        catch(ClassNotFoundException e)
    {
        e.printStackTrace(); 
    }
    
        //user disabled if day-to-day
        if(!Authentication.LoginController.isAdmin)
        {
            users.setDisable(true);
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
    private void usersPage(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void customerPage(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/gui.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML
    private void vehicleRecordPage(ActionEvent event) throws IOException
    {
        
          AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
          pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void bookingPage(ActionEvent event) throws IOException
    {
         AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));
        
        pane.getChildren().setAll(rootPane);
    }
    
    @FXML 
    private void partsUsedPage(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    
    //initiate adding parts button
    @FXML
    private void addParts(ActionEvent event) throws IOException 
    {
        if(table.getSelectionModel().getSelectedItem()==null)
        {
            alertError(null,"Select a row first");
            return;
        }
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateT = LocalDateTime.parse(table.getSelectionModel().getSelectedItem().getDate()+" "+table.getSelectionModel().getSelectedItem().getStartTime(), formatter);
        if(today.isAfter(dateT))
        {
            alertError(null,"You cannot add parts to a past booking");
            return;
        }
        
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        Parent root = (AnchorPane)loader.load();
        PartsRecord.gui.PartsController BookingObj = (PartsRecord.gui.PartsController) loader.getController();       
        BookingObj.initiateInstallPart(table.getSelectionModel().getSelectedItem().getBookingID(), LocalDate.parse(table.getSelectionModel().getSelectedItem().getDate(),formatter2));
        pane.getChildren().setAll(root);*/
        
        /*public void initiateInstallPart(int bookingID, LocalDate date)
        {
            bookingIdCombo.setValue(bookingID);
            dateOfInstall.setValue(date);
        }*/
    }
    
  
    //disable all bank holiday dates for 2017
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
    
    //on action of customer combo box, loads specific vehicle no.
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
            alertInfo("There are no vehicle for the selected customer","Please add a vehicle first");
        }
    }
    
    //on action of vehicle combo box, loads its mileage
    @FXML
    private void findMileageOnChange(ActionEvent event) throws ClassNotFoundException
    {
        mileage.clear();
        if(vehicleCombo.getValue()==null)
        {
            return;
        }
        
        Connection conn = new sqlite().connect();
        mileage.setText(Integer.toString(BookingObj.findMileage(BookingObj.findVehID(vehicleCombo.getValue()))));
        try {
            conn.close();
        } catch (SQLException ex) {
            
        }
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

            String SQL = "Select mechanic_id,fullname from mechanic";
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
        startTime.setItems(null);
        endTime.setItems(null);
    }
    
    //update button action
    @FXML 
    private void updateBooking(ActionEvent event) throws ClassNotFoundException
    {
        if(!checkIfCompleted())
        {
            alertError("Try again","Complete all fields");
            return;
        }
        
        LocalDateTime now = LocalDateTime.now();
        String dt = ((TextField)datePicked.getEditor()).getText() + " " +startTime.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateT = LocalDateTime.parse(dt, formatter);
        
        if(now.isAfter(dateT))
        {
            alertError("Cannot make a past booking","Choose a future date or time");
            return;
        }
        
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to update this booking");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
        
        String cust = customerCombo.getValue();
        String[] custArr = cust.split(": ");
        BookingObj.setCustName(custArr[1]);
        BookingObj.setVehicleReg(vehicleCombo.getValue());
        String mech = mechanicCombo.getValue();
        String[] mechArr = mech.split(": ");
        BookingObj.setMechanicName(mechArr[1]);
        BookingObj.setDate(((TextField)datePicked.getEditor()).getText());
        BookingObj.setDuration(BookingObj.calculateDuration(startTime.getValue(),endTime.getValue()));
        BookingObj.setStartTime(startTime.getValue());
        BookingObj.setEndTime(endTime.getValue());
        BookingObj.setMileage(Integer.parseInt(mileage.getText()));
        
        updateData(BookingObj,custArr,mechArr);
        buildBooking();
        fillCustomerCombo();
        book.setVisible(true);
        exitEditB.setVisible(false);
        update.setVisible(false);
        clearFields();

    }
    
    //update the database booking info
    private void updateData(DiagnosisAndRepairBooking BookingObj,String[] custArr, String[] mechArr) throws ClassNotFoundException
    {
         Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String sql = "UPDATE booking SET vehicleID=?,customer_id=?,mechanic_id=?,scheduled_date=?,duration=?,startTime=?,endTime=? WHERE booking_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
           
                state.setInt(1, BookingObj.findVehID(BookingObj.getVehicleReg()));
                state.setString(2, custArr[0]);
                state.setString(3, mechArr[0]);
                state.setString(4, BookingObj.getDate());
                state.setInt(5, BookingObj.getDuration());
                state.setString(6, BookingObj.getStartTime());
                state.setString(7, BookingObj.getEndTime()); 
                state.setInt(8, BookingObj.getBookingID());
                
                BookingObj.updateMileage(BookingObj.getMileage(),BookingObj.findVehID(BookingObj.getVehicleReg()));
                
                state.execute();

                state.close();
                conn.close();
                
                alertInfo(null,"Update Successful");
                
                customerCombo.setDisable(false);
                vehicleCombo.setDisable(false);
            }
         catch (SQLException e) {
            System.out.println("");
        }
        new Mechanic(0,0,0).updateMechanicBill(BookingObj.getBookingID(),Integer.parseInt(mechArr[0]));
        
    }
    
    //submit booking button action
    @FXML
    private void booking(ActionEvent event) throws IOException, ClassNotFoundException
    {
        if(!checkIfCompleted())
        {
            alertError("Try again","Complete all fields");
            return;
        }
        
        LocalDateTime now = LocalDateTime.now();
        String dt = ((TextField)datePicked.getEditor()).getText() + " " +startTime.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateT = LocalDateTime.parse(dt, formatter);
        
        if(now.isAfter(dateT))
        {
            alertError("Cannot make a past booking","Choose a future date or time");
            return;
        }
        
        Optional<ButtonType> selected = alertConfirm("Are you sure you want to submit this booking");
        
        if(selected.get() != ButtonType.OK)
        {
            return;
        }
        
        String cust = customerCombo.getValue();
        String[] custArr = cust.split(": ");
        BookingObj.setCustName(custArr[1]);
        BookingObj.setVehicleReg(vehicleCombo.getValue());
        String mech = mechanicCombo.getValue();
        String[] mechArr = mech.split(": ");
        BookingObj.setMechanicName(mechArr[1]);
        BookingObj.setDate(((TextField)datePicked.getEditor()).getText());
        BookingObj.setDuration(BookingObj.calculateDuration(startTime.getValue(),endTime.getValue()));
        BookingObj.setStartTime(startTime.getValue());
        BookingObj.setEndTime(endTime.getValue());
        BookingObj.setMileage(Integer.parseInt(mileage.getText()));
            
        int recentBookingID = createBooking(BookingObj,custArr,mechArr);
        buildBooking();
        clearFields();
        new Mechanic(0,0,0).addMechanicBill(recentBookingID,BookingObj.findVehID(BookingObj.getVehicleReg()),Integer.parseInt(custArr[0]),Integer.parseInt(mechArr[0]));
    }
    
    //clear all button
    @FXML
    private void clearAll(ActionEvent event)
    {
        clearFields();
    }
    
    //reset entries
    private void clearFields()
    {
        if(book.isVisible())
        {
            customerCombo.setValue(null);
            vehicleReg.clear();
            vehicleCombo.setValue(null);
        }
        
        datePicked.setValue(null);
        startTime.setItems(null);
        endTime.setItems(null);
        mechanicCombo.setValue(null);
        datePicked.setValue(null);
        mileage.clear();
    }
    
    public void initiateBooking(String cName, int id, String reg, int mile)
    {
        customerCombo.setValue(id+": "+cName);
        vehicleCombo.setValue(reg);
        mileage.setText(Integer.toString(mile));
        
    }
    
    //check if every entry is complete
    private boolean checkIfCompleted()
    { 
        if(customerCombo.getValue()!=null && vehicleCombo.getValue()!=null && mechanicCombo.getValue()!=null && datePicked.getValue()!=null && startTime.getValue()!=null && endTime.getValue()!=null && mileage.getText()!=null)
        {
            return true;
        }
        return false;
    }
    
    //edit booking button action
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
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateT = LocalDateTime.parse(table.getSelectionModel().getSelectedItem().getDate()+" "+table.getSelectionModel().getSelectedItem().getStartTime(), formatter2);

        if(today.isAfter(dateT))
        {
            alertError(null,"You cannot edit a past booking");
            return;
        }
        
        clearFields();
        
        customerCombo.setDisable(true);
        vehicleCombo.setDisable(true);
        
        update.setVisible(true);
        book.setVisible(false);
        exitEditB.setVisible(true);
        
        BookingObj.setBookingID(id);
        String reg = table.getSelectionModel().getSelectedItem().getVehicleReg();      
        String custName = table.getSelectionModel().getSelectedItem().getCustName();     
        String mechName = table.getSelectionModel().getSelectedItem().getMechanicName();     
        String date = table.getSelectionModel().getSelectedItem().getDate();
        String sTime = table.getSelectionModel().getSelectedItem().getStartTime();     
        String eTime = table.getSelectionModel().getSelectedItem().getEndTime();
        int mAge = table.getSelectionModel().getSelectedItem().getMileage();
        
        String cust = BookingObj.findCustID(reg)+": "+custName;
        System.out.println(cust);
        customerCombo.setValue(findComboVal(custNames,cust));  
        String mech = BookingObj.findMechID(BookingObj.getBookingID())+": "+mechName;
        mechanicCombo.setValue(findComboVal(mechNames,mech));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
      
        datePicked.setValue(localDate); 
      
        vehicleCombo.setValue(findComboVal(vehicleReg,reg));  
        startTime.setValue(findComboVal(startTimeLs,sTime));
        endTime.setValue(findComboVal(endTimeLs,eTime));
        mileage.setText(Integer.toString(mAge));
    }

    //find value from combo box
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
    
    //delete booking button action
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
        
        BookingObj.setBookingID(id);
        if(selected.get() == ButtonType.OK && isBookingDeleted(BookingObj))
        {             
            buildBooking();
            alertInfo(null, "Booking ID " + id + " has been deleted.");
            
            if(!book.isVisible())
                    {
                        update.setVisible(false);
                        book.setVisible(true);
                        exitEditB.setVisible(false); 
                        customerCombo.setDisable(false);
                        vehicleCombo.setDisable(false);
                        clearFields();    
                    }
            datePicked.setValue(null);
        }
    }
    
    //delete booking from the database
    private boolean isBookingDeleted(DiagnosisAndRepairBooking BookingObj) throws ClassNotFoundException
    {        
        Connection conn = null;
        
        try
        {
            
            conn = (new sqlite().connect());
            String sql = "DELETE FROM booking WHERE booking_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, BookingObj.getBookingID());
  
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

    //insert booking data into the database and return booking id
    private int createBooking(DiagnosisAndRepairBooking BookingObj, String[] custArr, String[] mechArr)
    {
        Connection conn = null;
        int id = -1;
        
        try
        {
            conn = (new sqlite().connect());
            String sql = "insert into booking(vehicleID,customer_id,mechanic_id,scheduled_date,duration,startTime,endTime) values(?,?,?,?,?,?,?)";
           
            PreparedStatement state = conn.prepareStatement(sql);
          
            state.setInt(1, BookingObj.findVehID(BookingObj.getVehicleReg()));
            state.setString(2, custArr[0]);
            state.setString(3, mechArr[0]);
            state.setString(4, BookingObj.getDate());
            state.setInt(5, BookingObj.getDuration());         
            state.setString(6, BookingObj.getStartTime());
            state.setString(7, BookingObj.getEndTime());
            
            BookingObj.updateMileage(BookingObj.getMileage(),BookingObj.findVehID(BookingObj.getVehicleReg()));
            
            state.execute(); 
            
            ResultSet rs = conn.createStatement().getGeneratedKeys();  
            
            if(rs.next())
            {
                id = rs.getInt(1);
            }
            state.close();
            conn.close();
           
            alertInfo(null,"Booking Successful");
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
        return id;
    }
    
   
    //cancel from edit mode button action
    @FXML
    private void exitEditMode(ActionEvent event) throws ClassNotFoundException  
    {
        update.setVisible(false);
        book.setVisible(true);
        exitEditB.setVisible(false); 
        customerCombo.setDisable(false);
        vehicleCombo.setDisable(false);
        fillCustomerCombo(); 
        clearFields();
        
    }
    
    //load up the booking table view  
    private void buildBooking() throws ClassNotFoundException
    {
        textLabel.setVisible(false);
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
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
            
            data.add(new DiagnosisAndRepairBooking(rs.getInt(1), BookingObj.findVehReg(rs.getInt(2)), BookingObj.findMake(rs.getInt(2)), BookingObj.findCustName(rs.getInt(3)), BookingObj.findMechName(rs.getInt(4)) ,rs.getString(5), rs.getInt(6), BookingObj.findMileage(rs.getInt(2)), rs.getString(7), rs.getString(8),""));
            
        }
        tempData.addAll(data);
        table.setItems(data);
        
        searchField.clear();
        
        rs.close();
        conn.close();
        
        /*if(bHour.isSelected())
        {
            filterByHour();
        }
        else if(bToday.isSelected())
        {
            filterByToday();
        }
        else if(bMonth.isSelected())
        {
            filterByMonth();
        }
        else if(pBooking.isSelected())
        {
            filterByPast();
        }
        else if(fBooking.isSelected())
        {
            filterByFuture();
        }
        else if(nBooking.isSelected())
        {
            filterByNextBooking();
        }
        else if(anyDayPicker.getValue()!=null)
        {
            filterByAnyDay();
        }
        else if(monthCombo.getValue()!=null)
        {
            filterByAnyMonth();
        }
        else*/
        {
            searchFilter(data);
        }
        
    }
    catch(SQLException e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
    }
    
    
    @FXML
    private void AlertStartTimeOnChange(ActionEvent event) throws ClassNotFoundException
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
        
        endTime.setItems(null);
        
         ObservableList<String> temp = FXCollections.observableArrayList(startTimeLs);
      
        
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String date = ((TextField)datePicked.getEditor()).getText();
         LocalDate localDate = LocalDate.parse(date, formatter);
         formatter.format(localDate);
         if(BookingObj.checkSaturday(localDate))
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
            if(!book.isVisible() && rs.getInt("booking_id")==BookingObj.getBookingID())
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
    private void AlertEndTimeComboOnChange(ActionEvent event) throws ClassNotFoundException
    {
     
        if(startTime.getValue()==null || datePicked.getValue()==null)
        {
            return;
        }
        
         String start = startTime.getValue();
         
         int closestTime = 10000;
         String closestTimeString = "";
         
        Connection conn = null;
    try{      
        conn = (new sqlite().connect());
      
        String[] mech = mechanicCombo.getValue().split(": ");
        String SQL = "Select startTime,booking_id from booking where scheduled_date='"+((TextField)datePicked.getEditor()).getText()+ "' and mechanic_id='"+mech[0]+"' and startTime>'"+start+"'";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next())
        {             
            
            if(!book.isVisible() && rs.getInt("booking_id")==BookingObj.getBookingID())
            {
                if(!rs.next())
                {
                    break;
                }
            }
            
            String tempTime =  rs.getString("startTime");
            int t = BookingObj.calculateDuration(start,tempTime);

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
        if(start==null || start.isEmpty() || datePicked.getValue()==null)
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
                    if(BookingObj.checkSaturday(localDate))
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
    
    //find all vehicle ID
    private int fillVehID() throws ClassNotFoundException
    {
        vehID.clear();
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select distinct vehicleID from booking";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                    vehID.add(rs.getInt("vehicleID"));
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
        /*long time1,time2;
        time1 = new Date().getTime();
               
        time2 = new Date().getTime();
        System.out.println("is "+(time2-time1)+"ms");*/
        
        
        if(!nBooking.isSelected())
        {
            nBooking.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false);

        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        bHour.setSelected(false); 
        bToday.setSelected(false);
        bMonth.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        
        
        
        int size = fillVehID();
        
       ObservableList<DiagnosisAndRepairBooking> nextList = FXCollections.observableArrayList();

  
        String nextDate="";
        int bookingID=0;
        String vReg="";
        int custID=0;
        int mechID=0;
        String startTime="";
        String endTime="";
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
                           custID=rs.getInt("customer_id");
                           mechID=rs.getInt("mechanic_id");
                           startTime=rs.getString("startTime");
                           endTime=rs.getString("endTime");
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
                           custID=rs.getInt("customer_id");
                           mechID=rs.getInt("mechanic_id");
                           startTime=rs.getString("startTime");
                           endTime=rs.getString("endTime");
                           duration = rs.getInt("duration");                 
                }      
            }
            
            rs.close();
            conn.close();
            if(dateToday.isBefore(nextDT))
            {
                nextList.add(new DiagnosisAndRepairBooking(bookingID, BookingObj.findVehReg(vID), BookingObj.findMake(vID), BookingObj.findCustName(custID), BookingObj.findMechName(mechID), nextDate, duration, BookingObj.findMileage(vID), startTime, endTime,""));
            }
       } //end for loop
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }  
        table.setItems(nextList);
 
        searchField.clear();
        searchFilter(nextList);
       
    }
    
    
    //method used for searching the table view
    private void searchFilter(ObservableList<DiagnosisAndRepairBooking> data)
    {
            
            FilteredList<DiagnosisAndRepairBooking> filteredData = new FilteredList<>(data, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observableValue, oldValue2, newValue2) -> {
                filteredData.setPredicate((Predicate<? super DiagnosisAndRepairBooking>) Booking -> {
                    if (newValue2 == null || newValue2.isEmpty()) {
                        return true;
                    }
                    String newValLow = newValue2.toLowerCase();
                    
                    if (Booking.getCustName().toLowerCase().contains(newValLow) && searchCombo.getValue().equalsIgnoreCase("Customer Name")) {
                        return true;
                    } else if (Booking.getVehicleReg().toLowerCase().contains(newValLow) && searchCombo.getValue().equalsIgnoreCase("Vehicle Registration")) {
                        return true;
                    }
                    else if (Booking.getMake().toLowerCase().contains(newValLow) && searchCombo.getValue().equalsIgnoreCase("Make")) {
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
            bHour.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        bToday.setSelected(false);
        bMonth.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        
        ObservableList<DiagnosisAndRepairBooking> hourList = FXCollections.observableArrayList(tempData);
     
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); 
        LocalDateTime today = LocalDateTime.now();
        
        String dateTimeTemp = "";
        
        for(int i=0; i<hourList.size(); i++)
        {
            dateTimeTemp = hourList.get(i).getDate() +" "+hourList.get(i).getStartTime();
            LocalDateTime dateTemp = LocalDateTime.parse(dateTimeTemp ,formatter);
        
            int minutes = (int)ChronoUnit.MINUTES.between(today, dateTemp);
            
            if(minutes > 60 || minutes<0)
            {
                hourList.remove(i);
                i--;  
            }
           
        }
        
        if(hourList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("No bookings within the next hour");
        }
        
       table.setItems(hourList);
       
       searchField.clear();
       searchFilter(hourList);
       
    }
    
    @FXML
    private void filterByToday() throws ClassNotFoundException
    {
        if(!bToday.isSelected())
        {
            bToday.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        bHour.setSelected(false);
        bMonth.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        
        
        ObservableList<DiagnosisAndRepairBooking> todayList = FXCollections.observableArrayList(tempData);

     
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String today = LocalDate.now().format(formatter);
        
        for(int i=0; i<todayList.size(); i++)
        {
            if(!today.equals(todayList.get(i).getDate()))
            {
                todayList.remove(i);
                i--;  
            }
           
        }
        
        if(todayList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("No bookings for today");
        }
        
       table.setItems(todayList);
       
       searchField.clear();
       searchFilter(todayList);
    } 

    @FXML
    private void filterByMonth() throws ClassNotFoundException
    {
        if(!bMonth.isSelected())
        {
            bMonth.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
          
        ObservableList<DiagnosisAndRepairBooking> monthList = FXCollections.observableArrayList(tempData);
        
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = now.format(formatter);
        String[] dateNow = date.split("/");
        
        for(int i=0; i<monthList.size(); i++)
        {
            String[] tempDate = monthList.get(i).getDate().split("/");
            if(!dateNow[1].equals(tempDate[1]) || !dateNow[2].equals(tempDate[2])) //different month or different year
            {
                monthList.remove(i);
                i--;
            }
        }
        
        if(monthList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("No bookings for this month");
        }
        
        table.setItems(monthList); 
       
        searchField.clear();
        searchFilter(monthList);
    }
    
    @FXML
    private void filterByPast() throws ClassNotFoundException
    {
       if(!pBooking.isSelected())
        {
            pBooking.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        bMonth.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
        
        ObservableList<DiagnosisAndRepairBooking> pastList = FXCollections.observableArrayList(tempData);
      
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for(int i=0; i<pastList.size(); i++)
        {
            LocalDateTime tempDate = LocalDateTime.parse(pastList.get(i).getDate()+" "+pastList.get(i).getStartTime(),formatter);
  
            if(now.isBefore(tempDate)) 
            {
                pastList.remove(i);
                i--;
            }
        }
        
        if(pastList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("There is no past bookings");
        }
        
        table.setItems(pastList);
        
        searchField.clear();
        searchFilter(pastList);
    }
    
    @FXML
    private void filterByFuture() throws ClassNotFoundException
    {
        if(!fBooking.isSelected())
        {
            fBooking.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        pBooking.setSelected(false);
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        bMonth.setSelected(false);
        nBooking.setSelected(false);
        
        ObservableList<DiagnosisAndRepairBooking> futureList = FXCollections.observableArrayList(tempData);
       
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for(int i=0; i<futureList.size(); i++)
        {
            LocalDateTime tempDate = LocalDateTime.parse(futureList.get(i).getDate()+" "+futureList.get(i).getStartTime(),formatter);
  
            if(now.isAfter(tempDate)) 
            {
                futureList.remove(i);
                i--;
            }
        }
        
        if(futureList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("There is no future bookings");
        }
        
        table.setItems(futureList);   
        
        searchField.clear();
        searchFilter(futureList);
    }
    
    @FXML
    private void showAllBooking() throws ClassNotFoundException
    {
        if(!allBooking.isSelected())
        {
            allBooking.setSelected(true);
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        anyDayPicker.setValue(null);
        bHour.setSelected(false);
        bToday.setSelected(false); 
        bMonth.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);

        
        buildBooking();
    }
    
    @FXML
    private void filterByAnyMonth()
    {
        if(monthCombo.getValue()==null)
        {
            return;
        }
        
        textLabel.setVisible(false); 
        
        anyDayPicker.setValue(null);
        bMonth.setSelected(false);
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
          
        ObservableList<DiagnosisAndRepairBooking> anyMonthList = FXCollections.observableArrayList(tempData);

        
        String dp = monthCombo.getValue();
        String[] month = dp.split(":");
        
        for(int i=0; i<anyMonthList.size(); i++)
        {
            String[] tempDate = anyMonthList.get(i).getDate().split("/");
            if(!month[0].equals(tempDate[1]))
            {
                anyMonthList.remove(i);
                i--;
            }
        }
        
        if(anyMonthList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("No bookings for "+month[1]);
        }
        
        table.setItems(anyMonthList);
        
        searchField.clear();
        searchFilter(anyMonthList);
    }
            
    @FXML
    private void filterByAnyDay()
    {
        if(anyDayPicker.getValue()==null)
        {
            return;
        }
        
        textLabel.setVisible(false); 
        
        monthCombo.setValue(null);
        bMonth.setSelected(false);
        bHour.setSelected(false);
        bToday.setSelected(false);
        allBooking.setSelected(false);
        pBooking.setSelected(false);
        fBooking.setSelected(false);
        nBooking.setSelected(false);
          
        ObservableList<DiagnosisAndRepairBooking> anyDateList = FXCollections.observableArrayList(tempData);
        
        
        LocalDate now = anyDayPicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = now.format(formatter);
        
        for(int i=0; i<anyDateList.size(); i++)
        {
            String tempDate = anyDateList.get(i).getDate();
            if(!date.equals(tempDate)) 
            {
                anyDateList.remove(i);
                i--;
            }
        }
        
        if(anyDateList.isEmpty())
        {
            textLabel.setVisible(true);
            textLabel.setText("No bookings for "+date);
        }
        
        table.setItems(anyDateList);
        
        searchField.clear();
        searchFilter(anyDateList);
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