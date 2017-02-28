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
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> nameCombo;
    @FXML
    private TextField description;
    @FXML
    private TextField stockLevels;
    @FXML
    private TextField cost;
    @FXML
    private TableView<parts> table;
    @FXML
    private TableColumn<parts, Integer> idCol;
    @FXML
    private TableColumn<parts, String> nameCol;
    @FXML
    private TableColumn<parts, String> descriptionCol;
    @FXML
    private TableColumn<parts, Integer> stockLevelsCol;
    @FXML
    private TableColumn<parts, Double> costCol;
    @FXML
    private Button back;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button delete;
    ObservableList<parts> data;
    public int partID;
    public int stockLevel;
    public static parts showPart = new parts(0, "", "", 0, 0.0); // an object of type parts.

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
    
    

    

    @FXML
    public void addButton(ActionEvent event) throws IOException, ClassNotFoundException {
        showPart.setPartName(nameCombo.getValue());
        showPart.setPartDescription(description.getText());
        showPart.setPartStockLevel(Integer.parseInt(stockLevels.getText()));
        showPart.setCost(Double.parseDouble(cost.getText()));
        boolean checkName = checkNameAlreadyExist(showPart.getPartName());
        if(checkName){
           int value =findStockLevel(showPart.getPartName());
           updateStockLevel(showPart, value);
           System.out.println("The stock level is updated. ");
        }
        else {
            createData(showPart);
        }
        
        buildPartsStockData();  // Add the data from the database to the tableview.
        clearFields(); // Clear all the input fields.
    }
    
    
    public boolean checkNameAlreadyExist(String name) throws ClassNotFoundException 
    {
        boolean check = false;
        Connection conn = null;
        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully 44 ");
            String query = "SELECT nameofPart from vehiclePartsStock WHERE nameofPart ='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(query);
        
            if(rs.next()){
                check= true;
            }
            
            rs.close();
            conn.close();
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Error while checking.");
            //throw new ClassNotFoundException();
        }
        return check;
    }
    
    private int findStockLevel(String name) throws ClassNotFoundException
    {
        int stockLevel = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
          

            String SQL = "Select stockLevelsOfParts from vehiclePartsStock where nameofPart='"+ name +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) 
            {       
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
    
    public void updateStockLevel(parts showPart, int value)throws ClassNotFoundException {
        Connection conn = null;
        
        System.out.println("The old stocklevel is " + value + ".");
        int stockField = showPart.getPartStockLevel();
        System.out.println("The stock should increase by " + stockField + ".");
        int newStockLevel = value + stockField;
        

        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE nameofPart =?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setInt(1, newStockLevel);
            state.setString(2,showPart.getPartName() );
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
        
    

    

    // Filling data to the tableView From the database.
    public void buildPartsStockData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully 2");

            String SQL = "Select * from vehiclePartsStock";
            // Execute query and store results in a resultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                //  a ResultSet object that retrieves the results of your query,
                // and executes a simple while loop, which retrieves and displays those results.
                // Calling the constructor
                showPart.setPartId(rs.getInt(1));
                showPart.setPartName(rs.getString(2));
                showPart.setPartDescription(rs.getString(3));
                showPart.setPartStockLevel(rs.getInt(4));
                showPart.setCost(rs.getDouble(5));
                
                data.add(new parts(showPart.getPartIDentify(), showPart.getPartName(), showPart.getPartDescription(), showPart.getPartStockLevel(), showPart.getCost()));
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
    public void createData(parts part) throws ClassNotFoundException {

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            System.out.println("Opened database successfully 1.");
            System.out.println("Creating data.");
            //query you have to write to insert your data
            String sql = "insert into vehiclePartsStock(nameofPart,description,stockLevelsOfParts,cost) values(?,?,?,?)";
            //The query is sent to the database and prepared there which means the SQL statement is "analysed".
            PreparedStatement state = conn.prepareStatement(sql);
            
            if (nameCombo.getValue()==null || description.getText().equals("") || stockLevels.getText().equals("") || cost.getText().equals("")) {

                alertError("Please complete all the fields.");

            } else {
                // the values are sent to the server
                // use the appropriate state.setXX() methods to set the appropriate values for the parameters that you've defined.
                // Then call state.executeUpdate() to execute the call to the database.
                state.setString(1, part.getPartName());
                state.setString(2, part.getPartDescription());
                state.setInt(3, part.getPartStockLevel());
                state.setDouble(4, part.getCost());
                state.execute();

                state.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Creating Data");
        }
            

    }
    
    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException {
        clearFields();

    }

    public void clearFields() {
        nameCombo.setValue(null);
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
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set cell value factory to tableview
        // Example : Use the partsId property of our object 'parts' and get all the values of their partsId

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("partIDentify"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("partName"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("partDescription"));
        stockLevelsCol.setCellValueFactory(
                new PropertyValueFactory<>("partStockLevel"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
        //arrivedStockDateCol.setCellValueFactory(
                //new PropertyValueFactory<>("arrivedDate"));
        ObservableList<String> partsName = FXCollections.observableArrayList("Spark Plugs", "Prop Shaft", "Handbrake Cable","Bumper","Rims","HeadLights", "Tail Lights","Radiator", "Fender", "Roof Rack");
        nameCombo.setItems(partsName);
        
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
                                nameCombo.setValue(rs.getString("nameofPart"));
                                description.setText(rs.getString("description"));
                                stockLevels.setText(String.valueOf(rs.getInt("stockLevelsOfParts")));
                                cost.setText(String.valueOf(rs.getDouble("cost")));
                                
                            }

                            showPart.setPartId(partID);
                            showPart.setPartName(nameCombo.getValue());
                            showPart.setPartDescription(description.getText());
                            showPart.setPartStockLevel(Integer.parseInt(stockLevels.getText()));
                            showPart.setCost(Double.parseDouble(cost.getText()));
                            

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
    
}
