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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import java.util.regex.*;

/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartStockController implements Initializable {
    
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField nameOfParts;
    @FXML
    private TextArea description;
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
    private Button update;
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
    // ObservableList to hold the data for the tableviews.
    ObservableList<parts> data;
    ObservableList<PartsItem> partItemData;
    // ObservableList to hold the partId's for the partIdCombo
    ObservableList<Integer> partIdList = FXCollections.observableArrayList();
    // Creating objects.
    public static parts showPart = new parts(0, "", "", 0, 0.0); // an object of type parts.
    public static PartsItem partItemObj = new PartsItem("",0, "");
    
    private int partID;
    private int currentStockLevel;
    private int stockLevel;
    private String partNameFromTable;
    private Double costFromTable;
    private int idFromTable;
    
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
        arrivalDate.setDayCellFactory(dayCellFactory);
        try {
            fillPartsIdCombo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        description.setWrapText (true);
        addparts.setTooltip(new Tooltip("Complete all the fields and click to add a new part"));
        addItem.setTooltip(new Tooltip("Complete all the fields and click to add item to stock"));
        clear.setTooltip(new Tooltip("Click to clear all the fields"));
        clear2.setTooltip(new Tooltip("Click to clear all the fields"));
        edit.setTooltip(new Tooltip("Select a row from the table and click to edit a part"));
        update.setTooltip(new Tooltip("Edit a part and click to update any changes"));
        delete.setTooltip(new Tooltip("Select a row from the table and click to delete a part"));
        
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * This method adds data from the text fields to the vehiclePartStock table in the database.
     * @param part
     * @throws ClassNotFoundException 
     */
    public void createData(parts part) throws ClassNotFoundException {

        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully.");
            
            String sql = "insert into vehiclePartsStock(nameofPart,description,stockLevelsOfParts,cost) values(?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            
                state.setString(1, part.getPartName());
                state.setString(2, part.getPartDescription());
                state.setInt(3, 0);
                state.setDouble(4, part.getCost());
                state.execute();

                state.close();
                conn.close();
            
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
   
        }
    }
    
    /**
     * This method fills data to the tableView from the vehiclePartsStock table in the database.
     */
    public void buildPartsStockData() {
        
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while buildingPartsStockData");

            String SQL = "Select * from vehiclePartsStock";
            
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
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
            
           alertError("Error on building data for repair information.");
        }

    }
    
    
    /**
     * This method is used to load data to the delivery table from the stockDelivery table in the database.
     */
    public void buildItemData() {
        
        partItemData = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully.");

            String SQL = "Select * from stockDelivery";
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
          alertError("Error on building stock delivery data.");  
        }

    }
    
    
    /**
     * This method is called when the user clicks the add part button to add a new part to the stock.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    
    @FXML
    public void addButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to add this part?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeNo) {
            return;
        }
        if(!isFieldsCompleted2()){
            return;
        }
        if(!verifyCost(cost.getText())){
            return;
        }
        if(!checkForWhiteSpace()){
            return;
        }
        
        showPart.setPartName((nameOfParts.getText()).toLowerCase());
        showPart.setPartDescription(description.getText());
        showPart.setCost(Double.parseDouble(cost.getText()));
        boolean checkName = checkNameAlreadyExist(showPart.getPartName());
        if(checkName){
            alertInformation("The part already exists in the stock. Enter a different name to add a new part.");
        }
        else {
            alertInformation("The new part is added to the stock.");
            createData(showPart);
            buildPartsStockData();
            fillPartsIdCombo();
            clearFields();
        }
        
    }
    
  
    /**
     * This method is used to check whether a part name already exists in the stock.
     * If the part exists then the user is informed and asked to give a different name to add a new part.
     * Else if the part doesn't exist, it is added to the stock.
     * @param name
     * @return
     * @throws ClassNotFoundException 
     */
    public boolean checkNameAlreadyExist(String name) throws ClassNotFoundException {
        boolean check = false;
        String nameFromDatabase;
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
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
            
        }
        
        return check;
    }
    
    /**
     * This method checks whether the cost input is valid or not using a regular expression.
     * If its not valid the user is informed.
     * @param cost
     * @return 
     */
    public boolean verifyCost(String cost){
        boolean check = true;
        if(!cost.matches("^[0-9]+(\\.[0-9]{1,2}$)?")){
           alertError("Please enter a valid cost.");
           check = false;
        }
        return check;
    }
    
    /**
     * This method is called when the user clicks the add item button to add item to the stock.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
     @FXML
    public void addItemButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to add this part?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeNo) {
            return;
        }
        if(!isFieldsCompleted()){
            return;
        }
        if(!verifyQuantity(quantity.getText())){
            return;
        }
        if(!isQuantityZero(quantity.getText())){
            return;
        }
        
        int value = partIdCombo.getValue();
        partItemObj.setPartName(findPartsName(value));
        partItemObj.setQuantity(Integer.parseInt(quantity.getText()));
        partItemObj.setArrivalDate(arrivalDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("The quantity from the textField " + partItemObj.getQuantity());
        int stock= partItemObj.getQuantity();
        alertInformation("The item is added to the stock.");
        createDeliveryData(partItemObj);
        buildItemData();
        updateStockLevel(value, stock);
        buildPartsStockData();
        alertInformation("The stock level is updated.");
        clearItemFields(); 
          
    }
    
    /**
     * This method checks whether the user entered 0 for the quantity.
     * If 0 is entered the user is informed that quantity cant be 0.
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
     * This method is used to find the parts name given the partsID as an argument.
     * @param ID
     * @return
     * @throws ClassNotFoundException 
     */  
    public String findPartsName(int ID) throws ClassNotFoundException{
        String name = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            
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
        }
        return name;
        
    }
    
    /**
     * This method adds data from the text fields to the stockDelivery table in the database.
     * @param partItemObj
     * @throws ClassNotFoundException 
     */
    public void createDeliveryData(PartsItem partItemObj) throws ClassNotFoundException {

        Connection conn = null;

        try {
            
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully while creatingDeliveryData");
            
            String sql = "insert into stockDelivery(partName,quantity,deliverydate) values(?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            
            
                state.setString(1, partItemObj.getPartName());
                state.setInt(2, partItemObj.getQuantity());
                state.setString(3, partItemObj.getArrivalDate() );
                state.execute();

                state.close();
                conn.close();

            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
    }    
            
    
    /**
     * This method updates the stockLevel of parts when a new delivery is made.
     * @param id
     * @param quantity
     * @throws ClassNotFoundException 
     */
    public void updateStockLevel(int id, int quantity)throws ClassNotFoundException {
        Connection conn = null;
        int stockLevel = findStockLevel(id);
        System.out.println("The quantity is " + quantity);
        System.out.println("the stock level is " + stockLevel);
        int newStockLevel = stockLevel + quantity;
        System.out.println("The new stock is " + newStockLevel);
        
        try {
            conn = (new sqlite().connect());
            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE parts_id =?";
            PreparedStatement state = conn.prepareStatement(sql);
            
            state.setInt(1, newStockLevel);
            state.setInt(2,id);
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * This method is used to get the current stockLevel of a part.
     * @param id
     * @return
     * @throws ClassNotFoundException 
     */
    public int findStockLevel(int id) throws ClassNotFoundException {
        
        int oldStock = 0;
        Connection conn = null;
        try { 
            conn = (new sqlite().connect());
            String sql = "SELECT stockLevelsOfParts FROM vehiclePartsStock where parts_id='" + id + "'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                oldStock = rs.getInt("stockLevelsOfParts");
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
           
        }
        return oldStock;
    }
    
    /**
     * This method is called when the user wants to edit a part in the stock.
     * When the user selects a row from the parts stock table and clicks the edit button the data from the table is shown in the text fields to let the user edit the part.
     * @param event
     * @throws ClassNotFoundException 
     */
    @FXML
    private void editParts(ActionEvent event) throws ClassNotFoundException
    {       
     try
        {
            idFromTable = table.getSelectionModel().getSelectedItem().getPartIDentify();
        }
        catch(Exception e)
        {
            alertError("Please select a row first to edit a part.");
            return;
        }
        showPart.setPartId(idFromTable);
        partNameFromTable = table.getSelectionModel().getSelectedItem().getPartName();      
        String partDescription = table.getSelectionModel().getSelectedItem().getPartDescription();     
        costFromTable = table.getSelectionModel().getSelectedItem().getCost();
        
        nameOfParts.setText(partNameFromTable);  
        description.setText(partDescription);
        cost.setText(String.valueOf(costFromTable));
    }
    
    /**
     * This method is called when the user is done editing a part and wants to update the data in the system.
     * @param event
     * @throws ClassNotFoundException 
     */
    @FXML 
    private void updateParts(ActionEvent event) throws ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to update the parts?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeNo) {
            return;
        }
        if(!isFieldsCompleted2()){
            return;
        }
        if(!verifyCost(cost.getText())){
            return;
        }  
        
        showPart.setPartName((nameOfParts.getText().toLowerCase()));
        showPart.setPartDescription(description.getText());
        showPart.setCost(Double.parseDouble(cost.getText()));
        updatePartsInDataBase(showPart);
        buildPartsStockData();
        String newName = findNewName(idFromTable);
        if(!newName.equals(partNameFromTable)) {
            updateNameInItemTable(newName, partNameFromTable);
            buildItemData();
        }
    }

    /**
     * This method is used to update the edited data in the database.
     * @param showPart
     * @throws ClassNotFoundException 
     */
    @FXML
    private void updatePartsInDataBase(parts showPart) throws ClassNotFoundException {

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String sql = "UPDATE vehiclePartsStock SET nameofPart=?,description=?,cost=? WHERE parts_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            
                state.setString(1, showPart.getPartName());
                state.setString(2,showPart.getPartDescription() );
                state.setDouble(3,showPart.getCost());
                state.setInt(4, showPart.getPartIDentify());
                
                state.execute();

                state.close();
                conn.close();
                alertInformation("PartID: " + showPart.getPartIDentify() + " has been updated successfully.");
                clearFields();
            }
         catch (SQLException e) {
            
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    /**
     * This method is used to find the new name of the part when the user edits a part from the stock.
     * @param ID
     * @return
     * @throws ClassNotFoundException 
     */
    public String findNewName(int ID)throws ClassNotFoundException {
        String newName = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            
            String query = "SELECT nameofPart from vehiclePartsStock WHERE parts_id ='" + ID + "'";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                newName = rs.getString("nameofPart");
                
            }
            rs.close();
            conn.close();
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return newName;
    }
    
    /**
     * This method is used to update the name in the stockDelivery table if the user edited the name of a part from the stock.
     * @param newName
     * @param oldName
     * @throws ClassNotFoundException 
     */
    @FXML
    private void updateNameInItemTable(String newName, String oldName) throws ClassNotFoundException {

        Connection conn = null;
        
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
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    
    /**
     * This method is called when the user selects a row and clicks the delete button to delete a part from the stock.
     * The part is then deleted from the stock.
     * @param event
     * @throws ClassNotFoundException 
     */
    @FXML 
    private void deleteParts(ActionEvent event) throws ClassNotFoundException
    {
        int id;
        try
        {
            id = table.getSelectionModel().getSelectedItem().getPartIDentify();
        }
        catch(Exception e)
        {
            alertError("Please select a row first to delete a part.");
            return;
        }
        showPart.setPartId(id);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to delete the parts?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeYes && isPartsDeleted(showPart)) {

            alertInformation("PartsStockID: " + id + " has been deleted.");

        }
        buildPartsStockData();
        fillPartsIdCombo();
    }
    
    /**
     * This method deletes the part from the stock and returns true to show that the part has been deleted from the stock.
     * @param part
     * @return
     * @throws ClassNotFoundException 
     */
    public boolean isPartsDeleted(parts part) throws ClassNotFoundException {
        boolean partsDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getPartIDentify();

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            
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
            
        }
        return partsDeleted;
    }
    
    /**
     * This method is used to fill up the parts Id Combo.
     * @throws ClassNotFoundException 
     */
    public void fillPartsIdCombo() throws ClassNotFoundException {
        
        partIdList.clear();
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            
            String SQL = "Select parts_id from vehiclePartsStock";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                partIdList.add(rs.getInt("parts_id"));
            }
            partIdCombo.setItems(partIdList);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    
    @FXML
    public void backButton(ActionEvent event) throws IOException
    {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        pane.getChildren().setAll(rootPane);
        
    }
    
    
    /**
     * This method is called when the user clicks the clear button in the add part to stock section.
     * It clears out all the text from the text fields.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException {
        clearFields();

    }
    /**
     * This method is called when the user clicks the clear button in the add item to stock section.
     * It clears out all the text from the comboBox/text field/date picker.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void clearButton2(ActionEvent event) throws IOException, ClassNotFoundException {
        clearItemFields();

    }
    
    /**
     * This method is used to check whether a user has completed all the fields in the add part to stock section.
     * @return 
     */
    public boolean isFieldsCompleted() {
        if (partIdCombo.getValue() == null || quantity.getText().equals("") || arrivalDate.getValue().equals("")) {
            alertError("Please complete all the fields."); 
            return false;
        }
        return true;
    }
    
    /**
     * This method is used to check whether a user has completed all the fields in the add item to stock section.
     * @return 
     */
    public boolean isFieldsCompleted2() {
        if (nameOfParts.getText().equals("") || description.getText().equals("") || cost.getText().equals("")) {
            alertError("Please complete all the fields."); 
            return false;
        }
        return true;
    }
    
    /**
     * This method clears all the fields in the add part to stock section.
     */
    public void clearFields() {
        nameOfParts.clear();
        description.clear();
        cost.clear();
       
    }
    
    /**
     * This method checks if the user entered any white spaces at the front of the text.
     * If there is a white space in front of the text the user is informed not to have white space at the start of the fields.
     * @return 
     */
    public boolean checkForWhiteSpace()
    {
        if(nameOfParts.getText().trim().isEmpty() || description.getText().trim().isEmpty())
         {
             alertError("You cannot have a white space at the start of the fields.");
             return false;
         }
        return true;
    }
    
    /**
     * This method clears all the fields in the add item to stock section.
     */
    public void clearItemFields(){
        partIdCombo.setValue(null);
        quantity.clear();
        arrivalDate.setValue(null);
        
        
    }
    
    /**
     * This method is used to update the user with an information.
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
     * This method is used to show any errors occurred while running the system.
     * @param error 
     */
    public void alertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
}
