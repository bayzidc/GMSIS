/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.gui;

import Authentication.sqlite;
import VehicleRecord.logic.CustBookingInfo;
import VehicleRecord.logic.PartsInfo;
import VehicleRecord.logic.Vehicle;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VehicleController implements Initializable {

    //Declaring FXML buttons, images, tables, tablecolumns etc.
    @FXML
    public Button backBtn;
    @FXML
    public Button addEntryBtn;
    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public Button viewParts;
    @FXML
    public ImageView searchVecImg;
    @FXML 
    public ImageView refeshImg;
    @FXML
    public TextField searchVehicle;
    @FXML
    public JFXButton backButtn;
    @FXML
    public TableView<Vehicle> table;
    @FXML
    public TableColumn<Vehicle, String> regCol;
    @FXML
    public TableColumn<Vehicle, String> makeCol;
    @FXML
    public TableColumn<Vehicle, String> modelCol;
    @FXML
    public TableColumn<Vehicle, Double> engSizeCol;
    @FXML
    public TableColumn<Vehicle, String> fuelTypeCol;
    @FXML
    public TableColumn<Vehicle, String> colourCol;
    @FXML
    public TableColumn<Vehicle, String> motRenewalCol;
    @FXML
    public TableColumn<Vehicle, String> lastServiceCol;
    @FXML
    public TableColumn<Vehicle, Integer> mileageCol;
    @FXML
    public TableColumn<Vehicle, String> vehicleTCol;
    @FXML
    public TableColumn<Vehicle, String> warrantyCol;
    @FXML
    public TableColumn<Vehicle, String> nameAndAddCol;
    @FXML
    public TableColumn<Vehicle, String> warExpDateCol;
    @FXML
    public TableColumn<Vehicle, Integer> vecIDCol;
    @FXML
    public TableColumn<Vehicle, Integer> custIDCol;

    @FXML
    public TableView<CustBookingInfo> custTable;
    @FXML
    public TableColumn<CustBookingInfo, String> fullNameCol;
    @FXML
    public TableColumn<CustBookingInfo, String> bookingDateCol;
    @FXML 
    public TableColumn<CustBookingInfo, String> regNCol;
    @FXML
    public TableColumn<CustBookingInfo, Double> totalCostCol;

    @FXML
    public TableView<PartsInfo> partsTable;
    @FXML
    public TableColumn<PartsInfo, Integer> partIDCol;
    @FXML
    public TableColumn<PartsInfo, String> partsUsedCol;
    @FXML
    public TableColumn<PartsInfo, Integer> quantityCol;

    //Declaring observable lists to be manipulated later on.
    ObservableList<Vehicle> data;
    ObservableList<CustBookingInfo> custData;
    ObservableList<PartsInfo> partsData;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        regCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("RegNumber"));
        makeCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("Make"));
        modelCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("Model"));
        engSizeCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, Double>("EngSize"));
        fuelTypeCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("FuelType"));
        colourCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("Colour"));
        motRenewalCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("MotRenewal"));
        lastServiceCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("LastService"));
        mileageCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, Integer>("Mileage"));
        vehicleTCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("VehicleType"));
        warrantyCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("Warranty"));
        nameAndAddCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("WarNameAndAdd"));
        warExpDateCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("WarrantyExpDate"));
        vecIDCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, Integer>("VecID"));
        custIDCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, Integer>("CustID"));

        fullNameCol.setCellValueFactory(
                new PropertyValueFactory<CustBookingInfo, String>("FullName"));
        bookingDateCol.setCellValueFactory(
                new PropertyValueFactory<CustBookingInfo, String>("BookingDate"));
        regNCol.setCellValueFactory(
        new PropertyValueFactory<CustBookingInfo, String> ("RegNumber"));
        totalCostCol.setCellValueFactory(
        new PropertyValueFactory<CustBookingInfo, Double> ("TotalCost"));

        partIDCol.setCellValueFactory(
                new PropertyValueFactory<PartsInfo, Integer>("PartID"));
        partsUsedCol.setCellValueFactory(
                new PropertyValueFactory<PartsInfo, String>("PartsUsed"));
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<PartsInfo, Integer>("Quantity"));

        try 
        {   
            buildData();
            buildCustomerData();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    //Method which allows the user to press a button to refresh the data shown on the page
    @FXML
    public void refreshButton(MouseEvent event)
    {
        try
        {
            buildData();
            buildCustomerData();
        }
        
        catch(Exception e)
        {
            alertError("Some data is missing from parts of our system. Please try again later.");
        }
    }
    
    //Method which allows the user to go back to the admin page
    @FXML
    public void backButton(ActionEvent event) throws IOException // method which goes back to admin page
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

    //Method which directs the user to another fxml to add a vehicle
    @FXML
    public void addEntry(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        Parent vehicle_Page = loader.load(getClass().getResource("AddVehicle.fxml").openStream());
        AddVehicleController c = (AddVehicleController) loader.getController();
        c.updateBtn.setVisible(false);
        c.addEntry.setVisible(true);
        Scene vehicle_Scene = new Scene(vehicle_Page);
        Stage stageVehicle = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageVehicle.setScene(vehicle_Scene);
        stageVehicle.show();

    }
    
    //Method which allows the user to direct to another fxml to edit their vehicle once the appropriate row has been selected
    @FXML
    public void showButton(ActionEvent e) throws IOException, ClassNotFoundException // method to show vehicle details on textfields
    {
        try
        {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("AddVehicle.fxml").openStream());
            AddVehicleController c = (AddVehicleController) loader.getController();
            Stage stage2 = (Stage) ((Node) e.getSource()).getScene().getWindow();
            c.addEntry.setVisible(false);
            c.updateBtn.setVisible(true);
            String regN = table.getSelectionModel().getSelectedItem().getRegNumber();
            String vecMake = table.getSelectionModel().getSelectedItem().getMake();
            String vecModel = table.getSelectionModel().getSelectedItem().getModel();
            double engine = table.getSelectionModel().getSelectedItem().getEngSize();
            String ft = table.getSelectionModel().getSelectedItem().getFuelType();
            String col = table.getSelectionModel().getSelectedItem().getColour();
            String mot = table.getSelectionModel().getSelectedItem().getMotRenewal();
            String ls = table.getSelectionModel().getSelectedItem().getLastService();
            int mil = table.getSelectionModel().getSelectedItem().getMileage();
            String vecType = table.getSelectionModel().getSelectedItem().getVehicleType();
            String war = table.getSelectionModel().getSelectedItem().getWarranty();
            String wNameAndAdd = table.getSelectionModel().getSelectedItem().getWarNameAndAdd();
            String warDate = table.getSelectionModel().getSelectedItem().getWarrantyExpDate();
            int ID = table.getSelectionModel().getSelectedItem().getVecID();
            int cust = table.getSelectionModel().getSelectedItem().getCustID();
            if(regN.isEmpty())
            {
                alertInf("One or more rows have a missing value in the row");
            }
            c.regNumber.setText(regN);
            c.make.setText(vecMake);
            c.model.setText(vecModel);
            c.engSize.setText(String.valueOf(engine));
            c.fuelType.setValue(ft);
            c.colour.setText(col);
            c.mileage.setText(String.valueOf(mil));
            c.motRenDate.getEditor().setText(mot);
            c.lastService.getEditor().setText(ls);
            c.vehicleChoice.setValue(vecType);
            c.nameAndAdd.setText(wNameAndAdd);  
            c.warExpiry.getEditor().setText(warDate);
            c.id.setText(String.valueOf(ID));
            c.custID.setText(String.valueOf(cust));
            stage2.hide();
            Scene edit_Scene = new Scene(root);
            primaryStage.setScene(edit_Scene);
            primaryStage.show();
        }
        
        catch(Exception ex)
        {
            alertInf("Please select a row to edit a vehicle.");
        }
    }
    
    //Method which allows the user to delete a vehicle once the appropriate row has been selected
    @FXML
    public void deleteVehicle(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to delete vehicle
    {
        try
        {
            String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this vehicle? (Yes or No) ");
            int id = table.getSelectionModel().getSelectedItem().getVecID();
            if (confirmDelete.equalsIgnoreCase("Yes") && isVehicleDeleted() && deletePartofVec() && deleteBookingDate())
            {
                alertInf("VehicleID: " + id + " has been deleted.");
                buildData();
                buildCustomerData();
                buildParts();
            }

        }
        catch(Exception e)
        {
            alertInf("Please select a row to delete a vehicle.");
        }
    }
    
    //Method which allows the user to view the parts used once the appropriate row has been selected
    @FXML
    public void viewPartsData(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
    {
        try
        {
            int id = table.getSelectionModel().getSelectedItem().getVecID();
            if(!checkIfPartExists())
            {
                alertInf("There are no parts used for this vehicle");
            }
            else
            {
                buildParts();
            }
            
        }
        
        catch(Exception e)
        {
            alertInf( "Please select a row to view the part used for that vehicle.");
        }
    }

    public void buildParts()
    {
        partsData = FXCollections.observableArrayList();
        Connection conn = null;
        try
        {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            String SQL = "Select vehiclePartsUsed.partsId, name, quantity from vehiclePartsUsed, vehicleList where vehicleList.vehicleID= vehiclePartsUsed.vehicleID";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                PartsInfo parts = new PartsInfo();
                parts.partID.set(rs.getInt("partsId"));
                parts.partsUsed.set(rs.getString("name"));
                parts.quantity.set(rs.getInt("quantity"));
                partsData.add(parts);
            }
            partsTable.setItems(partsData);
            conn.close();
            rs.close();
        }
        catch(Exception e)
        {
            alertError("Error on building parts data. Please try again later.");
        }
    }
    
    //Method which checks in the database if any parts exist for a vehicle
    public boolean checkIfPartExists() throws ClassNotFoundException, SQLException
    {
         int id = table.getSelectionModel().getSelectedItem().getVecID();
         int count = 0;
         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rset = null;
         try 
         {
             conn = (new sqlite().connect());
             stmt = conn.prepareStatement("SELECT Count(vehiclePartsUsed.partsId) from vehiclePartsUsed,vehicleList WHERE vehicleList.vehicleID=?");
             stmt.setString(1, String.valueOf(id));
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
    
    //Method which loads the customers name, registration number and scheduled booking date from the database
    public void buildCustomerData() throws ClassNotFoundException, SQLException 
    {
        custData = FXCollections.observableArrayList();
        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String SQL = "Select customer_fullname, scheduled_date, RegNumber, totalCost from customer, booking, vehicleList,bill where customer.customer_id = booking.customer_id AND vehicleList.customerid = customer.customer_id AND customer.customer_id = bill.customerID";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
  
            while (rs.next()) 
            {
                CustBookingInfo cust = new CustBookingInfo("","","",0.0);
                cust.fullName.set(rs.getString("customer_fullname"));
                cust.bookingDate.set(rs.getString("scheduled_date"));
                cust.regNumber.set(rs.getString("RegNumber"));
                cust.totalCost.set(rs.getDouble("totalCost"));
                custData.add(cust);

                if(rs.getString("customer_fullname").equals(""))
                {
                   alertInf("There are no customers in our system at the moment. Please go to the customer services section");
                } 
                
                if(rs.getString("scheduled_date").equals(""))
                {
                    alertInf("There are no bookings available for some customers. Please go to the repair booking section.");
                }

            }           
            custTable.setItems(custData);
            rs.close();
            conn.close();
        }
        catch (Exception e) 
        {
            alertError("Error on building customer data. Please try again later.");
        }
    }
    
    //Method which loads up all the vehicle details for a customer from the database
    public void buildData() throws ClassNotFoundException, SQLException 
    {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try 
        {

            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            String SQL = "select RegNumber, Make, Model, Engsize, FuelType, Colour, MOTDate, LastServiceDate, Mileage, VehicleType, Warranty, WarrantyNameAndAdd, WarrantyExpDate, vehicleID, customer_id from vehicleList, customer where vehicleList.customerid = customer.customer_id";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {
                Vehicle vec = new Vehicle("","","",0.0,"","","","",0,"","","","",0,0);
                vec.regNumber.set(rs.getString("RegNumber"));
                vec.make.set(rs.getString("Make"));
                vec.model.set(rs.getString("Model"));
                vec.engSize.set(rs.getDouble("EngSize"));
                vec.fuelType.set(rs.getString("FuelType"));
                vec.colour.set(rs.getString("Colour"));
                vec.motRenewal.set(rs.getString("MOTDate"));
                vec.lastService.set(rs.getString("LastServiceDate"));
                vec.mileage.set(rs.getInt("Mileage"));
                vec.vehicleType.set(rs.getString("VehicleType"));
                vec.warranty.set(rs.getString("Warranty"));
                vec.warNameAndAdd.set(rs.getString("WarrantyNameAndAdd"));
                vec.warrantyExpDate.set(rs.getString("WarrantyExpDate"));
                vec.vecID.set(rs.getInt("vehicleID"));
                vec.custID.set(rs.getInt("customer_id"));
                data.add(vec);

                FilteredList<Vehicle> filteredData = new FilteredList<>(data, e -> true);
                searchVehicle.setOnKeyReleased(e -> {
                    searchVehicle.textProperty().addListener((observableValue, oldValue, newValue) -> {
                        filteredData.setPredicate((Predicate<? super Vehicle>) vehicle -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String newValLow = newValue.toLowerCase();
                            if (vehicle.getRegNumber().toLowerCase().contains(newValLow)) {
                                return true;
                            } else if (vehicle.getMake().toLowerCase().contains(newValLow)) {
                                return true;
                            }
                            
                            else if (vehicle.getVehicleType().toLowerCase().contains(newValLow)) {
                                return true;
                            }

                            return false;
                        });
                    });
                    SortedList<Vehicle> sortedData = new SortedList<>(filteredData);
                    sortedData.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sortedData);
                });
            }
            table.setItems(data);
            rs.close();
            conn.close();

        } 
        catch (Exception e)
        {
            e.printStackTrace();
            alertError("Error on building vehicle data. Please try again later.");
        }

    }

    //Method which deletes the vehicle from the database where the appropriate vehicle ID is specified
    public boolean isVehicleDeleted() throws ClassNotFoundException 
    {
        boolean vecDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getVecID();

        Connection conn = null;

        try 
        {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM vehicleList WHERE vehicleID= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, String.valueOf(ID));
            state.executeUpdate();

            state.close();
            conn.close();

            vecDeleted = true;

        } 
        
        catch (SQLException e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return vecDeleted;

    }

    //Method which deletes the parts used on the vehicle from the database once the vehicle is deleted
    public boolean deletePartofVec() throws ClassNotFoundException
    {
        boolean partDeleted = false;
        int ID = table.getSelectionModel().getSelectedItem().getVecID();
        Connection conn = null;

        try 
        {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            String sql ="delete from vehiclePartsUsed where vehiclePartsUsed.vehicleID";
            PreparedStatement state = conn.prepareStatement(sql);
            state.executeUpdate();

            state.close();
            conn.close();
            partDeleted = true;

    
        } 
        catch (SQLException e) 
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return partDeleted;
    }
    
    //Method which returns the part ID from the database
    private int getPartID() throws ClassNotFoundException
    {
        int partID = 0;
        Connection conn = null;
 
        java.sql.Statement state = null;
        try
        {
            conn = (new sqlite().connect());
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT partsId FROM vehiclePartsUsed, vehiclePartsStock WHERE vehiclePartsStock.parts_id = vehiclePartsUsed.partsId");
            while(rs.next())
            {
                 partID= rs.getInt("partsId");
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
        return partID;
    }
    
    //Method which returns the part ID used on the vehicle
    private int getVehiclePartID() throws ClassNotFoundException
    {
        int vecPartID = 0;
        Connection conn = null;
 
        java.sql.Statement state = null;
        try
        {
            conn = (new sqlite().connect());
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT partsid FROM vehicleList, vehiclePartsStock WHERE vehiclePartsStock.parts_id = vehicleList.partsid");
            while(rs.next())
            {
                 vecPartID= rs.getInt("partsid");
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
        return vecPartID;
    }
    
    //Method which returns the booking ID from the database
    private int getBookingID() throws ClassNotFoundException
    {
        int bookID = 0;
        Connection conn = null;
 
        java.sql.Statement state = null;
        try
        {
            conn = (new sqlite().connect());
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT bookingid FROM vehicleList, booking WHERE vehicleList.bookingid = booking.booking_id");
            while(rs.next())
            {
                 bookID= rs.getInt("bookingid");
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
        return bookID;
    }
    
    //Method which removes the booking associated with a vehicle in the database
    public boolean deleteBookingDate() throws ClassNotFoundException
    {
        int id = table.getSelectionModel().getSelectedItem().getVecID();
        boolean bookingDeleted = false;
        Connection conn = null;
        
        try
        {
            conn = (new sqlite().connect());
            String sql = "delete from booking where booking.vehicleID=? ";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, String.valueOf(id));
            state.executeUpdate();
            
            state.close();
            conn.close();
            bookingDeleted = true;
            
        }
        
        catch(Exception e)
        {
            alertError("Error on deleting booking data. Please try again later.");
        }
        return bookingDeleted;
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
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   


}
