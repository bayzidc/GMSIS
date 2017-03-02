/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import Authentication.sqlite;
import CustomerAccount.gui.BillController;
import PartsRecord.logic.parts;
import PartsRecord.logic.partsUsed;
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
import PartsRecord.logic.partsUsed;
import PartsRecord.logic.vehicleCustomerInfo;
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

/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartsController implements Initializable {

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
    //@FXML
    //private ComboBox 
    @FXML
    private Button installParts;
    @FXML
    private Button editParts;
    @FXML
    private Button deleteParts;
    @FXML
    private Button clear;
    @FXML
    private Button addBill;
    @FXML
    private TableView<partsUsed> table;
    @FXML
    private TableColumn<partsUsed, Integer> usedIdCol;
    @FXML
    private TableColumn<partsUsed, String> nameCol;
    @FXML
    private TableColumn<partsUsed, Double> costCol;
    @FXML
    private TableColumn<partsUsed, Integer> quantityCol;
    @FXML
    private TableColumn<partsUsed, String> installDateCol;
    @FXML
    private TableColumn<partsUsed, String> expireDateCol;
    @FXML
    private TableColumn<partsUsed, String> registrationNoCol;
    @FXML
    private TableColumn<partsUsed, String> customerNameCol;
    @FXML
    private TableColumn<partsUsed, Integer> bookingIdCol;
    @FXML // Observable list to hold partsUsed object.
    ObservableList<partsUsed> data;
    public int usedPartID;
    public static partsUsed part = new partsUsed(0, "", 0.0, 0, "", "", "", "", 0);
    ObservableList<Integer> bookingId = FXCollections.observableArrayList();
    
    @FXML
    public TableView<vehicleCustomerInfo> custInfoTable;
    @FXML
    public TableColumn<vehicleCustomerInfo, String> fullCustomerNameCol;
    @FXML
    public TableColumn<vehicleCustomerInfo, String> bookingDateCol;
    @FXML 
    public TableColumn<vehicleCustomerInfo, String> regNoCol;
    
    public static vehicleCustomerInfo custVehicle = new vehicleCustomerInfo("","","");

    ObservableList<vehicleCustomerInfo> customerData;
    
    

    @FXML
    public void backButton(ActionEvent event) throws IOException, ClassNotFoundException // method which goes back to admin page
    {   // When pressed the back button load the Admin.fxml file
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();

    }

    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException {
        clearFields();

    }

    public boolean isFieldsCompleted() {
        if (partNameCombo.getValue() != null || quantity.getText().equals("") || dateOfInstall.getValue().equals("") || bookingIdCombo.getValue() != null) {
            return false;
        }
        return true;
    }

    @FXML
    public void addBill() throws ClassNotFoundException {
        BillController.showBill.addCostToBill(BillController.showBill, part, part.getQuantity());
        Double totalCost = BillController.showBill.getTotalCost();
        //int customer_id = Integer.parseInt(findCustomerID(table.getSelectionModel().getSelectedItem().getCustomerFullName()));
        try {
            Connection conn = null;

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Creating data.");

            String sql = "insert into bill(customerID, bookingID, totalCost, settled) values(?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            //state.setInt(1, customer_id);
            state.setInt(2, 1);
            state.setDouble(3, totalCost);
            state.setBoolean(4, false);
            BillController.showBill.setTotalCost(0);
            state.execute();

            state.close();
            conn.close();
        }//submit=true;
        catch (Exception e) {
            alertInformation("Error.");
            System.out.println("Here 8.");
        }
    }

    public void installButton() throws IOException, ClassNotFoundException {

        part.setPartName(partNameCombo.getValue());
        part.setQuantity(Integer.parseInt(quantity.getText()));
        part.setInstallDate(dateOfInstall.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        part.setBookingID(bookingIdCombo.getValue());
        int stockAvailable = findStockLevel(part.getPartName());
        if ((stockAvailable > 0) && stockAvailable >= part.getQuantity()) {
            decreaseStockLevel(part.getPartName(), stockAvailable);
            createData(part);
            buildPartsUsedData();
            clearFields();
        } else if (stockAvailable == 0) {
            alertInformation("The stock is empty.");
        } else if (stockAvailable < part.getQuantity()) {
            alertInformation("The quantity you want  is not available in the stock");

        }
    }

    public int findStockLevel(String name) throws ClassNotFoundException {
        int stockLevel = 0;

        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select stockLevelsOfParts from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                stockLevel = rs.getInt("stockLevelsOfParts");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return stockLevel;

    }

    public void decreaseStockLevel(String name, int stockAvailable) throws ClassNotFoundException {
        Connection conn = null;
        System.out.println("I am here in the decrease stocKlevel");
        int quantityWanted = part.getQuantity();
        System.out.println("Quantity Wanted : " + quantityWanted);
        int newStockLevel = stockAvailable - quantityWanted;
        System.out.println("NewstocKlevel : " + newStockLevel);
        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE nameofPart =?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setInt(1, newStockLevel);
            state.setString(2, part.getPartName());
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @FXML
    public void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        try {
            if (partNameCombo.getValue() == null || quantity.getText().equals("") || dateOfInstall.getValue().equals("") || bookingIdCombo.getValue() == null) {
                alertError("Select a row in order to delete a part.");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("");
                alert.setContentText("Are you sure you want to delete the parts?");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();
                
                int idSelected = table.getSelectionModel().getSelectedItem().getUsedID();
                
                if (result.get() == buttonTypeYes && isPartsDeleted(part)) {

                    alertInformation("PartsUsedID: " + idSelected + " has been deleted.");

                }
                buildPartsUsedData();
            }
        } catch (Exception e) {
            System.out.println(e);

        }

    }

    private boolean isPartsDeleted(partsUsed part) throws ClassNotFoundException {
        boolean partsDeleted = false;

       int ID = table.getSelectionModel().getSelectedItem().getUsedID();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
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
            System.exit(0);
        }
        return partsDeleted;
    }
    
    public int findOldQuantity(PartsRecord.logic.partsUsed part) throws ClassNotFoundException {
        int bookingId =part.getBookingID();
        int oldQuantity = 0;
        Connection conn = null;
          try { // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully 1");
            String sql = "SELECT quantity FROM vehiclePartsUsed where bookingID='" + bookingId + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                   oldQuantity = rs.getInt("quantity");
                    
                }

            rs.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in finding old quantity.");
        }
        return oldQuantity;
    }
    
    public String findOldName(PartsRecord.logic.partsUsed part) throws ClassNotFoundException {
        int bookingId =part.getBookingID();
        String oldName = "";
        Connection conn = null;
          try { // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully 1");
            String sql = "SELECT name FROM vehiclePartsUsed where bookingID='" + bookingId + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                   oldName = rs.getString("name");
                    
                }
            rs.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in finding old name.");
        }
        return oldName;
    }
    
    
    
    public void increaseStockLevel(String name, int stockAvailable, int oldQuantity) throws ClassNotFoundException {
        Connection conn = null;
        System.out.println("I am here in the increaseStockLevel");
        int newQuantityWanted = part.getQuantity();
        System.out.println("newQuantity : " + newQuantityWanted);
        
        int quantityLeft = oldQuantity - newQuantityWanted;
        System.out.println("Quantity left:" + quantityLeft);
        int newStockLevel = stockAvailable + quantityLeft;
        System.out.println("new stock level:" + newStockLevel);
        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE nameofPart =?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setInt(1, newStockLevel);
            state.setString(2, part.getPartName());
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Error in increase stock Level");
        }
    }
    
    public void decreaseEditStockLevel(String name, int stockAvailable, int oldQuantity) throws ClassNotFoundException {
        Connection conn = null;
        System.out.println("I am here in the decreaseEditStockLevel");
        int newQuantityWanted = part.getQuantity();
        System.out.println("newQuantity : " + newQuantityWanted);
        
        int quantityLeft =newQuantityWanted - oldQuantity ;
        System.out.println("Quantity left:" + quantityLeft);
        int newStockLevel = stockAvailable - quantityLeft;
        System.out.println("new stock level:" + newStockLevel);
        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE nameofPart =?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setInt(1, newStockLevel);
            state.setString(2, part.getPartName());
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Error in increase stock Level");
        }
    }
    
    @FXML
    public void editData(PartsRecord.logic.partsUsed part) throws ClassNotFoundException, IOException { 
          int partOldUsedQuantity = findOldQuantity(part);
          String partOldName = findOldName(part);
          String newName = part.getPartName();
          System.out.println("NewPartName:" + newName);
          System.out.println("OldPartName:" + partOldName);
          System.out.println("oldUsedQuantity : " + partOldUsedQuantity);
          System.out.println("Quantity wanted :" + part.getQuantity());
          
          //if(!partOldName.equals(part.getPartName())){
              //addOldNameStockBack();
             
            //}
          
        
          if(partOldUsedQuantity > part.getQuantity()) {
              increaseStockLevel(part.getPartName(), findStockLevel(part.getPartName()), partOldUsedQuantity);
          }
          else if(partOldUsedQuantity < part.getQuantity()){
              decreaseEditStockLevel(part.getPartName(),findStockLevel(part.getPartName()), partOldUsedQuantity);
          }
          
           Connection conn = null;
          try { // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully 1");
            String sql = "UPDATE vehiclePartsUsed SET partsId =?,name=?,cost=?,quantity=?,dateOfInstall=?,dateOfWarrantyExpire=?,vehicleID=?,customerID=?,bookingID=? WHERE partsUsedID=?";
            PreparedStatement state = conn.prepareStatement(sql); // Binding the parameters. 
            state.setInt(1, findPartsID(partOldName)); 
            state.setString(2,partOldName); 
            state.setDouble(3, findPartsCost(partOldName));
            state.setDouble(4, part.getQuantity()); 
            state.setString(5, part.getInstallDate());
            state.setString(6, findExpireDate(part.getInstallDate()));
            state.setInt(7, findVehicleID(part.getBookingID()));
            state.setInt(8, findCustomerID(part.getBookingID()));
            state.setInt(9, part.getBookingID()); // booking ID
            state.setInt(10, part.getUsedID());
            state.execute();
            state.close(); 
            conn.close(); 
            clearFields();
     
     } catch (SQLException e) {
         System.err.println(e.getClass().getName() + ":" + e.getMessage());
         System.out.println("Error on editing data.");
       } 
    }
   
   
     @FXML 
     public void editButton(ActionEvent event) throws IOException,ClassNotFoundException {
        try{ 
          if(isFieldsCompleted())  {
            alertError("Please complete all the fields.");
          }
  
          else {
            //part.setPartId(Integer.parseInt(idNumber.getText()));
            part.setPartName(partNameCombo.getValue());
            part.setQuantity(Integer.parseInt(quantity.getText()));
            part.setInstallDate(dateOfInstall.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            part.setBookingID(bookingIdCombo.getValue());
      
            editData(part); 
            buildPartsUsedData(); 
            alertInformation("The database has been updated.");
        }
        }
         catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println(" Error in editButton.");
        } 
 }
     
    

    public void clearFields() {
        partNameCombo.setValue(null);
        quantity.clear();
        ((TextField) dateOfInstall.getEditor()).clear();
        bookingIdCombo.setValue(null);
        

    }

    public void alertInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }

    public void alertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void alertConfirmation(String confirmation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation.");
        alert.setHeaderText(null);
        alert.setContentText(confirmation);
        alert.showAndWait();
    }

    private int findVehicleID(int bookingID) throws ClassNotFoundException {
        int vehRegID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select vehicleID from booking where booking_id='" + bookingID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegID = rs.getInt("vehicleID");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding vehicle id.");
        }
        return vehRegID;
    }

    private int findCustomerID(int bookingID) throws ClassNotFoundException {
        int customerID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
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

    private int findPartsID(String name) throws ClassNotFoundException {
        int partID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select parts_id from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                partID = rs.getInt("parts_id");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding PartsId.");
        }
        return partID;
    }

    private Double findPartsCost(String name) throws ClassNotFoundException {
        double cost = 0.0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select cost from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                cost = rs.getDouble("cost");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error findind cost.");
        }
        return cost;
    }

    public String findExpireDate(String installDate) {

        Date date = null;
        String warrantyexpire = "";

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {

            date = df.parse(installDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, 1); // to get previous year add -1
            Date warrantyExpire = cal.getTime();
            warrantyexpire = df.format(warrantyExpire);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return warrantyexpire;
    }

    private String findCustomerName(int customerID) throws ClassNotFoundException {
        String customerName = "";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String SQL = "Select customer_fullname from customer where customer_id='" + customerID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customerName = rs.getString("customer_fullname");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding Customer Name.");
        }
        return customerName;
    }

    private String findVehReg(int vehicleID) throws ClassNotFoundException {
        String vehRegNo = "";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String SQL = "Select RegNumber from vehicleList where vehicleID='" + vehicleID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegNo = rs.getString("RegNumber");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in finding veh Reg.");
        }
        return vehRegNo;
    }

    private void fillBookingIdCombo() throws ClassNotFoundException {

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            //conn = (new sqlite().connect());
            String SQL = "Select booking_id from Booking";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                bookingId.add(rs.getInt("booking_id"));
            }
            bookingIdCombo.setItems(bookingId);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error filling in the combo.");
        }
    }

 

    // Filling data to the tableView From the database.
    public void buildPartsUsedData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully 2");

            String SQL = "Select * from vehiclePartsUsed";
            // Execute query and store results in a resultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                //  a ResultSet object that retrieves the results of your query,
                // and executes a simple while loop, which retrieves and displays those results.
                // Calling the constructor
                part.setUsedID(rs.getInt(1));
                part.setPartName(rs.getString(3));
                part.setCost(rs.getDouble(4));
                part.setQuantity(rs.getInt(5));
                part.setInstallDate(rs.getString(6));
                part.setWarrantyExpireDate(rs.getString(7));
                part.setVehicleRegNo(findVehReg(rs.getInt(8)));
                part.setcustomerFullName(findCustomerName(rs.getInt(9)));
                part.setBookingID(rs.getInt(10));
                

                data.add(new partsUsed(part.getUsedID(), part.getPartName(), part.getCost(), part.getQuantity(), part.getInstallDate(), part.getWarrantyExpireDate(), part.getVehicleRegNo(), part.getCustomerFullName(), part.getBookingID()));
                FilteredList<partsUsed> filteredData = new FilteredList<>(data, e -> true);
                searchField.setOnKeyReleased(e -> {
                searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super partsUsed>) partsUsed -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String newLowerValue = newValue.toLowerCase();
                    if (partsUsed.getVehicleRegNo().toLowerCase().contains(newLowerValue)) {
                        return true; // Filter matches vehicle registraion number.
                    } else if (partsUsed.getCustomerFullName().toLowerCase().contains(newLowerValue)) {
                        return true; // filter matches customer full name.
                    }

                    return false;
                });
                SortedList<partsUsed> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
            });
        });
            
            
            }
            

            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data.");
        }

    }

    // add data to the database from the textfield.
    public void createData(partsUsed part) throws ClassNotFoundException {

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened database successfully 1.");
            System.out.println("Creating data.");
            //query you have to write to insert your data
            String sql = "insert into vehiclePartsUsed(partsId,name,cost,quantity, dateOfInstall, dateOfWarrantyExpire,vehicleID, customerID, bookingID) values(?,?,?,?,?,?,?,?,?)";
            //The query is sent to the database and prepared there which means the SQL statement is "analysed".
            PreparedStatement state = conn.prepareStatement(sql);

            if (isFieldsCompleted()) {

                alertError("Please complete all the fields.");

            } else {
                // the values are sent to the server
                // use the appropriate state.setXX() methods to set the appropriate values for the parameters that you've defined.
                // Then call state.executeUpdate() to execute the call to the database.
                state.setInt(1, findPartsID(part.getPartName()));
                state.setString(2, part.getPartName());
                state.setDouble(3, findPartsCost(part.getPartName()));
                state.setInt(4, part.getQuantity());
                state.setString(5, part.getInstallDate());
                state.setString(6, findExpireDate(part.getInstallDate()));// return a string date
                state.setInt(7, findVehicleID(part.getBookingID()));
                state.setInt(8, findCustomerID(part.getBookingID()));
                state.setInt(9, part.getBookingID()); // booking ID
                
                state.execute();

                state.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Creating Data");
        }

    }

    public LocalDate convertStringToDate(String dateString) {
        LocalDate date = null;

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("I am here");
        try {

            date = LocalDate.parse(dateString, df);
            System.out.println("I am here 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
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
            dateOfInstall.setDayCellFactory(dayCellFactory);

        ObservableList<String> namesCombo = FXCollections.observableArrayList("Spark Plugs", "Prop Shaft", "Handbrake Cable", "Bumper", "Rims", "HeadLights", "Tail Lights", "Radiator", "Fender", "Roof Rack");
        partNameCombo.setItems(namesCombo);
        try {
            fillBookingIdCombo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        try {
            buildPartsUsedData();

            // selectedItemProperty = gives you the item the user selected form the table.
            // add listner to your tableview selectedItemProperty
            // The listener is gonna sit on the item of the tableView and its gonna wait for some action to happen, Ex: User selecting an item from the tableview 
            //
            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override //This method will be called whenever user selected row

                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {   //Checks whether item is selected.
                        if (table.getSelectionModel().getSelectedItem() != null) {
                            Connection conn = null;
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                            System.out.println("Opened Database Successfully initial");
                            usedPartID = table.getSelectionModel().getSelectedItem().getUsedID();
                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            // Execute query and store results in a resultSet
                            ResultSet rs = state.executeQuery("SELECT * FROM vehiclePartsUsed WHERE partsUsedID = " + "'" + usedPartID + "'");
                            while (rs.next()) {
                                // get data from db and show it on the textFields.
                                usedPartID = rs.getInt("partsUsedID");
                                partNameCombo.setValue(rs.getString("name"));
                                quantity.setText(String.valueOf(rs.getInt("quantity")));
                                dateOfInstall.setValue(convertStringToDate(rs.getString("dateOfInstall")));
                                bookingIdCombo.setValue(rs.getInt("bookingID"));
                                

                            }
                            part.setUsedID(usedPartID);
                            part.setPartName(partNameCombo.getValue());
                            part.setQuantity(Integer.parseInt(quantity.getText()));
                            part.setCost(table.getSelectionModel().getSelectedItem().getCost());
                            part.setInstallDate((dateOfInstall.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                            part.setWarrantyExpireDate(table.getSelectionModel().getSelectedItem().getWarrantyExpireDate());
                            part.setcustomerFullName(table.getSelectionModel().getSelectedItem().getCustomerFullName());
                            part.setVehicleRegNo(table.getSelectionModel().getSelectedItem().getVehicleRegNo());
                            part.setBookingID(table.getSelectionModel().getSelectedItem().getBookingID());
                            
                            getCustomerDetails(part);
                            state.close();
                            conn.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error 1.");
            e.printStackTrace();

        }
    }

    public void getCustomerDetails(partsUsed part) {
        System.out.println(part.getCost());
        System.out.println(part.getQuantity());
        System.out.println(part.getCustomerFullName());
    }
    // TODO
}
