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
public class EditUsersController implements Initializable {

    @FXML
    private Label currentUser;
    @FXML
    private Label editLabel;
    @FXML
    private Label newUserLabel;
    @FXML
    private Label newPassLabel;
    @FXML
    private TextField newUserID;
    @FXML
    private TextField newPass;
    @FXML
    private TextField currentText;
    @FXML
    private Button submitBtn;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void editButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent adminUser = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (isEditForm()) {
            System.out.println("Edited User");
            stage2.hide();
            stage2.setScene(admin_Scene);
            stage2.show();
        } else {
            newUserID.clear();
            newPass.clear();
        }

    }

    private boolean isEditForm() throws ClassNotFoundException {
        boolean edit = false;
        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            //String sql = "insert into Login(FirstName,Surname,UserID) values(?,?,?)";
            String sql = "UPDATE Login SET Username=?, Password=? WHERE Username=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, newUserID.getText());
            state.setString(2, newPass.getText());
            state.setString(3, currentText.getText());
            //state.setString(1, newUserID.getText());
            //state.setString(2, newPassword.getText());

            state.execute();

            state.close();
            conn.close();

            edit = true;

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return edit;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
