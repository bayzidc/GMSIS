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

    @FXML
    public Button backBtn;
    @FXML
    public Button addEntryBtn;
    @FXML
    public Button editBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public TextField searchVehicle;

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
    public TableView<PartsInfo> partsTable;
    @FXML
    public TableColumn<PartsInfo, Integer> partIDCol;
    @FXML
    public TableColumn<PartsInfo, String> partsUsedCol;

    ObservableList<Vehicle> data;
    ObservableList<CustBookingInfo> custData;
    ObservableList<PartsInfo> partsData;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void addEntry(ActionEvent event) throws IOException {
        Parent vehicle_Page = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/AddVehicle.fxml"));
        Scene vehicle_Scene = new Scene(vehicle_Page);
        Stage stageVehicle = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageVehicle.setScene(vehicle_Scene);
        stageVehicle.show();

    }

    @FXML
    public void backButton(ActionEvent event) throws IOException // method which goes back to admin page
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();

    }

    @FXML
    public void showButton(ActionEvent e) throws IOException, ClassNotFoundException // method to show vehicle details on textfield
    {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("AddVehicle.fxml").openStream());
        AddVehicleController c = (AddVehicleController) loader.getController();
        Stage stage2 = (Stage) ((Node) e.getSource()).getScene().getWindow();
        
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
        //String cust = custTable.getSelectionModel().getSelectedItem().getFullName();
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
        //c.customerNames.setValue(cust);
        stage2.hide();
        Scene edit_Scene = new Scene(root);
        primaryStage.setScene(edit_Scene);
        primaryStage.show();
    }

    @FXML
    public void deleteVehicle(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to delete vehicle
    {
        String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this vehicle? (Yes or No) ");
        int id = table.getSelectionModel().getSelectedItem().getVecID();
        if (confirmDelete.equalsIgnoreCase("Yes") && isVehicleDeleted()) {

            JOptionPane.showMessageDialog(null, "VehicleID: " + id + " has been deleted.");
            buildData();
        } else if (id == 0) {
            JOptionPane.showMessageDialog(null, "Vehicle does not exist.");
        } else {
            JOptionPane.showMessageDialog(null, "Error deleting vehicle.");
        }

    }

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

        partIDCol.setCellValueFactory(
                new PropertyValueFactory<PartsInfo, Integer>("PartID"));
        partsUsedCol.setCellValueFactory(
                new PropertyValueFactory<PartsInfo, String>("PartsUsed"));

        try {
            buildPartsData();
            buildCustomerData();
            buildData();
            /*quickSel.setOnAction(e ->{
            
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try
                {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                    System.out.println("Opened Database Successfully");
                    String query = "select * from vehicleList where Make = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1,(String) quickSel.getSelectionModel().getSelectedItem());
                    rs = ps.executeQuery();
                    
                    while(rs.next())
                    {
                        regNumber.setText(rs.getString("RegNumber"));
                        make.setText(rs.getString("Make"));
                        model.setText(rs.getString("Model"));
                        engSize.setText(rs.getString("EngSize"));
                        fuelType.setValue(rs.getString("FuelType"));
                        colour.setText(rs.getString("Colour"));
                        ((TextField) motRenDate.getEditor()).setText(rs.getString("MOTDate"));
                        ((TextField) lastService.getEditor()).setText(rs.getString("LastServiceDate"));
                        mileage.setText(rs.getString("Mileage"));   
                        vehicleChoice.setValue(rs.getString("VehicleType"));
                        nameAndAdd.setText(rs.getString("WarrantyNameAndAdd"));
                        ((TextField) warExpiry.getEditor()).setText(rs.getString("WarrantyExpDate"));
                        id.setText(rs.getString("vehicleID"));
                    }
                    
                    conn.close();
                    ps.close();
                    rs.close();
                }
                
                catch(Exception ex)
                {
                    
                }
            
        });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildPartsData() throws ClassNotFoundException, SQLException
    {
        partsData = FXCollections.observableArrayList();
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");
            String SQL = "Select parts_id, nameOfPart from vehiclePartsStock, vehiclePartsUsed INNER JOIN vehicleList ON vehiclePartsUsed.parts_id = vehicleList.vehicleID";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                PartsInfo parts = new PartsInfo();
                parts.partID.set(rs.getInt("parts_id"));
                parts.partsUsed.set(rs.getString("nameOfPart"));
                partsData.add(parts);
            }
            partsTable.setItems(partsData);
            conn.close();
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Error on building parts data");
        }
    }
    public void buildCustomerData() throws ClassNotFoundException, SQLException {
        custData = FXCollections.observableArrayList();
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");
            String SQL = "Select customer_fullname, scheduled_date from customer, booking where customer.customer_ID = booking.booking_id";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            System.out.println("Success");
            while (rs.next()) {
                CustBookingInfo cust = new CustBookingInfo();
                cust.fullName.set(rs.getString("customer_fullname"));
                cust.bookingDate.set(rs.getString("scheduled_date"));
                custData.add(cust);
            }
            custTable.setItems(custData);
            rs.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public void buildData() throws ClassNotFoundException, SQLException {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");
            String SQL = "select RegNumber, Make, Model, Engsize, FuelType, Colour, MOTDate, LastServiceDate, Mileage, VehicleType, Warranty, WarrantyNameAndAdd, WarrantyExpDate, vehicleID, customer_id from vehicleList, customer where vehicleList.vehicleID = customer.customer_id";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                Vehicle vec = new Vehicle();
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

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        //fillQuickSelection();

    }

    public void showVecOnText() {
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

    }

    public boolean isVehicleDeleted() throws ClassNotFoundException {
        boolean vecDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getVecID();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM vehicleList WHERE vehicleID= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, String.valueOf(ID));
            state.executeUpdate();

            state.close();
            conn.close();

            vecDeleted = true;

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return vecDeleted;

    }

    /* private int getVehicleID() throws ClassNotFoundException
    {
         Connection conn = null;
        
         int vecid=0;
         
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
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
        
    }*/
}
