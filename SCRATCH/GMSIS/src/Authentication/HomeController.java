/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Home> table;
    @FXML private TableColumn<Home, String> passCol;
    @FXML private TableColumn<Home, String> IDCol;
    @FXML private TableColumn<Home, String> firstnameCol;
    @FXML private TableColumn<Home, String> surnameCol;
    
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
