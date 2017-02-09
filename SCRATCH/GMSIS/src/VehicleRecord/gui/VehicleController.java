/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.gui;

import Authentication.Home;
import VehicleRecord.logic.Vehicle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VehicleController implements Initializable {

    ObservableList<String> vehicleBox = FXCollections.observableArrayList("Car","Van","Truck");
    ObservableList<String> quickSelection = FXCollections.observableArrayList("Honda Jazz", "Ford Focus", "Audi A5", "Toyota Yaris", "Vauxhall Corsa");
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
    private Button queryList;
    @FXML
    private Button searchVehicles;
    @FXML
    private Button backBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
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
    @FXML private TableColumn<Vehicle, Integer> vecIDCol;
    
    ObservableList<Vehicle> data;
   
    /**
     * Initializes the controller class.
     */
    @FXML
    private void backButton(ActionEvent event) throws IOException
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();           
        stage2.setScene(admin_Scene);
        stage2.show();
        
    }
    
    @FXML
    private void addVehicle(ActionEvent event) throws IOException, ClassNotFoundException
    {
        createData();
        System.out.println("Vehicle Added to Database");
        buildData();
        regNumber.clear();
        make.clear();
        model.clear();
        engSize.clear();
        colour.clear();
        mileage.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vehicleChoice.setItems(vehicleBox);
        quickSel.setItems(quickSelection);
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
    
    public void buildData(){        
    data = FXCollections.observableArrayList();
    Connection conn = null;
    try{      
        
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        System.out.println("Opened Database Successfully");
        String SQL = "Select * from VehicleList";            
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
            vec.vecID.set(rs.getInt("VehicleID"));
            data.add(vec);                  
        }
        table.setItems(data);
        rs.close();
        conn.close();
    }
    catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
    }
}   
    
     public void createData() throws ClassNotFoundException
    {
        
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            System.out.println("Opened Database Successfully");
            
            String sql = "insert into VehicleList(RegNumber,Make,Model,EngSize,FuelType,Colour,MOTDate,LastServiceDate,Mileage,VehicleType,Warranty,WarrantyNameAndAdd) values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
