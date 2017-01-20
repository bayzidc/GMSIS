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

/**
 * FXML Controller class
 *
 * @author User
 */
public class NewUserController implements Initializable {
    String firstN = "";
    String surn = "";
    String newID = "";

    @FXML
    private Label titleLabel;
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private TextField newUserID;
    @FXML
    private Button submitBtn;

    @FXML
    private void submitButton(ActionEvent event) throws IOException, ClassNotFoundException
    {
        Parent new_User = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene new_User_scene = new Scene(new_User);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if(isSubmitForm(firstN,surn,newID))
        {
            System.out.println("Added to database");
            stage.hide();           
            stage.setScene(new_User_scene);
            stage.show();
        }
        else
        {
            firstName.clear();
            surname.clear();
            newUserID.clear();
            titleLabel.setText("Invalid Inputs.");
        }
    }
    @FXML
    private boolean isSubmitForm(String firstN, String surn, String newID) throws ClassNotFoundException
    {
        boolean submit = false;
        System.out.println("SELECT * FROM newUser WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        
        Connection conn = null;
        
        PreparedStatement state = null;
        ResultSet rs = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:NewUsers.sqlite");
            conn.setAutoCommit(false);
            
            System.out.println("Opened Database Successfully");
            String sql = ("INSERT INTO newUser(FirstName,Surname,UserID) VALUES(?,?,?)");
            state = conn.prepareStatement(sql);
            state.setString(1, firstName.getText());
            state.setString(2, surname.getText());
            state.setString(3, newUserID.getText());
            
            rs = state.executeQuery();
            
            state.close();
            rs.close();
            conn.close();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return submit;
        
            
            
            
        }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
