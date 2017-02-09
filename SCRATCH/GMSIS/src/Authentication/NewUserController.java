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
import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NewUserController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private TextField newPassword;
    @FXML
    private Button submitBtn;

    @FXML
    private void submitButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent new_User = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if(isSubmitForm())
        {
            System.out.println("Added to database");
            
            //Alert alert = new Alert(AlertType.INFORMATION);
            //alert.setTitle("Information");
            //alert.setHeaderText("null");
            //alert.setContentText("Your unique User ID is " + getID());
            //alert.showAndWait();
            
            JOptionPane.showMessageDialog(null,"Your unique User ID is " + getID());
            
            stage.hide();           
            stage.setScene(new_User_scene);
            stage.show();
        }
        else
        {
            firstName.clear();
            surname.clear();
            titleLabel.setText("Invalid Inputs.");
        }
    }
    @FXML
    private boolean isSubmitForm() throws ClassNotFoundException
    {
        boolean submit = false;
        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        System.out.println("SELECT * FROM Login WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND Password= " + "'" + newPassword.getText() + "'");
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
            state.setString(3, newPassword.getText());
            
            state.execute();
            
            state.close();
            conn.close();
            
            submit=true;
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return submit;       
            
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}