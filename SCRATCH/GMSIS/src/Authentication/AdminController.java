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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button partsButton;
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private TextField id;
    @FXML
    private TextField oldPass;
    @FXML
    private TextField Pass;
    @FXML
    private TextField newPass;
    @FXML private TableView<Home> table;
    @FXML private TableColumn<Home, String> passCol;
    @FXML private TableColumn<Home, String> IDCol;
    @FXML private TableColumn<Home, String> firstnameCol;
    @FXML private TableColumn<Home, String> surnameCol;
    private ObservableList<Home> data;
    private IntegerProperty index = new SimpleIntegerProperty();
    
    

    /**
     * Initializes the controller class.
     */
    @FXML
     private void createButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        /*Parent new_User = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new_User_scene);
        stage.show();*/
        createData();
        buildData();
        JOptionPane.showMessageDialog(null,"Your unique User ID is " + getID());
        firstName.clear();
        surname.clear();
        newPass.clear();
    }
     @FXML
     private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        /*Parent del_User = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
        Scene del_User_scene = new Scene(del_User);
        Stage delPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        delPage.setScene(del_User_scene);
        delPage.show();*/
        String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this user? (Yes or No) ");
        if(confirmDelete.equalsIgnoreCase("Yes") && isDeleted())
        {
            
            String ID = table.getSelectionModel().getSelectedItem().getID(); //Gets ID 
            JOptionPane.showMessageDialog(null,"UserID: " + ID + " has been deleted"); 
            buildData();
        }
        
        
    }
     @FXML
     private void editButton(ActionEvent event) throws IOException, ClassNotFoundException
     {
        /*Parent edit_User = FXMLLoader.load(getClass().getResource("EditUsers.fxml"));
        Scene edit_User_scene = new Scene(edit_User);
        Stage editPage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editPage.setScene(edit_User_scene);
        editPage.show();*/
        editUser();
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
        Parent vehicle_Page = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
        Scene vehicle_Scene = new Scene(vehicle_Page);
        Stage stageVehicle = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageVehicle.setScene(vehicle_Scene);
        stageVehicle.show();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stageVehicle.setX(bounds.getMinX());
        stageVehicle.setY(bounds.getMinY());
        stageVehicle.setWidth(bounds.getWidth());
        stageVehicle.setHeight(bounds.getHeight());
        
     }
     
     @FXML
     private void partsButtn(ActionEvent event) throws IOException, ClassNotFoundException
     {
        Parent partsRecordPage = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        Scene parts_Scene = new Scene(partsRecordPage);
        Stage stageParts = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageParts.setScene(parts_Scene);
        stageParts.show();
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
    
    table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){
        @Override
        public void changed(ObservableValue<?> observable, Object oldValue, Object newValue){
            index.set(data.indexOf(newValue));
            System.out.println("Index on click is: " + data.indexOf(newValue));
           
        }
        
    });
    try{
        buildData();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}

    public void editUser() //print on text field
    {
        String ID = table.getSelectionModel().getSelectedItem().getID();
        String pass = table.getSelectionModel().getSelectedItem().getPassword();
        
        id.setText(ID);
        oldPass.setText(pass);
    }
        // TODO
    
    @FXML
    private void updatePass(ActionEvent event) throws IOException, ClassNotFoundException //update button
    {
        isEditForm();
        JOptionPane.showMessageDialog(null,"Updated");
        buildData();
        id.clear();
        oldPass.clear();
        Pass.clear();
    }
     private void isEditForm() throws ClassNotFoundException 
     {
        
        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE Login SET Password=? WHERE ID=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, Pass.getText());
            state.setString(2,id.getText());

            state.execute();

            state.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
     
     }
    
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
    public void createData() throws ClassNotFoundException
    {
        //boolean submit = false;
        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        System.out.println("SELECT * FROM Login WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND Password= " + "'" + newPass.getText() + "'");
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            System.out.println("Opened Database Successfully");
            
            String sql = "insert into Login(FirstName, Surname, Password) values(?,?,?)";
            //String sql = "insert into Login(Username,Password) values(?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, firstName.getText());
            state.setString(2,surname.getText());
            state.setString(3, newPass.getText());
            
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
   private String getID() throws ClassNotFoundException
    {
         Connection conn = null;
        
         String id = "";
         
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT * FROM Login WHERE FirstName= " + "'" + firstName.getText() + "'");
            while(rs.next())
            {
                 id = rs.getString("ID");
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
        return id;
        
    }
    
    /*public void deleteData()
    {   
        int i = index.get();
        if(i>-1)
        {
           data.remove(i);
           table.getSelectionModel().clearSelection();
        }
    }*/

    private boolean isDeleted() throws ClassNotFoundException
    {
        boolean userDeleted = false;
        
        String ID = table.getSelectionModel().getSelectedItem().getID();
        
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM Login WHERE ID= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, ID);
            state.executeUpdate();
            
            state.close();
            conn.close();
            
            userDeleted = true;
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return userDeleted;   
            
        }
    
    }
  

