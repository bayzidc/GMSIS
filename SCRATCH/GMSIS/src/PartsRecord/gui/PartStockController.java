/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import PartsRecord.logic.parts;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartStockController implements Initializable {
    @FXML
    private TextField idNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField description ;
    @FXML
    private TextField stockLevels;
    @FXML
    private TextField cost;
    @FXML
    private TextField arrivedStockDate;
    
    @FXML
    private TableView<parts> table;
    @FXML
    private TableColumn<parts, Integer> idCol;
    @FXML
    private TableColumn<parts, String> nameCol;
    @FXML
    private TableColumn<parts, String> descriptionCol;
    @FXML
    private TableColumn<parts, Integer> stockLevelsCol ;
    @FXML
    private TableColumn<parts, Double> costCol;
    @FXML
    private TableColumn<parts, String> arrivedStockDateCol;
    @FXML
    private Button back;
    @FXML
    private Button add;
    @FXML
    private Button edit;
    @FXML
    private Button clear;
    @FXML
    private Button delete;
    ObservableList<parts> data;
    public int partID;
    
    
    @FXML
    private void backButton(ActionEvent event) throws IOException // method which goes back to admin page
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();           
        stage2.setScene(admin_Scene);
        stage2.show();
        
    }
    
    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        clearFields();
      
    }
      
    
    
    
    @FXML
    public void addButton(ActionEvent event) throws IOException, ClassNotFoundException {
        createData(); // Add the data collected from the fields to the database.
        System.out.println("Parts added to Database");
        buildPartsStockData();  // Add the data from the database to the tableview.
        // Clear all the input fields.
        clearFields();
        
    }
    
    @FXML
    public void editButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            if (idNumber.getText().equals("") || name.getText().equals("")||description.getText().equals("") || stockLevels.getText().equals("")|| cost.getText().equals("")) 
            {
                
                alertError("Please complete all the fields.");
               
            } 
            else {
                editData(); 
                buildPartsStockData();
                alertInformation("The database has been updated.");
                
            }
        } catch (Exception e) {
            
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    @FXML
    public void editData() throws ClassNotFoundException {
        Connection conn = null;

        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehiclePartsStock SET nameofPart=?,d=?,description=?,stockLevelsOfParts=?,cost=? WHERE parts_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setString(1,name.getText());
            state.setString(2, description.getText());
            state.setInt(3, Integer.parseInt(stockLevels.getText()));
            state.setDouble(4, Double.parseDouble(cost.getText()));
            state.setInt(5, Integer.parseInt(idNumber.getText()));
          
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to delete the parts?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            int idSelected = table.getSelectionModel().getSelectedItem().getPartID();

            if (result.get() == buttonTypeYes && isPartsDeleted()){
                
                alertInformation("PartsID: " + idSelected + "has been deleted.");
               
            }
            if(idSelected == 0) {
                
                alertError("The Part does not exists.");
            }
        }
        catch (Exception e) {
            System.out.println(e);
         
        }

    }
    
    private boolean isPartsDeleted() throws ClassNotFoundException {
        boolean partsDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getPartID();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM vehiclePartsStock WHERE parts_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, ID);
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
    
    
    
    // Filling data to the tableView From the database.
    public void buildPartsStockData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");

            String SQL = "Select * from vehiclePartsStock";
            // Execute query and store results in a resultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                // get data from db and add to the Observable list
                // Calling the constructor
                // show getDouble()problem
                
                data.add(new parts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5)));
            }
            
            table.setItems(data);
            rs.close();
            conn.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data 2");
        }
        
        
        
        
    }
    
    public void createData() throws ClassNotFoundException {

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened database successfully.");

            String sql = "insert into vehiclePartsStock(parts_id,nameofPart,description,stockLevelsOfParts,cost,arrivedStockDate) values(?,?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            if (idNumber.getText().equals("") || name.getText().equals("")||description.getText().equals("") || stockLevels.getText().equals("")|| cost.getText().equals("")) 
            {
                
                alertError("Please complete all the fields.");
               
            } 
            else {
                state.setString(1, idNumber.getText());
                state.setString(2, name.getText());
                state.setString(3, description.getText());
                state.setString(4, stockLevels.getText());
                state.setString(5, cost.getText());
                state.setString(6,arrivedStockDate.getText());
               
                state.execute();

                state.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data 3");
        }
        //return submit;       

    }
    
    public void clearFields() {
        idNumber.clear();
        name.clear();
        description.clear();
        stockLevels.clear();
        cost.clear();
        
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
        alert.setTitle("Information");
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set cell value factory to tableview
        // Example : Use the partsId property of our object 'parts' and get all the values of their partsId
        
        
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("partId"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("partName"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("partDescription"));
        stockLevelsCol.setCellValueFactory(
                new PropertyValueFactory<>("partStockLevel"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
    try {
            buildPartsStockData();
            // selectedItemProperty = gives you the item the user selected form the table.
            // add listner to your tableview selectedItemProperty
            // The listener is gonna sit on the item of the tableView and its gonna wait for some action to happen, Ex: User selecting an item from the tableview 
            //
            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override //This method will be called whenever user selected row
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {   //Returns the currently selected item. Check whether item is selected.
                        if (table.getSelectionModel().getSelectedItem() != null) { 
                            Connection conn = null;
                            partID = table.getSelectionModel().getSelectedItem().getPartID(); // Get the partId of the row selected.
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                            System.out.println("Opened Database Successfully");

                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            // Execute query and store results in a resultSet
                            ResultSet rs = state.executeQuery("SELECT * FROM vehiclePartsStock WHERE parts_id= " + "'" + partID + "'");
                            while (rs.next()) {
                                // get data from db and add to the tableview.
                                partID = rs.getInt("parts_id");
                                name.setText(rs.getString("nameofPart"));
                                description.setText(rs.getString("description"));
                                stockLevels.setText(String.valueOf(rs.getInt("stockLevelsOfParts")));
                                cost.setText(String.valueOf(rs.getDouble("cost")));
                               
               
                            }
                            state.close();
                            conn.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            
            
        }
    }
        // TODO
    }    
    
