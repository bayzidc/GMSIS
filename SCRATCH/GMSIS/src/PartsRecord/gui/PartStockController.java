/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import PartsRecord.logic.PartsItem;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Authentication.sqlite;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.DateCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartStockController implements Initializable {
    
    @FXML
    private TextField nameOfParts;
    @FXML
    private TextField description;
    @FXML
    private TextField cost;
    @FXML
    private ComboBox<Integer> partIdCombo;
    @FXML
    private TextField quantity;
    @FXML
    private DatePicker arrivalDate;
    
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
    private TableView<PartsItem> table2;
    @FXML
    private TableColumn<PartsItem, String> partNameCol;
    @FXML
    private TableColumn<PartsItem, String> arrivalDateCol;
    @FXML
    private TableColumn<PartsItem, Integer> quantityCol;
    
    
    @FXML
    private Button back;
    @FXML
    private Button addparts;
    @FXML
    private Button edit;
    @FXML
    private Button clear;
    @FXML
    private Button clear2;
    @FXML
    private Button delete;
    @FXML
    private Button addItem;
    public static parts showPart = new parts(0, "", "", 0, 0.0); // an object of type parts.
    ObservableList<parts> data;
    
    public static PartsItem partItemObj = new PartsItem("",0, "");
    ObservableList<PartsItem> partItemData;
    ObservableList<Integer> partIdList = FXCollections.observableArrayList();
    
    public int partID;
    public int currentStockLevel;
    public int stockLevel;
    
    // add data to the database from the textfield.
    public void createData(parts part) throws ClassNotFoundException {

        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while creatingData");
            //query you have to write to insert your data
            String sql = "insert into vehiclePartsStock(nameofPart,description,stockLevelsOfParts,cost) values(?,?,?,?)";
            //The query is sent to the database and prepared there which means the SQL statement is "analysed".
            PreparedStatement state = conn.prepareStatement(sql);
            
                state.setString(1, part.getPartName());
                state.setString(2, part.getPartDescription());
                state.setInt(3, 0);
                state.setDouble(4, part.getCost());
                state.execute();

                state.close();
                conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Creating Data");
        }
    }
    
    // Filling data to the tableView From the database.
    public void buildPartsStockData() {
        
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while buildingPartsStockData");

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
            System.out.println("Error on Building Parts Stock Data.");
        }

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set cell value factory to tableview
        // Example : Use the partsId property of our object 'parts' and get all the values of their partsId
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
        arrivalDate.setDayCellFactory(dayCellFactory);
        try {
            fillPartsIdCombo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        partNameCol.setCellValueFactory(
                new PropertyValueFactory<>("PartName"));
        
        arrivalDateCol.setCellValueFactory(
                new PropertyValueFactory<>("ArrivalDate"));
        
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("Quantity"));
        
        
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
        
        
        
        try {
            buildPartsStockData();
            buildItemData();
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
                            conn = (new sqlite().connect());
                            System.out.println("Opened Database Successfully in initialization");
                            
                            partID = table.getSelectionModel().getSelectedItem().getPartIDentify();
                            currentStockLevel = table.getSelectionModel().getSelectedItem().getPartStockLevel();
                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            // Execute query and store results in a resultSet
                            ResultSet rs = state.executeQuery("SELECT * FROM vehiclePartsStock WHERE parts_id= " + "'" + partID + "'");
                            while (rs.next()) {
                                // get data from db and show it on the textFields.
                                //idNumber.setText(String.valueOf(rs.getInt("parts_id")));
                                partID = rs.getInt("parts_id");
                                nameOfParts.setText(rs.getString("nameofPart"));
                                description.setText(rs.getString("description"));
                                currentStockLevel = rs.getInt("stockLevelsOfParts");
                                cost.setText(String.valueOf(rs.getDouble("cost")));
                                
                            }

                            showPart.setPartId(partID);
                            showPart.setPartName(nameOfParts.getText());
                            showPart.setPartDescription(description.getText());
                            showPart.setPartStockLevel(currentStockLevel);
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
            System.out.println("Error in initialization.");
            e.printStackTrace();

        }
    }
    
    @FXML
    public void addButton(ActionEvent event) throws IOException, ClassNotFoundException {
        if(!isFieldsCompleted2()){
            alertError("Please complete all the fields."); 
        }
        else{
            showPart.setPartName((nameOfParts.getText()).toLowerCase());
            showPart.setPartDescription(description.getText());
            showPart.setCost(Double.parseDouble(cost.getText()));
            boolean checkName = checkNameAlreadyExist(showPart.getPartName());
            if(checkName){
               alertInformation("The name of part already exists in the system");
            }
            else {
               createData(showPart);
               buildPartsStockData();
               clearFields();
            }
            }
        }

    public boolean checkNameAlreadyExist(String name) throws ClassNotFoundException 
    {
        boolean check = false;
        String nameFromDatabase;
        Connection conn = null;
        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully 44 ");
            String query = "SELECT nameofPart from vehiclePartsStock WHERE nameofPart ='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(query);
        
            if(rs.next()){
                nameFromDatabase = rs.getString("nameofPart");
                if(nameFromDatabase.equalsIgnoreCase(name)){
                    check= true;
                }
                
            }
            
            rs.close();
            conn.close();
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Error while checking whether name exists.");
            //throw new ClassNotFoundException();
        }
        
        return check;
    }
    
     @FXML
    public void addItemButton(ActionEvent event) throws IOException, ClassNotFoundException {
        if(isFieldsCompleted()){
            alertError("Please complete all the fields."); 
        }
        else {
            int value = partIdCombo.getValue();
            partItemObj.setPartName(findPartsName(value));
            partItemObj.setQuantity(Integer.parseInt(quantity.getText()));
            partItemObj.setArrivalDate(arrivalDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            createDeliveryData(partItemObj);
            buildItemData();
            updateStockLevel(value, partItemObj.getQuantity());
            buildPartsStockData();
            clearFields(); // Clear all the input fields.
        }    
    }
    
    public String findPartsName(int ID) throws ClassNotFoundException{
        String name = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while find parts name ");
            String query = "SELECT nameofPart from vehiclePartsStock WHERE parts_id ='" + ID + "'";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                name = rs.getString("nameofPart");
            }
            rs.close();
            conn.close();
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Error while find name of parts to add it to the tableView.");
            //throw new ClassNotFoundException();
        }
        return name;
        
    }
    
    public void createDeliveryData(PartsItem partItemObj) throws ClassNotFoundException {

        Connection conn = null;

        try {
            
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while creatingDeliveryData");
            //query you have to write to insert your data
            String sql = "insert into stockDelivery(partName,quantity,deliverydate) values(?,?,?)";
            //The query is sent to the database and prepared there which means the SQL statement is "analysed".
            PreparedStatement state = conn.prepareStatement(sql);
            
            if (isFieldsCompleted()){

                alertError("Please complete all the fields.");

            } else {
                state.setString(1, partItemObj.getPartName());
                state.setInt(2, partItemObj.getQuantity());
                state.setString(3, partItemObj.getArrivalDate() );
                state.execute();

                state.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Creating Delivery Data");
        }
    }    
            
    // Filling data to the tableView From the database.
    public void buildItemData() {
        
        partItemData = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while buildingItemData");

            String SQL = "Select * from stockDelivery";
            // Execute query and store results in a resultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                partItemObj.setPartName(rs.getString(1));
                partItemObj.setQuantity(rs.getInt(2));
                partItemObj.setArrivalDate(rs.getString(3));
                partItemData.add(new PartsItem(partItemObj.getPartName(), partItemObj.getQuantity(), partItemObj.getArrivalDate()));
            }

            table2.setItems(partItemData);
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Item Data.");
        }

    }
    
    public void updateStockLevel(int id, int quantity)throws ClassNotFoundException {
        Connection conn = null;
        System.out.println("the quantity is " + quantity);
        int stockLevel = findStockLevel(id);
        int newStockLevel = stockLevel + quantity;
        System.out.println("The new stock level is " + newStockLevel);
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully for updating stockLevel.");

            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE parts_id =?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setInt(1, newStockLevel);
            state.setInt(2,id);
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error while updating stockLevel");
        }
    }
    
    public int findStockLevel(int id) throws ClassNotFoundException {
        
        int oldStock = 0;
        Connection conn = null;
        try { // Create a Java Connection to the database.
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully  for finding stock.");
            String sql = "SELECT stockLevelsOfParts FROM vehiclePartsStock where parts_id='" + id + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                oldStock = rs.getInt("stockLevelsOfParts");
                System.out.println("The old stock value is: " + oldStock);

            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in finding old stock.");
        }
        return oldStock;
    }
    
    @FXML
    private void editButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            
            if (!isFieldsCompleted2()) {
                alertError(" Please complete all the fields.");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("");
                alert.setContentText("Are you sure you want to update the parts?");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();

                int idSelected = table.getSelectionModel().getSelectedItem().getPartIDentify();
                String name = table.getSelectionModel().getSelectedItem().getPartName();

                if (result.get() == buttonTypeYes) {
                  showPart.setPartName(nameOfParts.getText());
                  showPart.setPartDescription(description.getText());
                  showPart.setCost(Double.parseDouble(cost.getText()));
                  System.out.println("");
                  updateData(showPart, idSelected); //Gets ID 
                alertInformation("UserID: " + idSelected + " has been updated");
                }
                buildPartsStockData();
                String newName = findNewName(idSelected);
                if(!newName.equals(name)) {
                    updateNameInItemTable(newName, name);
                    buildItemData();
                }
                
                
            }
        }    
        catch(Exception e) {
            System.out.println("Error in edit button");
        }
    }
    
    @FXML
    private void updateData(parts showPart, int Id) throws ClassNotFoundException {

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while updating data.");
            System.out.println("part Name edited : " + showPart.getPartName());
            System.out.println("part description edited : " + showPart.getPartDescription());
            System.out.println("part cost edited : " + showPart.getCost());
        
            String sql = "UPDATE vehiclePartsStock SET nameofPart=?,description=?,cost=? WHERE parts_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            
                state.setString(1, showPart.getPartName());
                state.setString(2,showPart.getPartDescription() );
                state.setDouble(3,showPart.getCost());
                state.setInt(4, Id);
                
                state.execute();

                state.close();
                conn.close();
                clearFields();
            }//submit=true;
         catch (Exception e) {
            
            System.out.println("Error while updating data.");
        }

    }
    
    public String findNewName(int ID)throws ClassNotFoundException {
        String newName = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while finding new parts name ");
            String query = "SELECT nameofPart from vehiclePartsStock WHERE parts_id ='" + ID + "'";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                newName = rs.getString("nameofPart");
                System.out.println("The name of new part is " + newName);
            }
            rs.close();
            conn.close();
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Error while checking.");
            //throw new ClassNotFoundException();
        }
        return newName;
    }
    
    @FXML
    private void updateNameInItemTable(String newName, String oldName) throws ClassNotFoundException {

        Connection conn = null;
        System.out.println("The new name is " + newName);
        System.out.println("The old name is " + oldName);

        try {
            conn = (new sqlite().connect());
            String sql = "UPDATE stockDelivery SET partName=? WHERE partName=?";
            PreparedStatement state = conn.prepareStatement(sql);
            
                state.setString(1, newName);
                state.setString(2,oldName );
                state.execute();

                state.close();
                conn.close();
                clearFields();
            }//submit=true;
         catch (Exception e) {
            
            System.out.println("Error while updating data.");
        }

    }
    
    @FXML
    public void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        try {
            if(!isFieldsCompleted2()){
                alertError("Please complete all the fields."); 
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("");
                alert.setContentText("Are you sure you want to delete the parts?");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();

                int idSelected = table.getSelectionModel().getSelectedItem().getPartIDentify();

                if (result.get() == buttonTypeYes && isPartsDeleted(showPart)) {

                    alertInformation("PartsStockID: " + idSelected + " has been deleted.");

                }
                buildPartsStockData();
            }
        } catch (Exception e) {
            System.out.println(e);

        }

    }
    
    private boolean isPartsDeleted(parts part) throws ClassNotFoundException {
        boolean partsDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getPartIDentify();

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM vehiclePartsStock WHERE parts_id =?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, part.getPartIDentify());
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
    
    
    
    @FXML
    private void backButton(ActionEvent event) throws IOException // method which goes back to admin page
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
    
    private void fillPartsIdCombo() throws ClassNotFoundException {

        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            //conn = (new sqlite().connect());
            String SQL = "Select parts_id from vehiclePartsStock";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                partIdList.add(rs.getInt("parts_id"));
            }
            partIdCombo.setItems(partIdList);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error filling in the part id combo.");
        }
    }
    
    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException {
        clearFields();

    }
    
    public void clearButton2(ActionEvent event) throws IOException, ClassNotFoundException {
        clearItemFields();

    }
    
    public boolean isFieldsCompleted() {
        if (partIdCombo.getValue() != null || quantity.getText().equals("") || arrivalDate.getValue().equals("")) {
            return false;
        }
        return true;
    }
    
    public boolean isFieldsCompleted2() {
        if (nameOfParts.getText().equals("") || description.getText().equals("") || cost.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    public void clearFields() {
        nameOfParts.clear();
        description.clear();
        cost.clear();
       
    }
    
    public void clearItemFields(){
        partIdCombo.setValue(null);
        quantity.clear();
        ((TextField) arrivalDate.getEditor()).clear();
        
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
    
    
    
}
