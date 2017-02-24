/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

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
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;


/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartsController implements Initializable {
    
    @FXML
    private TextField idNumber;
    @FXML
    private TextField cost;
    @FXML
    private DatePicker dateOfInstall;
    @FXML
    private DatePicker dateOfWarrantyExpire ;
    @FXML
    private TextField vehicleRegNumber;
    @FXML
    private TextField customerFullName;
    @FXML
    private TextField searchParts;
    @FXML
    private Button addParts;
    @FXML
    private Button editParts ;
    @FXML
    private Button deleteParts;
    @FXML
    private Button installButton;
    @FXML
    private Button clear;
    @FXML
    private TableView<partsUsed> table;
    @FXML
    private TableColumn<partsUsed, Integer> idCol;
    @FXML
    private TableColumn<partsUsed, Double> costCol; 
    @FXML
    private TableColumn<partsUsed, String> installDateCol;
    @FXML
    private TableColumn<partsUsed, String> expireDateCol;
    @FXML
    private TableColumn<partsUsed, String> registrationNoCol;
    @FXML
    private TableColumn<partsUsed, String> customerNameCol;
    @FXML // Observable list to hold partsUsed object.
    ObservableList<partsUsed> data;
    public static partsUsed part = new partsUsed(0, 0.0, "", "", "", "");
    
    
    
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
    
    public boolean isFieldsCompleted(){   
        if (idNumber.getText().equals("") || cost.getText().equals("") || dateOfInstall.getValue().equals("") || dateOfWarrantyExpire.getValue().equals("")||vehicleRegNumber.getText().equals("") || customerFullName.getText().equals("")) {
        return false;
        }
        return true;
    }
    
    public void withdrawButton() throws IOException, ClassNotFoundException {
        
    }
    
    
    
    @FXML
    /**public void editData(PartsRecord.logic.parts part) throws ClassNotFoundException {
        Connection conn = null;

        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully 1");

            String sql = "UPDATE vehiclePartsStock SET nameofPart=?,description=?,stockLevelsOfParts=?,cost=? WHERE parts_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setString(1, part.getPartName());
            state.setString(2, part.getPartDescription());
            state.setInt(3, part.getPartStockLevel());
            state.setDouble(4, part.getCost());
            //state.setString(5, part.getArrivedDate());
            state.setInt(5, part.getPartIDentify());

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
    public void editButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            if (name.getText().equals("") || description.getText().equals("") || stockLevels.getText().equals("") || cost.getText().equals("")) {

                alertError("Please complete all the fields.");

            } else {
                //showPart.setPartId(Integer.parseInt(idNumber.getText()));
                showPart.setPartName(name.getText());
                showPart.setPartDescription(description.getText());
                showPart.setPartStockLevel(Integer.parseInt(stockLevels.getText()));
                showPart.setCost(Double.parseDouble(cost.getText()));
                //showPart.setArrivedDate(arrivedStockDate.getText());
                editData(showPart);
                buildPartsStockData();
                alertInformation("The database has been updated.");

            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }**/
    
    
    private void searchParts() {
        FilteredList<partsUsed> filteredData = new FilteredList<>(data, e -> true);
        searchParts.setOnKeyReleased(e -> {
            searchParts.textProperty().addListener((observableValue, oldValue, newValue) -> {
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
    
    
    public void clearFields() {
        idNumber.clear();
        cost.clear();
        //dateOfInstall.clear();
        //dateOfWarrantyExpire.clear();
        vehicleRegNumber.clear();
        customerFullName.clear();
        
        
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
    
    private int findVehicleID(String vehicleRegNo) throws ClassNotFoundException
    {
        int vehRegID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select vehicleID from vehicleList where RegNumber='"+ vehicleRegNo +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
                vehRegID = rs.getInt("vehicleID");    
            }
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehRegID;
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
                part.setPartId(rs.getInt(1));
                part.setCost(rs.getDouble(2));
                part.setInstallDate(rs.getString(3));
                part.setWarrantyExpireDate(rs.getString(4));
                part.setVehicleRegNo(rs.getString(5));
                part.setcustomerFullName(rs.getString(6));
                
                data.add(new partsUsed(part.getPartID(), part.getCost(), part.getInstallDate(), part.getWarrantyExpireDate(), part.getVehicleRegNo(), part.getCustomerFullName()));
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
            String sql = "insert into vehiclePartsUsed(partsId, cost, dateOfInstall, dateOfWarrantyExpire, vehicleRegNo, customerFullName, vehicleID) values(?,?,?,?,?,?,?)";
            //The query is sent to the database and prepared there which means the SQL statement is "analysed".
            PreparedStatement state = conn.prepareStatement(sql);
            
            if (!isFieldsCompleted()) {

                alertError("Please complete all the fields.");

            } else {
                // the values are sent to the server
                // use the appropriate state.setXX() methods to set the appropriate values for the parameters that you've defined.
                // Then call state.executeUpdate() to execute the call to the database.
                state.setInt(1, part.getPartID());
                state.setDouble(2, part.getCost());
                state.setString(3, part.getInstallDate());
                state.setString(4, part.getWarrantyExpireDate());
                state.setString(5, part.getVehicleRegNo());
                state.setString(6, part.getCustomerFullName());
                state.setInt(7, findVehicleID(part.getVehicleRegNo()));

                state.execute();

                state.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Creating Data");
        }
            

    }
    
    
    
    
    
    
    
  
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("partId"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
        installDateCol.setCellValueFactory(
                new PropertyValueFactory<>("installDate"));
        expireDateCol.setCellValueFactory(
                new PropertyValueFactory<>("warrantyExpireDate"));
        registrationNoCol.setCellValueFactory(
                new PropertyValueFactory<>("vehicleRegNo"));
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<>("customerFullName"));
        try{
            buildPartsUsedData();
        
            // selectedItemProperty = gives you the item the user selected form the table.
            // add listner to your tableview selectedItemProperty
            // The listener is gonna sit on the item of the tableView and its gonna wait for some action to happen, Ex: User selecting an item from the tableview 
            //
               /*table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override //This method will be called whenever user selected row
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {   //Returns the currently selected item. Check whether item is selected.
                        if (table.getSelectionModel().getSelectedItem() != null) {
                            Connection conn = null;
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                            System.out.println("Opened Database Successfully initial");
                            partID = table.getSelectionModel().getSelectedItem().getPartIDentify();
                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            // Execute query and store results in a resultSet
                            ResultSet rs = state.executeQuery("SELECT * FROM vehiclePartsStock WHERE parts_id= " + "'" + partID + "'");
                            while (rs.next()) {
                                // get data from db and show it on the textFields.
                                //idNumber.setText(String.valueOf(rs.getInt("parts_id")));
                                partID = rs.getInt("parts_id");
                                name.setText(rs.getString("nameofPart"));
                                description.setText(rs.getString("description"));
                                stockLevels.setText(String.valueOf(rs.getInt("stockLevelsOfParts")));
                                cost.setText(String.valueOf(rs.getDouble("cost")));
                                //arrivedStockDate.setText(rs.getString("arrivedStockDate"));
                            }

                            showPart.setPartId(partID);
                            showPart.setPartName(name.getText());
                            showPart.setPartDescription(description.getText());
                            showPart.setPartStockLevel(Integer.parseInt(stockLevels.getText()));
                            showPart.setCost(Double.parseDouble(cost.getText()));
                            //showPart.setArrivedDate(arrivedStockDate.getText());

                            state.close();
                            conn.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/
        } catch (Exception e) {
            System.out.println("Error 1.");
            e.printStackTrace();

        }
    }
        // TODO
    }    
    

