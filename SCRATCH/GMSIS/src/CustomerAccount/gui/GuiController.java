/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.gui;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import CustomerAccount.logic.customerAccount;

/**
 * FXML Controller class
 *
 * @author Joen
 */
public class GuiController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button bookingButton;
    @FXML
    private Button billsButton;
    @FXML
    private TextField fullNameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField postCodeText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView<customerAccount> table;
    @FXML
    private TableColumn<customerAccount, Integer> customerID;
    @FXML
    private TableColumn<customerAccount, String> customerFullName;
    @FXML
    private TableColumn<customerAccount, String> customerAddress;
    @FXML
    private TableColumn<customerAccount, String> customerPostCode;
    @FXML
    private TableColumn<customerAccount, Integer> customerPhone;
    @FXML
    private TableColumn<customerAccount, String> customerEmail;
    @FXML
    private TableColumn<customerAccount, String> customerType;
    private ObservableList<customerAccount> data;
    private IntegerProperty index = new SimpleIntegerProperty();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("Running this. BUILD DATA");
            buildData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");

            String SQL = "Select * from customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new customerAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
            }
            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        customerID.setCellValueFactory(
                new PropertyValueFactory<>("customerID"));
        customerFullName.setCellValueFactory(
                new PropertyValueFactory<>("customerFullName"));
        customerAddress.setCellValueFactory(
                new PropertyValueFactory<>("customerAddress"));
        customerPostCode.setCellValueFactory(
                new PropertyValueFactory<>("customerPostCode"));
        customerPhone.setCellValueFactory(
                new PropertyValueFactory<>("customerPhone"));
        customerEmail.setCellValueFactory(
                new PropertyValueFactory<>("customerEmail"));
        customerType.setCellValueFactory(
                new PropertyValueFactory<>("customerType"));
    }

}
