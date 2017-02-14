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
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Joen
 */
public class GuiController implements Initializable {

    @FXML
    private Button clearButton;
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
    private ChoiceBox accTypeText;
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
    public int ID;
    private IntegerProperty index = new SimpleIntegerProperty();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("Running this. BUILD DATA");
            accTypeText.setItems(FXCollections.observableArrayList("Business", "Private"));
            accTypeText.getSelectionModel().selectFirst();
            buildData();

            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {
                        if (table.getSelectionModel().getSelectedItem() != null) {
                            Connection conn = null;
                            ID = table.getSelectionModel().getSelectedItem().getCustomerID();
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                            System.out.println("Opened Database Successfully");

                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            ResultSet rs = state.executeQuery("SELECT * FROM customer WHERE customer_id= " + "'" + ID + "'");
                            while (rs.next()) {
                                ID = rs.getInt("customer_id");
                                fullNameText.setText(rs.getString("customer_fullname"));
                                addressText.setText(rs.getString("customer_address"));
                                postCodeText.setText(rs.getString("customer_postcode"));
                                phoneText.setText(String.valueOf(rs.getInt("customer_phone")));
                                emailText.setText(rs.getString("customer_email"));
                                if (rs.getString("customer_type").equals("Business")) {
                                    accTypeText.getSelectionModel().selectFirst();
                                } else {
                                    accTypeText.getSelectionModel().selectLast();
                                }
                            }
                            state.close();
                            conn.close();
                        }
                    } catch (Exception e) {
                        System.out.println("Here 1.");
                        alertError();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Here 2.");
            alertError();
        }
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException, ClassNotFoundException {
        createData();
        buildData();
    }

    @FXML
    private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this user? (Yes or No) ");
            if (confirmDelete.equalsIgnoreCase("Yes") && isDeleted()) {
                int ID = table.getSelectionModel().getSelectedItem().getCustomerID(); //Gets ID 
                JOptionPane.showMessageDialog(null, "UserID: " + ID + " has been deleted");
                buildData();
            }
        } catch (Exception e) {
            System.out.println("Here 3.");
            alertInf();
        }
    }

    @FXML
    private void updateButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            int check = Integer.parseInt(phoneText.getText());
            if (fullNameText.getText().equals("") || addressText.getText().equals("") || postCodeText.getText().equals("") || check < 1 || emailText.getText().equals("")) {
                alertInf();
            }
            String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to update this user? (Yes or No) ");
            if (confirmDelete.equalsIgnoreCase("Yes")) {
                updateData(); //Gets ID 
                JOptionPane.showMessageDialog(null, "UserID: " + ID + " has been updated");
                buildData();
            }
        } catch (Exception e) {
            System.out.println("Here 4.");
            alertError();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private boolean isDeleted() throws ClassNotFoundException {
        boolean customerDeleted = false;

        int ID = table.getSelectionModel().getSelectedItem().getCustomerID();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM customer WHERE customer_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, ID);
            state.executeUpdate();

            state.close();
            conn.close();

            customerDeleted = true;
            clearDetails();
        } catch (Exception e) {
            alertInf();
            System.out.println("Here 5.");
        }
        return customerDeleted;

    }

    @FXML
    private void updateData() throws ClassNotFoundException {

        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            int ID = table.getSelectionModel().getSelectedItem().getCustomerID();

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE customer SET customer_fullname=?,customer_address=?,customer_postcode=?,customer_phone=?,customer_email=?,customer_type=? WHERE customer_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            int check = Integer.parseInt(phoneText.getText());
            if (fullNameText.getText().equals("") || addressText.getText().equals("") || postCodeText.getText().equals("") || check < 1 || emailText.getText().equals("")) {
                alertInf();
            } else {
                state.setString(1, fullNameText.getText());
                state.setString(2, addressText.getText());
                state.setString(3, postCodeText.getText());
                state.setInt(4, Integer.parseInt(phoneText.getText()));
                state.setString(5, emailText.getText());
                state.setString(6, accTypeText.getSelectionModel().getSelectedItem().toString());
                state.setInt(7, ID);

                state.execute();

                state.close();
                conn.close();
                clearDetails();
            }//submit=true;
        } catch (Exception e) {
            alertInf();
            System.out.println("Here 6.");
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
            alertError();
            System.out.println("Here 7.");
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

    public void createData() throws ClassNotFoundException {

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Creating data.");

            String sql = "insert into customer(customer_fullname, customer_address, customer_postcode, customer_phone, customer_email, customer_type) values(?,?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            int check = Integer.parseInt(phoneText.getText());
            if (fullNameText.getText().equals("") || addressText.getText().equals("") || postCodeText.getText().equals("") || check < 1 || emailText.getText().equals("")) {
                alertInf();
            } else {
                state.setString(1, fullNameText.getText());
                state.setString(2, addressText.getText());
                state.setString(3, postCodeText.getText());
                state.setInt(4, Integer.parseInt(phoneText.getText()));
                state.setString(5, emailText.getText());
                state.setString(6, accTypeText.getSelectionModel().getSelectedItem().toString());

                state.execute();

                state.close();
                conn.close();
                clearDetails();
            }//submit=true;
        } catch (Exception e) {
            alertInf();
            System.out.println("Here 8.");
        }
        //return submit;       

    }

    @FXML
    public void clearDetails() {
        fullNameText.setText("");
        addressText.setText("");
        postCodeText.setText("");
        phoneText.setText("");
        emailText.setText("");
        accTypeText.getSelectionModel().selectFirst();
    }

    public void alertInf() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("One or more fields are incomplete, incorrect or a user is not selected.");
        alert.showAndWait();
    }

    public void alertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong.");
        alert.showAndWait();
    }

}