/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import Authentication.sqlite;
import PartsRecord.logic.PartsStock;
import PartsRecord.logic.PartsUsed;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import PartsRecord.logic.PartsUsed;
import PartsRecord.logic.VehicleCustomerInfo;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.util.Callback;
import Authentication.sqlite;
import CustomerAccount.gui.GuiController;
import PartsRecord.logic.PartsUsedPerVehicle;
import VehicleRecord.logic.CustBookingInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.time.LocalDateTime;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartsController implements Initializable {
    
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
    private JFXButton partStockButton;
    @FXML
    private ComboBox<String> partNameCombo;
    @FXML
    private TextField quantity;
    @FXML
    private DatePicker dateOfInstall;
    @FXML
    private ComboBox<Integer> bookingIdCombo;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> searchCombo;
    @FXML
    public TextArea showPartInfo;
    @FXML
    private Button backButton;
    @FXML
    private Button installParts;
    @FXML
    private Button deleteParts;
    @FXML
    private Button clear;
    @FXML
    private Button viewCustomerInfo;
    @FXML
    private Button viewPartsInfo;
    @FXML
    public JFXCheckBox pastBooking;
    @FXML
    public JFXCheckBox futureBooking;
    @FXML
    public JFXCheckBox showAll;
  
     @FXML
    private TableView<PartsUsed> table;
    @FXML
    private TableColumn<PartsUsed, Integer> usedIdCol;
    @FXML
    private TableColumn<PartsUsed, String> nameCol;
    @FXML
    private TableColumn<PartsUsed, Double> costCol;
    @FXML
    private TableColumn<PartsUsed, Integer> quantityCol;
    @FXML
    private TableColumn<PartsUsed, String> installDateCol;
    @FXML
    private TableColumn<PartsUsed, String> expireDateCol;
    @FXML
    private TableColumn<PartsUsed, String> registrationNoCol;
    @FXML
    private TableColumn<PartsUsed, String> customerNameCol;
    @FXML
    private TableColumn<PartsUsed, Integer> bookingIdCol;
    
    @FXML
    public TableView<PartsUsedPerVehicle> partInfoTable;
    @FXML
    public TableColumn<PartsUsedPerVehicle, String> regCol;
    @FXML
    public TableColumn<PartsUsedPerVehicle, String> customerCol;
    @FXML
    public TableColumn<PartsUsedPerVehicle, String> pNameCol;
    @FXML
    public TableColumn<PartsUsedPerVehicle, Integer> qCol;
    
    @FXML
    public TableView<VehicleCustomerInfo> custInfoTable;
    @FXML
    public TableColumn<VehicleCustomerInfo, String> regNoCol;
    @FXML
    public TableColumn<VehicleCustomerInfo, String> fullCustomerNameCol;
    @FXML
    public TableColumn<VehicleCustomerInfo, String> bookingDateCol;
    
    private static PartsUsed part = new PartsUsed(0, "", 0.0, 0, "", "", "", "", 0);
    private static PartsUsedPerVehicle partVehicle = new PartsUsedPerVehicle("", "", "",0);
    private static VehicleCustomerInfo custVehicle = new VehicleCustomerInfo("", "", "");
    
    ObservableList<PartsUsed> data;
    ObservableList<PartsUsedPerVehicle> partVehData;
    ObservableList<VehicleCustomerInfo> customerData;
    private ObservableList<VehicleCustomerInfo> tempData = FXCollections.observableArrayList();
    // ObservableList to hold the bookingIds and partNames for the bookingIdCombo and partNameCombo
    ObservableList<Integer> bookingId = FXCollections.observableArrayList();
    ObservableList<String> namesCombo = FXCollections.observableArrayList();
    private int usedPartID;
    private PartsUsed partDisplay = null ;
    private LocalDate scheduleDateFromBooking;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #fcbabf;");
                    Platform.runLater(() -> setDisable(true));
                }
            }
        };
        dateOfInstall.setDayCellFactory(dayCellFactory);
        showAll.setSelected(true);
        ObservableList<String> searchOption = FXCollections.observableArrayList();
        searchOption.add("Customer Name");
        searchOption.add("Vehicle registration");
        
        searchCombo.setValue("Customer Name");
        searchCombo.setItems(searchOption);
        
        try {
            fillPartsNameCombo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fillBookingIdCombo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        installParts.setTooltip(new Tooltip("Complete all the fields and click to install a part"));
        viewCustomerInfo.setTooltip(new Tooltip("Select a row from the repair table and click to view booking information for the selected vehicle"));
        clear.setTooltip(new Tooltip("Click to clear all the fields"));
        viewPartsInfo.setTooltip(new Tooltip("Select a row from the repair table and click to view the parts used for the selected vehicle"));
        partStockButton.setTooltip(new Tooltip("Click to go to parts stock "));
        deleteParts.setTooltip(new Tooltip("Select a row from the table and click to delete a part"));
        backButton.setTooltip(new Tooltip("Click to go back home"));

        usedIdCol.setCellValueFactory(
                new PropertyValueFactory<>("usedID"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("partName"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        installDateCol.setCellValueFactory(
                new PropertyValueFactory<>("installDate"));
        expireDateCol.setCellValueFactory(
                new PropertyValueFactory<>("warrantyExpireDate"));
        registrationNoCol.setCellValueFactory(
                new PropertyValueFactory<>("vehicleRegNo"));
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<>("customerFullName"));
        bookingIdCol.setCellValueFactory(
                new PropertyValueFactory<>("bookingID"));
        regNoCol.setCellValueFactory(
                new PropertyValueFactory<>("vehicleregNo"));
        fullCustomerNameCol.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));
        bookingDateCol.setCellValueFactory(
                new PropertyValueFactory<>("bookingDate"));
        regCol.setCellValueFactory(
                new PropertyValueFactory<>("vehicleRegNo"));
        customerCol.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));
        pNameCol.setCellValueFactory(
                new PropertyValueFactory<>("partUsedName"));
        qCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        
        try {
            buildPartsUsedData();
            buildCustomerData();

        } catch (Exception e) {
            e.printStackTrace();

        }
        showPartInfo.setWrapText (true);
        showPartInfo.setEditable(false);
       
         table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {
                        partDisplay = table.getSelectionModel().getSelectedItem();
                        String selectedPartName = partDisplay.getPartName();
                        String partDescription = findPartsDescription(partDisplay.getPartName());
                        if (partDisplay != null) {
                             showPartInfo.setText("Part Name: " + partDisplay.getPartName() + "\n\n" + "Part Description: " +
                                     partDescription + "\n\n" + "Part Cost: £" + partDisplay.getCost() + "\n");
                        }
                }
                    catch(Exception e)
                            {
                             alertError("Cannot load Parts information.");
                            }
                }
         });
        
        if(!Authentication.LoginController.isAdmin)
        {
            users.setDisable(true);
        }
       parts.setStyle("-fx-background-color:  #85C1E9;");
    }
    
    /**
     * This method adds data from the text fields to the vehiclePartsUsed table in the database.
     * @param part
     * @throws ClassNotFoundException 
     */
    public void createData(PartsUsed part) throws ClassNotFoundException {

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            System.out.println("Opened database successfully.");
          
            String sql = "insert into vehiclePartsUsed(parts_id,quantity,cost, dateOfInstall, dateOfWarrantyExpire,vehicleID, customerID, bookingID) values(?,?,?,?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);

                state.setInt(1, findPartsID(part.getPartName()));
                state.setInt(2, part.getQuantity());
                state.setDouble(3, findPartsCost(part.getPartName()));
                state.setString(4, part.getInstallDate());
                state.setString(5, findExpireDate(part.getInstallDate()));
                state.setInt(6, findVehicleID(part.getBookingID()));
                state.setInt(7, findCustomerID(part.getBookingID()));
                state.setInt(8, part.getBookingID()); 
                
                state.execute();
                state.close();
                conn.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }

    }
    
    /**
     * This method fills data to the tableView from the vehiclePartsUsed table in the database.
     */
    public void buildPartsUsedData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            conn = (new sqlite().connect());
            String SQL = "Select * from vehiclePartsUsed";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                
                part.setUsedID(rs.getInt(1));
                part.setPartName(findPartsName(rs.getInt(2)));
                part.setQuantity(rs.getInt(3));
                part.setCost(rs.getDouble(4));
                part.setInstallDate(rs.getString(5));
                part.setWarrantyExpireDate(rs.getString(6));
                part.setVehicleRegNo(findVehReg(rs.getInt(7)));
                part.setcustomerFullName(findCustomerName(rs.getInt(8)));
                part.setBookingID(rs.getInt(9));
                

                data.add(new PartsUsed(part.getUsedID(), part.getPartName(), part.getCost(), part.getQuantity(), part.getInstallDate(), part.getWarrantyExpireDate(), part.getVehicleRegNo(), part.getCustomerFullName(), part.getBookingID()));
                
                searchField.clear();
                searchFilter(data);
            }

            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
           alertError("Error on building data for repair information.");
        }

    }
    
    /**
     * This method is used to show the parts used per vehicle in a tableView.
     * @param event 
     */
    @FXML
    public void buildPartsDataBtn(ActionEvent event){
        try{
            String vehReg = table.getSelectionModel().getSelectedItem().getVehicleRegNo();
            int vehicleId = findVehicleID(vehReg);
            partVehData = FXCollections.observableArrayList();
            try {
                Connection conn = null;
                conn = (new sqlite().connect());
                String SQL = "Select parts_id,quantity,customerID from vehiclePartsUsed where vehicleID ='" + vehicleId + "'";
                ResultSet rs = conn.createStatement().executeQuery(SQL);
                while (rs.next()){   

                    partVehicle.setRegNumber("");
                    partVehicle.setCustomerName("");
                    partVehicle.setPartUsedName("");
                    partVehicle.setQuantity(0);
                    partVehicle.setPartUsedName(findPartsName(rs.getInt("parts_id")));
                    partVehicle.setQuantity(rs.getInt("quantity"));
                    partVehicle.setCustomerName(findCustomerName(rs.getInt("customerID")));
                    partVehicle.setRegNumber(vehReg);

                    partVehData.add(new PartsUsedPerVehicle(partVehicle.getRegNumber(),partVehicle.getCustomerName(),partVehicle.getPartUsedName(),partVehicle.getQuantity()));

                }        

                partInfoTable.setItems(partVehData);
                rs.close();
                conn.close();
            }
        
            catch(Exception e){

                alertError("Error on building parts used per vehicle data. Please try again later.");
            }
        }
        catch(Exception e){
            alertInformation("Please select a row to view the parts used information for that vehicle.");
        }
    }
    
   
   /**
    * This method is used to show the customer booking information in a tableView when the user selects a row from the repair information table and clicks the viewCustomerInfo button.
    * @param event 
    */
    @FXML
    public void buildCustomerDataBtn(ActionEvent event)
    {
        futureBooking.setSelected(false);
        pastBooking.setSelected(false);
        showAll.setSelected(false);
        try{
            String vehReg = table.getSelectionModel().getSelectedItem().getVehicleRegNo();
            int vehicleId = findVehicleID(vehReg);
            tempData.clear();
            customerData = FXCollections.observableArrayList();


            try {
                Connection conn = null;
                conn = (new sqlite().connect());
                String SQL = "Select customer_id, scheduled_date from booking where vehicleID ='" + vehicleId + "'";
                ResultSet rs = conn.createStatement().executeQuery(SQL);
                while (rs.next()) {   
                    custVehicle.setBookingDate("");
                    custVehicle.setCustomerName("");
                    custVehicle.setRegNumber("");
                    custVehicle.setCustomerName(findCustomerName(rs.getInt("customer_id")));
                    custVehicle.setRegNumber(vehReg);
                    custVehicle.setBookingDate(rs.getString("scheduled_date"));
                    customerData.add(new VehicleCustomerInfo(custVehicle.getRegNumber(),custVehicle.getCustomerName(), custVehicle.getBookingDate()));

                }        
                tempData.addAll(customerData);
                custInfoTable.setItems(customerData);
                rs.close();
                conn.close();
            }

            catch(Exception e){

                alertError("Error on building customer data. Please try again later.");
            }
        }catch(Exception e){
            alertInformation( "Please select a row to view the customer/booking information for that vehicle.");
        }
    }
    
    /**
     * This method is used to show all the customer booking information in a tableView.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void buildCustomerData() throws ClassNotFoundException, SQLException {
        customerData = FXCollections.observableArrayList();
        tempData.clear();
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            String SQL = "Select * from booking";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
  
            while (rs.next()) {
                custVehicle.setBookingDate("");
                custVehicle.setCustomerName("");
                custVehicle.setRegNumber("");
                custVehicle.setCustomerName(findCustomerName(rs.getInt("customer_id")));
                custVehicle.setRegNumber(findVehReg(rs.getInt("vehicleID")));
                custVehicle.setBookingDate(rs.getString("scheduled_date"));
                
                customerData.add(new VehicleCustomerInfo(custVehicle.getRegNumber(),custVehicle.getCustomerName(), custVehicle.getBookingDate()));
            }
            
            tempData.addAll(customerData);
            custInfoTable.setItems(customerData);
            rs.close();
            conn.close();
        } catch (Exception e) {
            alertError("Error in building customer Data.");
        }
    }
    
    /**
     * This method is used to install a part in a vehicle when the user clicks the Install parts button.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @FXML
    public void installButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to install the part?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeNo) {
            return;
        }
        
        if(!isFieldsCompleted()) {
            return;
        }
        
        if(!verifyQuantity(quantity.getText())){
            return;
        }
        if(!isQuantityZero(quantity.getText())){
            return;
        }
        else{
            part.setBookingID(bookingIdCombo.getValue());
            part.setPartName(partNameCombo.getValue());
            part.setQuantity(Integer.parseInt(quantity.getText()));
            part.setInstallDate(dateOfInstall.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
            int stockAvailable = findStockLevel(part.getPartName());
            if ((stockAvailable > 0) && stockAvailable >= part.getQuantity()) {
               decreaseStockLevel(part.getPartName(), stockAvailable);
               createData(part);
               buildPartsUsedData();
               dateOfInstall.setDisable(false);
               clearFields();
               
               addBill(part.getBookingID(),part.getQuantity(),findPartsCost(part.getPartName()));
               
            } else if (stockAvailable == 0) {
                alertInformation("The stock is currently empty.");
            } else if (stockAvailable < part.getQuantity()) {
                alertInformation("The quantity you want is not available in the stock.");
            }
            
        }
        
    }
    
    /**
     * This method is used to show the installed date of a part in the datePicker when the user selects a bookingId in the bookingIdCombo.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @FXML
    public void showInstallDate (ActionEvent event) throws IOException, ClassNotFoundException {
        String date = "";
        
        if(bookingIdCombo.getValue() == null){
            return;
        }
        int comboBookingValue = bookingIdCombo.getValue();
        Connection conn = null;
        try {

            conn = (new sqlite().connect());
            String SQL = "Select scheduled_date from booking where booking_id ='" + comboBookingValue + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                date = rs.getString("scheduled_date");
            }  
            
            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
           
        }
        LocalDate local = convertStringToDateForBookingCombo(date) ;
        
        dateOfInstall.setValue(local);
        dateOfInstall.setDisable(true);
    }
    
    /**
     * This method checks whether the user entered a valid quantity value.
     * @param quantity
     * @return 
     */
    public boolean verifyQuantity(String quantity){
        boolean check = true;
        if(!quantity.matches("[0-9]+")){
            alertError("Please enter a valid quantity.");
            check = false;
            
        }
        return check;
    }
    
    /**
     * This method checks whether the user entered 0 for the quantity.
     * @param quantity
     * @return 
     */
    public boolean isQuantityZero(String quantity){
        boolean check = true;
        if(Integer.parseInt(quantity) == 0){
            alertError("Quantity cannot be 0.");
            check = false;
            
        }
        return check;
    }
    
    /**
     * This method is used to find the stock level of a part from the vehiclePartsStock table.
     * @param name
     * @return
     * @throws ClassNotFoundException 
     */
    public int findStockLevel(String name) throws ClassNotFoundException {
        int stockLevel = 0;

        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select stockLevelsOfParts from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                stockLevel = rs.getInt("stockLevelsOfParts");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return stockLevel;

    }

    /**
     * When the user withdraws a part for a repair this method is called which adds the parts cost and updates the total cost for a particular booking.
     * @param bookingId
     * @param quantity
     * @param cost
     * @throws ClassNotFoundException 
     */
     public void addBill(int bookingId, int quantity, double cost) throws ClassNotFoundException {
        boolean bookingExist = false;
        double totalBill = 0.0;
        try {
                
                bookingExist = checkIfBookingExists(bookingId);
                if(bookingExist){
                
                  CustomerAccount.gui.GuiController.showBill.addCostToBillParts(CustomerAccount.gui.GuiController.showBill, cost, part.getQuantity());
                  Double totalUnitPartsCost = CustomerAccount.gui.GuiController.showBill.getPartsCost();
                 
                  Double oldPartsCost = getOldPartsCost(bookingId);
                  Double totalPartsCost = oldPartsCost + totalUnitPartsCost;
                  Connection conn = null;
                  
                  conn = (new sqlite().connect());
                  String sql = "UPDATE bill SET partsCost=?,totalCost=? WHERE bookingID=?";
                  PreparedStatement state = conn.prepareStatement(sql);
                  state.setDouble(1, totalPartsCost);
                  double mechanicCost = getMechanicCost(bookingId);
                  totalBill = totalPartsCost + mechanicCost;
                  
                  state.setDouble(2,totalBill);
                  state.setInt(3, bookingId);
                  GuiController.showBill.setMechanicCost(0);
                  GuiController.showBill.setPartsCost(0);
                  state.execute();
                  state.close();
                 
                  conn.close();
                 
                  alertInformation("The bill has been added for BookingID " + bookingId + ".");
                
                  buildPartsUsedData();
                }
            } 
        catch (Exception e) {
            alertInformation("Error in adding bill.");

        }
        
    }
    /**
     * This method is used to get the parts costs previously added to the bill of a particular booking id.
     * @param bookingID
     * @return
     * @throws ClassNotFoundException 
     */
    public double getOldPartsCost(int bookingID) throws ClassNotFoundException{
        double value  = 0.0;
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select partsCost from bill where bookingID ='" + bookingID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                value = rs.getDouble("partsCost");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
       
    /**
     * This method is used to get the mechanic cost from the database.
     * @param bookingID
     * @return
     * @throws ClassNotFoundException 
     */
    public double getMechanicCost(int bookingID)throws ClassNotFoundException{
        double value  = 0.0;
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened database successfully to get mechanic cost");
            String SQL = "Select mechanicCost from bill where bookingID ='" + bookingID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                value = rs.getDouble("mechanicCost");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return value;
    }
    
    /**
     * This method checks whether a booking exists before installing a part.
     * @param bookingID
     * @return
     * @throws ClassNotFoundException 
     */
    public boolean checkIfBookingExists(int bookingID) throws ClassNotFoundException{
        boolean value  = false;
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Openend database successfully to check if booking exists.");
            String SQL = "Select bookingID from bill where bookingID ='" + bookingID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                value = true;
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return value;
    }
    
    public void decreaseStockLevel(String name, int stockAvailable) throws ClassNotFoundException {
        Connection conn = null;
        
        int quantityWanted = part.getQuantity();
        int newStockLevel = stockAvailable - quantityWanted;
        
        try {
            
            conn = (new sqlite().connect());
            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE nameofPart =?";
            PreparedStatement state = conn.prepareStatement(sql);
            
            state.setInt(1, newStockLevel);
            state.setString(2, part.getPartName());
            state.execute();

            state.close();
            conn.close();
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    /**
     * This method asks for the confirmation of the user before deleting a partUsed.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @FXML
    public void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        int id;
        try{
            id = table.getSelectionModel().getSelectedItem().getUsedID();
        }
        catch(Exception e){
            alertError("Please select a row first to delete a part.");
            return;
        }
        part.setUsedID(id);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("");
                alert.setContentText("Are you sure you want to delete the parts?");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == buttonTypeYes && isPartsDeleted(part)) {
                   alertInformation("RepairID: " + id + " has been deleted.");
                }
                buildPartsUsedData();
    }
    
    /**
     * This method is used to delete the selected PartUsed from the database.
     * @param part
     * @return
     * @throws ClassNotFoundException 
     */
    public boolean isPartsDeleted(PartsUsed part) throws ClassNotFoundException {
        boolean partsDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getUsedID();

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String sql = "DELETE FROM vehiclePartsUsed WHERE partsUsedID =?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, part.getUsedID());
            state.executeUpdate();

            state.close();
            conn.close();

            partsDeleted = true;
            clearFields();
        } catch (SQLException e) {
           System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return partsDeleted;
    }
        
    
    /**
     * This method is used to get the vehicleId from the database.
     * @param vehReg
     * @return
     * @throws ClassNotFoundException 
     */
    public int findVehicleID(String vehReg) throws ClassNotFoundException {
        int vehRegID = 0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());
            String SQL = "Select vehicleID from vehicleList where RegNumber ='" + vehReg + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegID = rs.getInt("vehicleID");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehRegID;
    }
    
    /**
     * This method is used to find the cost of a part from the database.
     * @param name
     * @return
     * @throws ClassNotFoundException 
     */
    public double findPartsCost(String name) throws ClassNotFoundException {
        double cost  = 0.0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select cost from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                cost = rs.getInt("cost");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return cost;
    }
    
    /**
     * This method is used to find the partsId of a part.
     * @param name
     * @return
     * @throws ClassNotFoundException 
     */
    public int findPartsID(String name) throws ClassNotFoundException {
        int partID = 0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select parts_id from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                partID = rs.getInt("parts_id");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return partID;
    }
    
    /**
     * This method is used to find the expiryDate of a part.
     * Each part has one year warranty from the day of installation.
     * @param installDate
     * @return 
     */
    public String findExpireDate(String installDate) {

        Date date = null;
        String warrantyexpire = "";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {

            date = df.parse(installDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, 1); 
            Date warrantyExpire = cal.getTime();
            warrantyexpire = df.format(warrantyExpire);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return warrantyexpire;
    }
    
    /**
     * This method is used to convert a String date to a localDate.
     * @param dateString
     * @return 
     */
    public LocalDate convertStringToDate(String dateString) {
        LocalDate date = null;

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       
        try {

            date = LocalDate.parse(dateString, df);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * This method is used to find the vehicleIdfrom the database.
     * @param bookingID
     * @return
     * @throws ClassNotFoundException 
     */
    public int findVehicleID(int bookingID) throws ClassNotFoundException {
        int vehRegID = 0;
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select vehicleID from booking where booking_id='" + bookingID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegID = rs.getInt("vehicleID");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehRegID;
    }
   
    /**
     * This method is used to find the customerId from the database.
     * @param bookingID
     * @return
     * @throws ClassNotFoundException 
     */
    public int findCustomerID(int bookingID) throws ClassNotFoundException {
        int customerID = 0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());
            String SQL = "Select customer_id from Booking where booking_id='" + bookingID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customerID = rs.getInt("customer_id");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding customerId");
        }
        return customerID;
    }
    
    /**
     * This method is used to findCustomerName from the database.
     * @param customerID
     * @return
     * @throws ClassNotFoundException 
     */
    public String findCustomerName(int customerID) throws ClassNotFoundException {
        
        String customerName = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select customer_fullname from customer where customer_id='" + customerID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customerName = rs.getString("customer_fullname");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return customerName;
    }
    
    /**
     * This method is used to find the vehicle registration number from the database.
     * @param vehicleID
     * @return
     * @throws ClassNotFoundException 
     */
    public String findVehReg(int vehicleID) throws ClassNotFoundException {
        String vehRegNo = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select RegNumber from vehicleList where vehicleID='" + vehicleID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegNo = rs.getString("RegNumber");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return vehRegNo;
    }
    
    /**
     * This method is used to find a part name from the database.
     * @param ID
     * @return
     * @throws ClassNotFoundException 
     */
    public String findPartsName(int ID) throws ClassNotFoundException{
        String name = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select nameofPart from vehiclePartsStock where parts_id ='" + ID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                name = rs.getString("nameofPart");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
    
    /**
     * This method is used to find the cost of a part from the database.
     * @param ID
     * @return
     * @throws ClassNotFoundException 
     */
    public Double findCostFromStock(int ID) throws ClassNotFoundException {
        double cost = 0.0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select cost from vehiclePartsStock where parts_id='" + ID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                cost = rs.getDouble("cost");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return cost;
    }
    
    /**
     * This method is used to find the parts description from the database.
     * @param name
     * @return
     * @throws ClassNotFoundException 
     */
    public String findPartsDescription(String name) throws ClassNotFoundException {
        String description  = "";
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select description from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                description = rs.getString("description");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return description;
    }
    
    
    
    /**
     * This method is used to filter all the past customer booking dates and show it in the tableView.
     * @throws ClassNotFoundException 
     */
    @FXML
    private void filterByPast() throws ClassNotFoundException {
       if(!pastBooking.isSelected()){  
           pastBooking.setSelected(true);
           return;
        }
         futureBooking.setSelected(false);
         showAll.setSelected(false);
         
         customerData = FXCollections.observableArrayList(tempData);
         LocalDate now = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for(int i=0; i<customerData.size(); i++){
            LocalDate tempDate = LocalDate.parse(customerData.get(i).getBookingDate(),formatter);
            if(now.isBefore(tempDate) || now.equals(tempDate)){
                customerData.remove(i);
                i--;
            }
        }
        if(customerData.isEmpty()){
           alertInformation("There are no past bookings for the vehicles.");
        }
        
        custInfoTable.setItems(customerData);
    }
       
     /**
      * This method is used to filter all the future customer booking dates and show it in the tableView.
      * @throws ClassNotFoundException 
      */   
    @FXML
    private void filterByFuture() throws ClassNotFoundException
    {
        if(!futureBooking.isSelected()){
            futureBooking.setSelected(true);
            return;
        }
   
        showAll.setSelected(false);
        pastBooking.setSelected(false);
        
        
        customerData = FXCollections.observableArrayList(tempData);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for(int i=0; i<customerData.size(); i++)
        {
            LocalDate tempDate = LocalDate.parse(customerData.get(i).getBookingDate(),formatter);
  
            if(now.isAfter(tempDate) || now.equals(tempDate)) {
                customerData.remove(i);
                i--;
            }
        }
        
        if(customerData.isEmpty())
        {
            alertInformation("There are no future bookings. Please create a booking for your vehicle in the Diagnosis and Repair Section.");
        }
        custInfoTable.setItems(customerData);    
    }
    
    /**
     * This method is used to show all the customer booking information.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @FXML
    private void showAllBooking() throws ClassNotFoundException, SQLException
    {
        if(!showAll.isSelected())
        {
            showAll.setSelected(true);
            return;
        }
        
        futureBooking.setSelected(false);
        pastBooking.setSelected(false);

        
        buildCustomerData();
    }
    
    /**
     * This method is used to search for a partsUsed by either full/partial vehicle registration number or customer name.
     * @param data 
     */
    public void searchFilter(ObservableList<PartsUsed> data) {
            System.out.println("new");
            FilteredList<PartsUsed> filteredData = new FilteredList<>(data, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observableValue, oldValue2, newValue2) -> {
                filteredData.setPredicate((Predicate<? super PartsUsed>) partsUsed -> {
                    if (newValue2 == null || newValue2.isEmpty()) {
                        return true;
                    }
                    String newValLow = newValue2.toLowerCase();
                    
                    if (partsUsed.getCustomerFullName().toLowerCase().contains(newValLow) && searchCombo.getValue().equalsIgnoreCase("Customer Name")) {
                        return true;
                    } else if (partsUsed.getVehicleRegNo().toLowerCase().contains(newValLow) && searchCombo.getValue().equalsIgnoreCase("Vehicle registration")) {
                        return true;
                    }

                    return false;
                }); 
            });
                SortedList<PartsUsed> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
        });
    }
    
    /**
     * This method is used to fill up the booking id combo from the booking table in the database.
     * @throws ClassNotFoundException 
     */
    public void fillBookingIdCombo() throws ClassNotFoundException {
        String date = "";
        LocalDate today = LocalDate.now();
        Connection conn = null;
        try {
            
            conn = (new sqlite().connect());
            String SQL = "Select booking_id,scheduled_date from Booking";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                date = rs.getString("scheduled_date");
                LocalDate scheduleDate = convertStringToDateForBookingCombo(date);
                if(today.isBefore(scheduleDate) || today.equals(scheduleDate)) {
                  bookingId.add(rs.getInt("booking_id"));
                }
            }
            bookingIdCombo.setItems(bookingId);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    
    
    
    /**
     * This method is used to convert a String date to a localDate.
     * @param dateString
     * @return 
     */ 
    public LocalDate convertStringToDateForBookingCombo(String dateString) {
        LocalDate date = null;
        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        try {

            date = LocalDate.parse(dateString, df);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * This method is used to fill up the parts name combo from the vehiclePartsStock table in the database.
     * @throws ClassNotFoundException 
     */
    public void fillPartsNameCombo() throws ClassNotFoundException {

        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            
            String SQL = "Select nameofPart from vehiclePartsStock";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                namesCombo.add(rs.getString("nameofPart"));
            }
            partNameCombo.setItems(namesCombo);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }
    
    
    /**
     * This method is used to redirect the user to the parts Record page when the user clicks the Parts Record button.
     * @param event
     * @throws IOException 
     */
    @FXML 
    private void partsUsedPage(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    /**
     * This method is used to logout a user.
     * @param event
     * @throws IOException 
     */
    @FXML 
    private void logout(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Login.fxml"));
        pane.getChildren().setAll(rootPane);
        Authentication.LoginController.isAdmin = false;
    }
    
    @FXML 
    private void users(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    /**
     * This method is used to redirect the user to the customer page when the user clicks the Customer Account button.
     * @param event
     * @throws IOException 
     */
    @FXML 
    private void cus(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/gui.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    /**
     * This method is used to redirect the user to the vehicle Record Page when the user clicks the Vehicle Record page.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void vehicleRecord(ActionEvent event) throws IOException {
          AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
          pane.getChildren().setAll(rootPane);
    }
    
    /**
     * This method is used to redirect the user to the AddVehicle page where the user can add vehicles in the system.
     * @param event
     * @throws IOException 
     */
    private void vehicleEntry(ActionEvent event) throws IOException {
         AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/AddVehicle.fxml"));
         pane.getChildren().setAll(rootPane);
    }
    
    /**
     * This method is used to redirect the user to the Diagnosis and Repair page where the user can add a booking in the system.
     * @param event
     * @throws IOException 
     */
    @FXML 
    private void diag(ActionEvent event) throws IOException {
         AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));
         pane.getChildren().setAll(rootPane);
    }
    
    /**
     * This method redirects the user to the partsStock page when the Parts Stock button is pressed.
     * @param event
     * @throws IOException 
     */
    @FXML 
    private void pStock(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/partStock.fxml"));
        pane.getChildren().setAll(rootPane);
    }
    
    /**
     * This method redirect the user to the home page.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @FXML
    public void backButton(ActionEvent event) throws IOException, ClassNotFoundException {   
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
    
    /**
     * This method checks if the user entered any white spaces at the front of the text.
     * If there is a white space in front of the text the user is informed not to have white space at the start of the fields.
     * @return 
     */
    public boolean checkForWhiteSpace() {
        if(quantity.getText().trim().isEmpty())
         {
             alertError("You cannot have a white space at the start of the fields.");
             return false;
         }
        return true;
    }
    
    /**
     * When the clear button is pressed this method is called and the fields are cleared.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @FXML
    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException {
        clearFields();
    }
    
    /**
     * This method is used to check whether the user has completed all the fields or not.
     * @return 
     */
    public boolean isFieldsCompleted() {
        if (partNameCombo.getValue() == null || quantity.getText().equals("") || dateOfInstall.getValue().equals("") || bookingIdCombo.getValue() == null) {
            alertInformation("Please complete all the fields.");
            return false;
        }
        return true;
    }
    
    /**
     * This method is used to initiateInstalling parts from the booking section.
     * @param bookingID
     * @param date 
     */
    public void initiateInstallPart(int bookingID, LocalDate date) {
            bookingIdCombo.setValue(bookingID);
            dateOfInstall.setValue(date);
            dateOfInstall.setDisable(true);
            
        }

    /**
     * This method is used to clear all the fields/comboBoxes and datePicker.
     */
    public void clearFields() {
        partNameCombo.setValue(null);
        quantity.clear();
        dateOfInstall.setValue(null);
        bookingIdCombo.setValue(null);

    }
 
    /**
     * This method is used to show an information dialog to the user.
     * @param information 
     */
    public void alertInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }
   
    /**
     * This method is used to show an error dialog to the user.
     * @param error 
     */
    public void alertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
    
    /**
     * This method is used to show a confirmation dialog to the user. 
     * @param confirmation 
     */
    public void alertConfirmation(String confirmation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation.");
        alert.setHeaderText(null);
        alert.setContentText(confirmation);
        alert.showAndWait();
    }

}
