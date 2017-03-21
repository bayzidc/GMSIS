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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AdminController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Button customerButton;
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
    @FXML
    private Button repairButton;
    @FXML
    private Button partsButtonUsed;
    @FXML
    private Button partsButtonStock;
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private TextField id;
    @FXML
    private TextField newPass;
    @FXML
    private CheckBox admin;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> passCol;
    @FXML
    private TableColumn<User, String> IDCol;
    @FXML
    private TableColumn<User, String> firstnameCol;
    @FXML
    private TableColumn<User, String> surnameCol;
    @FXML
    private TableColumn<User, String> adminCol;
    
    
    private ObservableList<User> data;
    private IntegerProperty index = new SimpleIntegerProperty();

    /**
     * Initializes the controller class.
     */
    
    
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
    
    private boolean allCompleted()
    {
        if(!firstName.equals("") && !surname.equals("") && !newPass.equals(""))
        {
            return true;
        }
        return false;
    }
    
    @FXML
    private void createButton(ActionEvent event) throws IOException, ClassNotFoundException {
        /*Parent new_User = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new_User_scene);
        stage.show();*/
        
        if(!allCompleted())
        {
            alertError(null,"Please complete all fields");
            return;
        }
        
        createData();
        buildData();

       
            alertInfo(null,"Your unique User ID is " + getID());

        id.clear();
        firstName.clear();
        surname.clear();
        newPass.clear();
        admin.setSelected(false);

    }
    
    @FXML
    private void clearAllButton(ActionEvent event)
    {
        id.clear();
        firstName.clear();
        surname.clear();
        newPass.clear();
        admin.setSelected(false);
    }

    @FXML
    private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        /*Parent del_User = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
        Scene del_User_scene = new Scene(del_User);
        Stage delPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        delPage.setScene(del_User_scene);
        delPage.show();*/
        
        if(table.getSelectionModel().getSelectedItem()==null)
        {
            alertError(null,"Please select a row");
            return;
        }
        
        
        Optional<ButtonType> confirmDelete = alertConfirm("Are you sure you want to delete this user?");
        if (confirmDelete.get()==ButtonType.OK && isDeleted()) {

            String ID = table.getSelectionModel().getSelectedItem().getID(); //Gets ID 
            alertInfo(null,"UserID: " + ID + " has been deleted");
            buildData();
        }

    }

  
    @FXML
    private void logoutButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent logoutPage = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene logout_Scene = new Scene(logoutPage);
        Stage stageLogout = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageLogout.setScene(logout_Scene);
        stageLogout.show();

     }
     
     

    @FXML
    private void vehicleBtn(ActionEvent event) throws IOException, ClassNotFoundException {

        Parent vehicle_Page = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
        Scene vehicle_Scene = new Scene(vehicle_Page);
        Stage stageVehicle = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageVehicle.setScene(vehicle_Scene);
        stageVehicle.show();
     }
      
    @FXML
    private void customerButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent logoutPage = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/gui.fxml"));
        Scene logout_Scene = new Scene(logoutPage);
        Stage stageLogout = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageLogout.setScene(logout_Scene);
        stageLogout.show();
    }

    @FXML
    private void repairButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent repairPage = FXMLLoader.load(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));
        Scene repair_Scene = new Scene(repairPage);
        Stage stageRepair = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageRepair.setScene(repair_Scene);
        stageRepair.show();
    }
    
    @FXML
    private void partsButtnUsed(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent partsRecordPage = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        Scene parts_Scene = new Scene(partsRecordPage);
        Stage stageParts = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageParts.setScene(parts_Scene);
        stageParts.show();
    }
    
    @FXML
    private void partsButtnStock(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent partsStockPage = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/partStock.fxml"));
        Scene partStock_Scene = new Scene(partsStockPage);
        Stage stageParts = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageParts.setScene(partStock_Scene);
        stageParts.show();
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        id.setEditable(false);
        
        
        passCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("Password"));
        IDCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("ID"));
        firstnameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("FirstName"));
        surnameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("Surname"));
        adminCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("Admin"));



        try {
            buildData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 

    
    @FXML
    public void editUser(ActionEvent event) //print on text field
    {

        if(table.getSelectionModel().getSelectedItem()==null)
        {
            alertError(null,"Please select a row");
            return;
        }
        
        
         id.setText(table.getSelectionModel().getSelectedItem().getID());
         firstName.setText(table.getSelectionModel().getSelectedItem().getFirstName());
         surname.setText(table.getSelectionModel().getSelectedItem().getSurname());
         newPass.setText(table.getSelectionModel().getSelectedItem().getPassword());
       
        if(table.getSelectionModel().getSelectedItem().getAdmin().equals("1"))
        {
            admin.setSelected(true);
        }
        else
        {
            admin.setSelected(false);
        }
 
        
    }
    // TODO

    @FXML
    private void update(ActionEvent event) throws IOException, ClassNotFoundException //update button
    {
        if(!allCompleted())
        {
            alertError(null,"Please complete all fields");
            return;
        }
        
        
        updateData();
   
        id.clear();
        firstName.clear();
        surname.clear();
        newPass.clear();
        admin.setSelected(false);
        
        buildData();
        
    }

    private void updateData() throws ClassNotFoundException {

        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE User SET FirstName=?, Surname=?, Password=?, Admin=? WHERE ID=?";
            PreparedStatement state = conn.prepareStatement(sql);
       
            state.setString(1, firstName.getText());
            state.setString(2, surname.getText());
            state.setString(3, newPass.getText());
            
            if(admin.isSelected())
            {
                state.setBoolean(4, true);
            }
            else
            {
                state.setBoolean(4, false);
            }
            
           
            
            state.setString(5, id.getText());

            state.execute();

            state.close();
            conn.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
       

    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");

            String SQL = "Select * from User";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                User cm = new User();
                cm.password.set(rs.getString("Password"));
                cm.ID.set(rs.getString("ID"));
                cm.firstName.set(rs.getString("FirstName"));
                cm.surname.set(rs.getString("Surname"));
                cm.admin.set(rs.getString("Admin"));
                data.add(cm);
            }
            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void createData() throws ClassNotFoundException {
        //boolean submit = false;
        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        System.out.println("SELECT * FROM User WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND Password= " + "'" + newPass.getText() + "'");
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "insert into User(FirstName, Surname, Password, Admin) values(?,?,?,?)";
            //String sql = "insert into Login(Username,Password) values(?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, firstName.getText());
            state.setString(2, surname.getText());
            state.setString(3, newPass.getText());

            if (admin.isSelected()) {
                state.setBoolean(4, true);
            } else {
                state.setBoolean(4, false);
            }
            
           

            state.execute();

            state.close();
            conn.close();

            //submit=true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
        //return submit;       

    }

    private String getID() throws ClassNotFoundException {
        Connection conn = null;

        String id = "";

        java.sql.Statement state = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);

            state = conn.createStatement();

            ResultSet rs = state.executeQuery("SELECT * FROM User WHERE FirstName= " + "'" + firstName.getText() + "'");
            while (rs.next()) {
                id = rs.getString("ID");
            }
            rs.close();
            state.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
        return id;

    }

    private boolean isDeleted() throws ClassNotFoundException {
        boolean userDeleted = false;

        String ID = table.getSelectionModel().getSelectedItem().getID();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM User WHERE ID= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, ID);
            state.executeUpdate();

            state.close();
            conn.close();

            userDeleted = true;

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
        }
        return userDeleted;

    }

    private void alertInfo(String header, String information) 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(information);
        alert.showAndWait();
    }

    private void alertError(String header, String error) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(error);
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
