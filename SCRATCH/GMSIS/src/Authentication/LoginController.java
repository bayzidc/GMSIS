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
    private void handleButton(ActionEvent event) throws IOException
    {
        Parent new_User = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(new_User_scene);
        stage.show();
    }
    
    @FXML 
    private void loginButton(ActionEvent event) throws IOException
    {
        Parent new_User = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if(isValidLogin() && pass.getText().equals("password1"))
        {
            stage.hide();           
            stage.setScene(new_User_scene);
            stage.show();
        }
        else
        {
            username.clear();
            pass.clear();
            JOptionPane.showMessageDialog(null,"Sorry,Invalid userID or password.");
        }
    }
           
    private boolean isValidLogin()
    {
        boolean loggedIn = false;
        System.out.println("SELECT * FROM Login WHERE UserID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
        
        Connection conn = null;
        
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);
            
            System.out.println("Opened Database Successfully");
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT * FROM Login WHERE UserID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
            while(rs.next())
            {
                if(rs.getString("UserID") !=null && rs.getString("Password") !=null)
                        {
                            String userID = rs.getString("UserID");
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