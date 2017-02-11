/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.gui;

import Authentication.sqlite;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VehicleController implements Initializable {

    ObservableList<String> vehicleBox = FXCollections.observableArrayList("Car","Van","Truck");
    ObservableList<String> quickSelection = FXCollections.observableArrayList();
    ObservableList<String> fuelT = FXCollections.observableArrayList("Petrol","Diesel");
    ObservableList<String> warrantyChoice = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox vehicleChoice;
    @FXML
    private CheckBox yesWarranty;
    
    @FXML
    private CheckBox noWarranty;
   
    @FXML
    private TextArea nameAndAdd;
    @FXML
    private ChoiceBox quickSel;
    @FXML
    private TextField regNumber;
    @FXML
    private TextField make;
    @FXML
    private TextField model;
    @FXML
    private TextField engSize;
    @FXML
    private ChoiceBox fuelType;
    @FXML
    private TextField colour;
    @FXML
    private DatePicker motRenDate;
    @FXML
    private DatePicker lastService;
    @FXML
    private TextField mileage;
    @FXML
    private TextField id;
    @FXML
    private DatePicker warExpiry;
    @FXML
    private Button queryList;
    @FXML
    private Button backBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField searchVehicle;
    @FXML 
    private Button queryParts;
    @FXML
    private Button clearBtn;
    @FXML
    private ScrollBar scrollRight;
    @FXML private TableView<Vehicle> table;
    @FXML private TableColumn<Vehicle, String> regCol;
    @FXML private TableColumn<Vehicle, String> makeCol;
    @FXML private TableColumn<Vehicle, String> modelCol;
    @FXML private TableColumn<Vehicle, Double> engSizeCol;
    @FXML private TableColumn<Vehicle, String> fuelTypeCol;
    @FXML private TableColumn<Vehicle, String> colourCol;
    @FXML private TableColumn<Vehicle, String> motRenewalCol;
    @FXML private TableColumn<Vehicle, String> lastServiceCol;
    @FXML private TableColumn<Vehicle, Integer> mileageCol;
    @FXML private TableColumn<Vehicle, String> vehicleTCol;
    @FXML private TableColumn<Vehicle, String> warrantyCol;
    @FXML private TableColumn<Vehicle, String> nameAndAddCol;
    @FXML private TableColumn<Vehicle, String> warExpDateCol;
    @FXML private TableColumn<Vehicle, Integer> vecIDCol;
    
    ObservableList<Vehicle> data;
   
    /**
     * Initializes the controller class.
     */
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
    private void clearButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        fuelType.setValue(null);
        colour.clear();
        motRenDate.setValue(null);
        motRenDate.getEditor().setText(null);
        lastService.setValue(null);
        lastService.getEditor().setText(null);
        mileage.clear();
        vehicleChoice.setValue(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
    }
    
    @FXML
    private void addVehicle(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to add vehicle
    {
        createData();
        System.out.println("Vehicle Added to Database");
        buildData();
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        fuelType.setValue(null);
        colour.clear();
        motRenDate.setValue(null);
        motRenDate.getEditor().setText(null);
        lastService.setValue(null);
        lastService.getEditor().setText(null);
        mileage.clear();
        vehicleChoice.setValue(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
        
    }
    
    @FXML void showButton(ActionEvent e) throws IOException, ClassNotFoundException // method to show vehicle details on textfield
     {
         showVecOnText();
     }
     @FXML
     private void editButton(ActionEvent e)throws IOException, ClassNotFoundException, SQLException
     {
        editVehicle();
        System.out.println("Edited on db");
        JOptionPane.showMessageDialog(null,"Updated");
        buildData();
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        fuelType.setValue(null);
        colour.clear();
        mileage.clear();
        vehicleChoice.setValue(null);
        motRenDate.setValue(null);
        motRenDate.getEditor().setText(null);
        lastService.setValue(null);
        lastService.getEditor().setText(null);
        yesWarranty.setSelected(false);
        noWarranty.setSelected(false);
        nameAndAdd.clear();
        warExpiry.setValue(null);
        warExpiry.getEditor().setText(null);
        id.clear();
     }
    @FXML
     private void deleteVehicle(ActionEvent event) throws IOException, ClassNotFoundException, SQLException // button method to delete vehicle
    {
        String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this vehicle? (Yes or No) ");
        int id = table.getSelectionModel().getSelectedItem().getVecID();
        if(confirmDelete.equalsIgnoreCase("Yes") && isVehicleDeleted())
        {
             
            JOptionPane.showMessageDialog(null, "VehicleID: " + id + " has been deleted."); 
            buildData();
        }
        
        else if(id ==0)
        {
            JOptionPane.showMessageDialog(null,"Vehicle does not exist.");
        }
        
        else
        {
            JOptionPane.showMessageDialog(null,"Error deleting vehicle.");
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vehicleChoice.setItems(vehicleBox);
        fuelType.setItems(fuelT);
        
        regCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("RegNumber"));        
    makeCol.setCellValueFactory(                
        new PropertyValueFactory<Vehicle,String>("Make"));
    modelCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("Model"));        
    engSizeCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,Double>("EngSize"));
    fuelTypeCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("FuelType"));        
    colourCol.setCellValueFactory(                
        new PropertyValueFactory<Vehicle,String>("Colour"));
    motRenewalCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("MotRenewal"));        
    lastServiceCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("LastService"));
    mileageCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,Integer>("Mileage"));
    vehicleTCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("VehicleType"));        
    warrantyCol.setCellValueFactory(                
        new PropertyValueFactory<Vehicle,String>("Warranty"));
    nameAndAddCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String>("WarNameAndAdd"));   
    warExpDateCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,String> ("WarrantyExpDate"));
    vecIDCol.setCellValueFactory(
        new PropertyValueFactory<Vehicle,Integer>("VecID"));
    
    
    
    
     try{
        buildData();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    }    
    

    
    public void buildData() throws ClassNotFoundException, SQLException{        
    data = FXCollections.observableArrayList();
    Connection conn = null;
    try{      
        
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        System.out.println("Opened Database Successfully");
        String SQL = "Select * from vehicleList";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next()){
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
            data.add(vec);
            
            FilteredList<Vehicle> filteredData = new FilteredList<>(data, e->true);
    searchVehicle.setOnKeyReleased(e ->{
        searchVehicle.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Vehicle>) vehicle -> {
                if(newValue ==null || newValue.isEmpty())
                {
                    return true;
                }
                String newValLow = newValue.toLowerCase();
                if(vehicle.getRegNumber().toLowerCase().contains(newValLow))
                {
                    return true;
                }
                
                else if(vehicle.getMake().toLowerCase().contains(newValLow))
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
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
    fillQuickSelection();

} 
    
     public void createData() throws ClassNotFoundException
    {
        
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            
            String sql = "insert into vehicleList(RegNumber,Make,Model,EngSize,FuelType,Colour,MOTDate,LastServiceDate,Mileage,VehicleType,Warranty,WarrantyNameAndAdd,WarrantyExpDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //String sql = "insert into Login(Username,Password) values(?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, regNumber.getText());
            state.setString(2,make.getText());
            state.setString(3, model.getText());
            state.setString(4, engSize.getText());
            state.setString(5, (String) fuelType.getValue());
            state.setString(6, colour.getText());
            state.setString(7, ((TextField) motRenDate.getEditor()).getText());
            state.setString(8, ((TextField) lastService.getEditor()).getText());
            state.setString(9, mileage.getText());
            state.setString(10, (String) vehicleChoice.getValue());
            state.setString(11, warrantyChoice.toString());
            state.setString(12, nameAndAdd.getText());
            state.setString(13, ((TextField) warExpiry.getEditor()).getText());
            state.execute();
            
            state.close();
            conn.close();
            
            //submit=true;
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        //return submit;       
            
        }
    
     
    private void showVecOnText()
    {
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
        
        regNumber.setText(regN);
        make.setText(vecMake);
        model.setText(vecModel);
        engSize.setText(String.valueOf(engine));
        fuelType.setValue(ft);
        colour.setText(col);
        mileage.setText(String.valueOf(mil));
        vehicleChoice.setValue(vecType);
        motRenDate.getEditor().setText(mot);
        lastService.getEditor().setText(ls);
        nameAndAdd.setText(wNameAndAdd);
        warExpiry.getEditor().setText(warDate);
        id.setText(String.valueOf(ID));
        
        
    }
     
     private void editVehicle() throws ClassNotFoundException
     {
         Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehicleList SET RegNumber=?,Make=?,Model=?,EngSize=?,FuelType=?,Colour=?,MOTDate=?, LastServiceDate=?,Mileage=?,VehicleType=?,Warranty=?,WarrantyNameAndAdd=?,WarrantyExpDate=? WHERE vehicleID=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, regNumber.getText());
            state.setString(2,make.getText());
            state.setString(3, model.getText());
            state.setString(4, engSize.getText());
            state.setString(5, (String) fuelType.getValue());
            state.setString(6, colour.getText());
            state.setString(7, ((TextField) motRenDate.getEditor()).getText());
            state.setString(8, ((TextField) lastService.getEditor()).getText());
            state.setString(9, mileage.getText());
            state.setString(10, (String) vehicleChoice.getValue());
            state.setString(11, warrantyChoice.toString());
            state.setString(12, nameAndAdd.getText());
            state.setString(13, ((TextField) warExpiry.getEditor()).getText());
            state.setString(14, id.getText());

            state.execute();

            state.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
     }
     
     private boolean isVehicleDeleted() throws ClassNotFoundException
    {
        boolean vecDeleted = false;
        
        int ID = table.getSelectionModel().getSelectedItem().getVecID();
        
        Connection conn = null;
        
        try
        {
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
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return vecDeleted;   
            
        }
    
    
     private int getVehicleID() throws ClassNotFoundException
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
        
    }
     
     public void fillQuickSelection() throws ClassNotFoundException
    {
        Connection conn=null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");
            String query = "Select Make from vehicleList";

            ResultSet rs = conn.createStatement().executeQuery(query);
            
            
            while(rs.next())
            {
                quickSelection.add(rs.getString("Make"));
                quickSel.setItems(quickSelection);
                
            }

            rs.close();
            conn.close();
        }
        
        catch(SQLException e)
        {
            
        }
    }
     private void fillTextQuickSel() throws SQLException, ClassNotFoundException
     {
        
     }
     
    @FXML
    private void checkBox1(MouseEvent e)
    {
        warrantyChoice.add(yesWarranty.getText());
    }
    @FXML
    private void checkBox2(MouseEvent e)
    {
        warrantyChoice.add(noWarranty.getText());
    }
    
    @FXML
    private void handleYesBox()
    {
        if(yesWarranty.isSelected())
        {
            noWarranty.setSelected(false); 
        }
    }
    @FXML
    private void handleNoBox()
    {
        if(noWarranty.isSelected())
        {
            yesWarranty.setSelected(false);
        }
    }
  
}
