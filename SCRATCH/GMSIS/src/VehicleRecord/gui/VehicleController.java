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
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    public Button viewInfo;
    @FXML
    public Button addBooking;
    @FXML
    public TextField searchVehicle;
    @FXML
    public ChoiceBox searchBy;
    
    @FXML
    public JFXCheckBox pastB;
    @FXML
    public JFXCheckBox futureB;
    @FXML
    public JFXCheckBox showAll;
    
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
    public TableColumn<Vehicle, String> custNameCol;

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


    //Declaring observable lists to be manipulated later on.
    ObservableList<Vehicle> data;
    ObservableList<CustBookingInfo> custData;
    ObservableList<PartsInfo> partsData;
    private ObservableList<CustBookingInfo> tempData = FXCollections.observableArrayList();
    ObservableList<String> search = FXCollections.observableArrayList("Make","Full/Partial RegNumber","Vehicle Type");

    public static Vehicle passVec = VehicleRecord.gui.AddVehicleController.vec;
    public static Vehicle vec = new Vehicle("", "", "", 0.0, "", "", "", "", 0, "", "", "", "",0,0, "");
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
        custNameCol.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("CustName"));

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

        
        searchBy.setValue("Make");
        searchBy.setItems(search);
        showAll.setSelected(true);
        if(!Authentication.LoginController.isAdmin)
        {
            users.setDisable(true);
        }
        
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
    public void addBooking(ActionEvent event) throws IOException
    {
        if(table.getSelectionModel().getSelectedItem()==null)
        {
            alertError("Select a row first");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));
        Parent root = (AnchorPane)loader.load();
        DiagnosisAndRepair.gui.DiagnosisAndRepairController obj = (DiagnosisAndRepair.gui.DiagnosisAndRepairController) loader.getController();       
        //obj.initiateBooking(table.getSelectionModel().getSelectedItem().getCustName(), table.getSelectionModel().getSelectedItem().getCustID(), table.getSelectionModel().getSelectedItem().getRegNumber(), table.getSelectionModel().getSelectedItem().getMileage());
        pane.getChildren().setAll(root);
        
        
    }
    @FXML
    public void addEntry(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        Parent vehicle_Page = loader.load(getClass().getResource("AddVehicle.fxml").openStream());
        AddVehicleController c = (AddVehicleController) loader.getController();
        c.updateBtn.setVisible(false);
        c.addEntry.setVisible(true);
        c.customerNames.setDisable(false);
        c.id.setVisible(false);
        c.vID.setVisible(false);
        Scene vehicle_Scene = new Scene(vehicle_Page);
        //vehicle_Scene.getStylesheets().add(getClass().getResource("vehicle.css").toExternalForm());
        Stage stageVehicle = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageVehicle.setScene(vehicle_Scene);
        stageVehicle.setHeight(810);
        stageVehicle.setWidth(1359);
        stageVehicle.show();
        

    }
    
    //Method which allows the user to direct to another fxml to edit their vehicle once the appropriate row has been selected
    @FXML
    public void showButton(ActionEvent e) throws IOException, ClassNotFoundException // method to show vehicle details on textfields
    {
        Vehicle v ;
        try
        {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("AddVehicle.fxml").openStream());
            AddVehicleController c = (AddVehicleController) loader.getController();
            Stage stage2 = (Stage) ((Node) e.getSource()).getScene().getWindow();
            c.addEntry.setVisible(false);
            c.updateBtn.setVisible(true);
            c.id.setVisible(true);
            c.vID.setVisible(true);
            c.regNumber.setEditable(false);

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
            if(!warDate.isEmpty())
            {
                c.warExpiry.setValue(vec.convert(warDate));
            }
            int ID = table.getSelectionModel().getSelectedItem().getVecID();
            int cust = table.getSelectionModel().getSelectedItem().getCustID();
            String cName = table.getSelectionModel().getSelectedItem().getCustName();
            c.motRenDate.setValue(vec.convert(mot));
            c.lastService.setValue(vec.convert(ls));
            
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
            c.id.setText(String.valueOf(ID));
            
            if(war.equals("Yes"))
            {
                c.yesWarranty.setSelected(true);
                c.noWarranty.setSelected(false);
                c.nameAndAdd.setText(wNameAndAdd);  
                c.warExpiry.getEditor().setText(warDate);
            }
            
            if(war.equals("No"))
            {
                c.noWarranty.setSelected(true);
                c.yesWarranty.setSelected(false);
                c.warExpiry.setVisible(false);
                c.nameAndAdd.setVisible(false);
                c.warrantyName.setVisible(false);
                c.warrantyDate.setVisible(false);
            }
            c.custID.setText(String.valueOf(cust));
            c.customerNames.setValue(cName);
            c.customerNames.setDisable(true);
            c.fillQuickSelection();
            stage2.hide();
            Scene edit_Scene = new Scene(root);
            //edit_Scene.getStylesheets().add(getClass().getResource("vehicle.css").toExternalForm());
            primaryStage.setScene(edit_Scene);
            primaryStage.setHeight(810);
            primaryStage.setWidth(1359);
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
            int id = table.getSelectionModel().getSelectedItem().getVecID();
            Optional<ButtonType> confirmDelete = alertConfirm("Are you sure you want to delete this vehicle?");
            if (confirmDelete.get() == ButtonType.OK && isVehicleDeleted(passVec))
            {
                alertInf("VehicleID: " + id + " has been deleted.");
                buildData();
                buildCustomerData();
            }

        }
        catch(Exception e)
        {
            alertInf("Please select a row to delete a vehicle.");
        }
    }
    
    //Method which allows the user to view the parts used once the appropriate row has been selected
    @FXML
    public void buildParts(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        try{
        int id = table.getSelectionModel().getSelectedItem().getVecID();
        partsData = FXCollections.observableArrayList();
        if(!checkIfPartExists())
            {
                alertInf("There are no parts used for this vehicle");
                partsTable.setItems(null);
            }
        else{
        Connection conn = null;
        try
        {
            conn = (new sqlite().connect());
            String SQL = "Select vehiclePartsUsed.partsUsedID, nameOfPart, quantity from vehiclePartsUsed, vehiclePartsStock,vehicleList where vehicleList.vehicleID= '"+id+"' AND vehiclePartsStock.parts_id = vehiclePartsUsed.parts_id AND vehicleList.vehicleID = vehiclePartsUsed.vehicleID";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                PartsInfo parts = new PartsInfo();
                parts.partID.set(rs.getInt("partsUsedID"));
                parts.partsUsed.set(rs.getString("nameOfPart"));
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
        }
        catch(Exception e)
        {
            alertInf( "Please select a row to view the part(s) used for that vehicle.");
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
             stmt = conn.prepareStatement("SELECT Count(vehiclePartsUsed.partsUsedID) from vehiclePartsUsed,vehicleList WHERE vehicleList.vehicleID=? AND vehicleList.vehicleID = vehiclePartsUsed.vehicleID");
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
    
    public void buildCustomerDataBtn(ActionEvent event)
    {
        futureB.setSelected(false);
        pastB.setSelected(false);
        showAll.setSelected(false);
        try{
        int id = table.getSelectionModel().getSelectedItem().getVecID();
        tempData.clear();
        custData = FXCollections.observableArrayList();
        

        try {
            Connection conn = null;
            conn = (new sqlite().connect());
            String SQL = "Select customer_fullname, scheduled_date, RegNumber, totalCost,startTime from customer, booking, vehicleList,bill where vehicleList.vehicleID= '"+id+"' AND customer.customer_id = booking.customer_id AND booking.vehicleID = vehicleList.vehicleID AND customer.customer_id = bill.customerID AND booking.booking_id = bill.bookingID";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
  
            while (rs.next()) 
            {
                CustBookingInfo cust = new CustBookingInfo("","","",0.0,"");
                cust.fullName.set(rs.getString("customer_fullname"));
                cust.bookingDate.set(rs.getString("scheduled_date"));
                cust.regNumber.set(rs.getString("RegNumber"));
                cust.totalCost.set(rs.getDouble("totalCost"));
                cust.time.set(rs.getString("startTime"));
                custData.add(cust);

            }        
            tempData.addAll(custData);
            custTable.setItems(custData);
            rs.close();
            conn.close();
        }
        
        catch(Exception e)
        {
            
            alertError("Error on building customer data. Please try again later.");
        }
        }
        catch(Exception ev)
        {
            alertInf( "Please select a row to view the customer/booking information for that vehicle.");
        }
        }
    
    public void buildCustomerData() throws ClassNotFoundException, SQLException 
    {
        custData = FXCollections.observableArrayList();
        tempData.clear();
        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String SQL = "Select customer_fullname, scheduled_date, RegNumber, totalCost from customer, booking, vehicleList,bill where customer.customer_id = booking.customer_id AND booking.vehicleID = vehicleList.vehicleID AND customer.customer_id = bill.customerID AND booking.booking_id = bill.bookingID";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
  
            while (rs.next()) 
            {
                CustBookingInfo cust = new CustBookingInfo("","","",0.0,"");
                cust.fullName.set(rs.getString("customer_fullname"));
                cust.bookingDate.set(rs.getString("scheduled_date"));
                cust.regNumber.set(rs.getString("RegNumber"));
                cust.totalCost.set(rs.getDouble("totalCost"));
                custData.add(cust);

            }        
            tempData.addAll(custData);
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
            String SQL = "select RegNumber, Make, Model, Engsize, FuelType, Colour, MOTDate, LastServiceDate, Mileage, VehicleType, Warranty, WarrantyNameAndAdd, WarrantyExpDate, vehicleID, customer_id, customer_fullname from vehicleList, customer where vehicleList.customerid = customer.customer_id";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {
                Vehicle vec = new Vehicle("","","",0.0,"","","","",0,"","","","",0,0,"");
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
                vec.custName.set(rs.getString("customer_fullname"));
                data.add(vec);

                FilteredList<Vehicle> filteredData = new FilteredList<>(data, e -> true);
                searchVehicle.setOnKeyReleased(e -> {
                    searchVehicle.textProperty().addListener((observableValue, oldValue, newValue) -> {
                        filteredData.setPredicate((Predicate<? super Vehicle>) vehicle -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String newValLow = newValue.toLowerCase();
                            if(searchBy.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Make") && vehicle.getMake().toLowerCase().contains(newValLow) )
                            {
                                   return true;
                            }
                            
                            else if(searchBy.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Full/Partial RegNumber") && vehicle.getRegNumber().toLowerCase().contains(newValLow))
                            {
                                    return true;
                            }
                            
                            else if(searchBy.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Vehicle Type") && vehicle.getVehicleType().toLowerCase().contains(newValLow))
                            {
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
    public boolean isVehicleDeleted(Vehicle passVec) throws ClassNotFoundException 
    {
        boolean vecDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getVecID();

        Connection conn = null;

        try 
        {
            conn = (new sqlite().connect());
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

    @FXML
    private void filterByPast() throws ClassNotFoundException
    {
       if(!pastB.isSelected())
        {
            pastB.setSelected(true);
            return;
        }
        
         futureB.setSelected(false);
         showAll.setSelected(false);
        
        custData = FXCollections.observableArrayList(tempData);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for(int i=0; i<custData.size(); i++)
        {
            LocalDateTime tempDate = LocalDateTime.parse(custData.get(i).getBookingDate() + " " + custData.get(i).getTime(),formatter);
            if(now.isBefore(tempDate)) //past dates
            {
                custData.remove(i);
                i--;
            }
        }
        if(custData.isEmpty())
        {
        alertInf("There are no past bookings for the vehicles.");
        }
        custTable.setItems(custData);
    }
    
    @FXML
    private void filterByFuture() throws ClassNotFoundException
    {
        if(!futureB.isSelected())
        {
            futureB.setSelected(true);
            return;
        }
   
        showAll.setSelected(false);
        pastB.setSelected(false);
        
        custData = FXCollections.observableArrayList(tempData);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
  
        for(int i=0; i<custData.size(); i++)
        {
            LocalDateTime tempDate = LocalDateTime.parse(custData.get(i).getBookingDate()  + " " + custData.get(i).getTime(),formatter);
  
            if(now.isAfter(tempDate)) //past dates
            {
                custData.remove(i);
                i--;
            }
        }
        
        if(custData.isEmpty())
        {
            alertInf("There are no future bookings. Please create a booking for your vehicle in the Diagnosis and Repair Section.");
        }
        custTable.setItems(custData);    
    }
    
    @FXML
    private void showAllBooking() throws ClassNotFoundException, SQLException
    {
        if(!showAll.isSelected())
        {
            showAll.setSelected(true);
            return;
        }
        
        futureB.setSelected(false);
        pastB.setSelected(false);

        
        buildCustomerData();
    }
    
    /*public LocalDate convert(String string)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(string, formatter);
        return localDate;
    }*/
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
   
    private Optional<ButtonType> alertConfirm(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
	alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

}
