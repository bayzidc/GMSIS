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
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DeleteUserController implements Initializable {

    @FXML
    private Label delLabel;
    @FXML
    private TextField userID;
    @FXML
    private Button delBtn;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void deleteUser(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent adminUser = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this user? (Yes or No) ");
        if(confirmDelete.equalsIgnoreCase("Yes") && isDeleted())
        {
            JOptionPane.showMessageDialog(null, userID.getText() + " has been deleted");
            stage2.hide();           
            stage2.setScene(admin_Scene);
            stage2.show();           
        }
        
        else
        {
            stage2.hide();           
            stage2.setScene(admin_Scene);
            stage2.show();
        }
    }
    
    private boolean isDeleted() throws ClassNotFoundException
    {
        boolean userDeleted = false;
        
        if(!contains())
        {
            JOptionPane.showMessageDialog(null,"The Username " + userID.getText() + " does not exist");
            return false;
        }
        
        System.out.println("SELECT * FROM Login WHERE ID= " + "'" + userID.getText() + "'");
        Connection conn = null;
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            
            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM Login WHERE ID= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, userID.getText());
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

    private boolean contains()
    {
        boolean check = false;
        
        Connection conn = null;
        
        java.sql.Statement state = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            conn.setAutoCommit(false);
            
            state = conn.createStatement();
            
            ResultSet rs = state.executeQuery("SELECT * FROM Login WHERE ID= " + "'" + userID.getText() + "'");
            while(rs.next())
            {
                if(rs.getString("ID").equals(userID.getText()))
                        {  
                            check = true;
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
        return check;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
