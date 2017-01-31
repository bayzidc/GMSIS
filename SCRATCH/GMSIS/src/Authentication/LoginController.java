/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController implements Initializable {

    @FXML
    private Label titleID;
    @FXML
    private TextField username;
    @FXML
    private PasswordField pass;
    @FXML
    private Button loginBtn;
    @FXML
    private Button newBtn;

    @FXML 
    private void loginButton(ActionEvent event) throws IOException
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Parent customer = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene customer_Scene = new Scene(customer);
        Stage stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        
        if(isValidLogin() && username.getText().equals("10000") && pass.getText().equals("pass1"))
        {
            stage2.hide();           
            stage2.setScene(admin_Scene);
            stage2.show();
        }
        
        else if(isValidLogin())
        {
            stage3.hide();
            stage3.setScene(customer_Scene);
            stage3.show();
         
        }
        else
        {
            username.clear();
            pass.clear();
            JOptionPane.showMessageDialog(null,"Sorry,Invalid Username or password.");
        }
    }
           
    public boolean isValidLogin()
    {
        boolean loggedIn = false;
        System.out.println("SELECT * FROM Login WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
        
        Connection conn = null;
        
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);
            
            System.out.println("Opened Database Successfully");
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT * FROM Login WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
            while(rs.next())
            {
                if(rs.getString("ID") !=null && rs.getString("Password") !=null)
                        {
                            String userID = rs.getString("ID");
                            String password = rs.getString("Password");
                            loggedIn = true;
                        }
            }
            rs.close();
            state.close();
            conn.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return loggedIn;
        
            
            
            
        }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
