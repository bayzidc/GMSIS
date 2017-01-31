/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AdminController implements Initializable {

    @FXML
    private Button createUser;
    @FXML
    private Button editUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Button logout;
    @FXML
    private Button vehicleButton;
    @FXML private TableView<Home> table;
    @FXML private TableColumn<Home, String> passCol;
    @FXML private TableColumn<Home, String> IDCol;
    @FXML private TableColumn<Home, String> firstnameCol;
    @FXML private TableColumn<Home, String> surnameCol;

    /**
     * Initializes the controller class.
     */
    @FXML
     private void createButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent new_User = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new_User_scene);
        stage.show();
    }
     @FXML
     private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent del_User = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
        Scene del_User_scene = new Scene(del_User);
        Stage delPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        delPage.setScene(del_User_scene);
        delPage.show();
    }
     @FXML
     private void editButton(ActionEvent event) throws IOException, ClassNotFoundException
     {
        Parent edit_User = FXMLLoader.load(getClass().getResource("EditUsers.fxml"));
        Scene edit_User_scene = new Scene(edit_User);
        Stage editPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editPage.setScene(edit_User_scene);
        editPage.show();
     }
     @FXML
     private void logoutButton(ActionEvent event) throws IOException, ClassNotFoundException
     {
        Parent logoutPage = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logout_Scene = new Scene(logoutPage);
        Stage stageLogout = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageLogout.setScene(logout_Scene);
        stageLogout.show();
     }
     
     @FXML
     private void vehicleBtn(ActionEvent event) throws IOException, ClassNotFoundException
     {
        Parent vehicle_Page = FXMLLoader.load(getClass().getResource("/VehicleRecord/VehRecord.fxml"));
        Scene vehicle_Scene = new Scene(vehicle_Page);
        Stage stageVehicle = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageVehicle.setScene(vehicle_Scene);
        stageVehicle.show();
     }
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        passCol.setCellValueFactory(
        new PropertyValueFactory<Home,String>("Password"));        
    IDCol.setCellValueFactory(                
        new PropertyValueFactory<Home,String>("ID"));
    firstnameCol.setCellValueFactory(
        new PropertyValueFactory<Home,String>("FirstName"));        
    surnameCol.setCellValueFactory(
        new PropertyValueFactory<Home,String>("Surname"));
    try{
        buildData();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}

        // TODO
    private ObservableList<Home> data;

    public void buildData(){        
    data = FXCollections.observableArrayList();
    Connection conn = null;
    try{      
        
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        System.out.println("Opened Database Successfully");
            
        String SQL = "Select * from Login";            
        ResultSet rs = conn.createStatement().executeQuery(SQL);  
        while(rs.next()){
            Home cm = new Home();
            cm.password.set(rs.getString("Password"));
            cm.ID.set(rs.getString("ID"));
            cm.firstName.set(rs.getString("FirstName"));
            cm.surname.set(rs.getString("Surname"));
            data.add(cm);                  
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
}
    

