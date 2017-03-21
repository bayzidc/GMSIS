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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController implements Initializable {
    
    public static boolean isAdmin = false;
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
    private Hyperlink forgotPass;
    @FXML 
    private void loginButton(ActionEvent event) throws IOException
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/common/gui/common.fxml"));
        //Parent adminUser = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        stage2.setResizable(false);
            stage2.setHeight(810);
            stage2.setWidth(1359);
        
        Parent customer = FXMLLoader.load(getClass().getResource("/customer/gui/customer.fxml"));
        Scene customer_Scene = new Scene(customer);
        Stage stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        
        stage3.setResizable(false);
            stage3.setHeight(810);
            stage3.setWidth(1359);
            
        
        if(isValidLogin() && isAdmin())
        {
            stage2.hide();           
            stage2.setScene(admin_Scene);
            stage2.show();
            isAdmin = true;
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
            Alert alert = new Alert(AlertType.ERROR); // Pop up box
	    alert.setTitle("Error");
	    alert.setHeaderText("Invalid Username or password.");
            alert.showAndWait();
        }
    }
   
    public boolean isValidLogin()
    {
        boolean loggedIn = false;
        System.out.println("SELECT * FROM User WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
        
        Connection conn = null;
        
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);
            
            System.out.println("Opened Database Successfully");
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT * FROM User WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
            while(rs.next())
            {
                if(rs.getString("ID") !=null && rs.getString("Password") !=null)
                        {
                            loggedIn = true;
                            break;
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
    
    public boolean isAdmin()
    {
        boolean admin = false;
         Connection conn = null;
        
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT Admin FROM User WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
            while(rs.next())
            {
                if(rs.getBoolean("Admin"))
                        {
                            admin = true;
                            break;
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
       return admin;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        forgotPass.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            username.clear();
            pass.clear();
            Alert alert = new Alert(AlertType.INFORMATION); // Pop up box
	    alert.setTitle("Information");
	    alert.setHeaderText("If you have forgotten your password, please contact an administrator so that they can change your password.");
            alert.showAndWait();
    }
});
        
        
        pass.setOnKeyPressed(ev -> {
            try {
                Parent adminUser = FXMLLoader.load(getClass().getResource("/common/gui/common.fxml"));
                //Parent adminUser = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                Scene admin_Scene = new Scene(adminUser);
                Stage stage2 = (Stage) ((Node)ev.getSource()).getScene().getWindow();
                
                stage2.setResizable(false);
                stage2.setHeight(810);
                stage2.setWidth(1359);
                
                Parent customer = FXMLLoader.load(getClass().getResource("/customer/gui/customer.fxml"));
                Scene customer_Scene = new Scene(customer);
                Stage stage3 = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                
                
                stage3.setResizable(false);
                stage3.setHeight(810);
                stage3.setWidth(1359);
                if(ev.getCode() == KeyCode.ENTER){
                    
                    boolean loggedIn = false;
                    System.out.println("SELECT * FROM User WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
                    
                    Connection conn = null;
                    
                    java.sql.Statement state = null;
                    try
                    {
                        conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                        conn.setAutoCommit(false);
                        
                        System.out.println("Opened Database Successfully");
                        state = conn.createStatement();
                        
                        ResultSet rs = state.executeQuery("SELECT * FROM User WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
                        while(rs.next())
                        {
                            if(rs.getString("ID") !=null && rs.getString("Password") !=null)
                            {
                                loggedIn = true;
                                break;
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
                    
                    
                    boolean admin = false;
                    Connection conn2 = null;
                    
                    java.sql.Statement state2 = null;
                    try
                    {
                        conn2 = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                        conn2.setAutoCommit(false);
                        
                        state2 = conn2.createStatement();
                        
                        ResultSet rs = state2.executeQuery("SELECT Admin FROM User WHERE ID= " + "'" + username.getText() + "'" + "AND Password= " + "'" + pass.getText() + "'");
                        while(rs.next())
                        {
                            if(rs.getBoolean("Admin"))
                            {
                                admin = true;
                                break;
                            }
                        }
                        rs.close();
                        state2.close();
                        conn2.close();
                    }
                    catch(Exception e)
                    {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                    
                    if(admin == true && loggedIn == true)
                    {
                        stage2.hide();
                        stage2.setScene(admin_Scene);
                        stage2.show();
                        isAdmin = true;
                    }
                    
                    else if(loggedIn == true)
                    {
                        stage3.hide();
                        stage3.setScene(customer_Scene);
                        stage3.show();
                    }
                    
                    else
                    {
                        username.clear();
                        pass.clear();
                        Alert alert = new Alert(AlertType.ERROR); // Pop up box
	                alert.setTitle("Error");
	                alert.setHeaderText("Invalid Username or password.");
                        alert.showAndWait();
                    }
                }   } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        

    });
        
    } 
    
    
    
}
