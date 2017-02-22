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
import VehicleRecord.logic.Vehicle;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Joen
 */
public class GuiController implements Initializable {

    @FXML
    private Button backButtton;
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
    private TextField searchCustomer;
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
    private TableColumn<customerAccount, String> customerVehReg;
    @FXML
    private TableColumn<customerAccount, Integer> customerPhone;
    @FXML
    private TableColumn<customerAccount, String> customerEmail;
    @FXML
    private TableColumn<customerAccount, String> customerType;
    private ObservableList<customerAccount> data;
    public int ID;
    private IntegerProperty index = new SimpleIntegerProperty();
    public static customerAccount acc = new customerAccount(0, "", "", "", 0, "", "", "");

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
                            String whichType = "";
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
                                    whichType = "Business";
                                } else {
                                    accTypeText.getSelectionModel().selectLast();
                                    whichType = "Private";
                                }
                            }
                            ResultSet rs2 = state.executeQuery("SELECT * FROM vehicleList WHERE customerid= " + ID);
                            while (rs2.next()) {
                                acc.setCustomerVehReg(rs.getString("RegNumber"));
                            }
                            acc.setCustomerID(ID);
                            acc.setCustomerFullName(fullNameText.getText());
                            acc.setCustomerAddress(addressText.getText());
                            acc.setCustomerPostCode(postCodeText.getText());
                            acc.setCustomerPhone(Integer.parseInt(phoneText.getText()));
                            acc.setCustomerEmail(emailText.getText());
                            acc.setCustomerType(whichType);
                            getCustomerDetails(acc);
                            state.close();
                            conn.close();
                        }
                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
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
    private void searchCustomer() {
        FilteredList<customerAccount> filteredData = new FilteredList<>(data, e -> true);
        searchCustomer.setOnKeyReleased(e -> {
            searchCustomer.textProperty().addListener((observableValue, oldValue2, newValue2) -> {
                filteredData.setPredicate((Predicate<? super customerAccount>) customerAccount -> {
                    if (newValue2 == null || newValue2.isEmpty()) {
                        return true;
                    }
                    String newValLow = newValue2.toLowerCase();
                    if (customerAccount.getCustomerFullName().toLowerCase().contains(newValLow)) {
                        return true;
                    } else if (customerAccount.getCustomerVehReg().toLowerCase().contains(newValLow)) {
                        return true;
                    }

                    return false;
                });
                SortedList<customerAccount> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
            });
        });
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException, ClassNotFoundException {
        acc.setCustomerFullName(fullNameText.getText());
        acc.setCustomerAddress(addressText.getText());
        acc.setCustomerPostCode(postCodeText.getText());
        acc.setCustomerPhone(Integer.parseInt(phoneText.getText()));
        acc.setCustomerEmail(emailText.getText());
        acc.setCustomerType(String.valueOf(accTypeText.getSelectionModel().getSelectedItem()));
        createData(acc);
        buildData();
    }

    @FXML
    private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            String confirmDelete = JOptionPane.showInputDialog("Are you sure you want to delete this user? (Yes or No) ");
            if (confirmDelete.equalsIgnoreCase("Yes") && isDeleted(acc)) {
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
                acc.setCustomerFullName(fullNameText.getText());
                acc.setCustomerAddress(addressText.getText());
                acc.setCustomerPostCode(postCodeText.getText());
                acc.setCustomerPhone(Integer.parseInt(phoneText.getText()));
                acc.setCustomerEmail(emailText.getText());
                acc.setCustomerType(String.valueOf(accTypeText.getSelectionModel().getSelectedItem()));
                updateData(acc); //Gets ID 
                JOptionPane.showMessageDialog(null, "UserID: " + ID + " has been updated");
                buildData();
            }
        } catch (Exception e) {
            System.out.println("Here 4.");
            alertError();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @FXML
    private void billsButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {

            Parent bills = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/bill.fxml"));
            Scene parts_Scene = new Scene(bills);
            Stage billstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            billstage.setScene(parts_Scene);
            billstage.show();
        } catch (Exception e) {
            System.out.print("Exception caught.");
            e.printStackTrace();
        }

    }

    private boolean isDeleted(customerAccount acc) throws ClassNotFoundException {
        boolean customerDeleted = false;

        ID = table.getSelectionModel().getSelectedItem().getCustomerID();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM customer WHERE customer_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, acc.getCustomerID());
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
    private void updateData(customerAccount acc) throws ClassNotFoundException {

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
                state.setString(1, acc.getCustomerFullName());
                state.setString(2, acc.getCustomerAddress());
                state.setString(3, acc.getCustomerPostCode());
                state.setInt(4, acc.getCustomerPhone());
                state.setString(5, acc.getCustomerEmail());
                state.setString(6, acc.getCustomerType());
                state.setInt(7, acc.getCustomerID());

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
                acc.setCustomerVehReg("");
                acc.setCustomerID(rs.getInt(1));
                acc.setCustomerFullName(rs.getString(2));
                acc.setCustomerAddress(rs.getString(3));
                acc.setCustomerPostCode(rs.getString(4));
                acc.setCustomerPhone(rs.getInt(5));
                acc.setCustomerEmail(rs.getString(6));
                acc.setCustomerType(rs.getString(7));
                java.sql.Statement state = null;
                state = conn.createStatement();
                ResultSet rs2 = state.executeQuery("SELECT * FROM vehicleList WHERE customerid= " + acc.getCustomerID());
                while (rs2.next()) {
                    acc.setCustomerVehReg(rs2.getString("RegNumber"));
                }
                data.add(new customerAccount(acc.getCustomerID(), acc.getCustomerFullName(), acc.getCustomerAddress(), acc.getCustomerPostCode(), acc.getCustomerPhone(), acc.getCustomerEmail(), acc.getCustomerType(), acc.getCustomerVehReg()));
            }
            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            alertError();
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
        customerVehReg.setCellValueFactory(
                new PropertyValueFactory<>("customerVehReg"));
    }

    public void createData(customerAccount acc) throws ClassNotFoundException {

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
        fullNameText.clear();
        addressText.clear();
        postCodeText.clear();
        phoneText.clear();
        emailText.clear();
        accTypeText.getSelectionModel().selectFirst();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();
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

    public void getCustomerDetails(customerAccount acc) {
        System.out.println(acc.getCustomerID());
        System.out.println(acc.getCustomerFullName());
        System.out.println(acc.getCustomerAddress());
        System.out.println(acc.getCustomerPostCode());
        System.out.println(acc.getCustomerEmail());
        System.out.println(acc.getCustomerPhone());
        System.out.println(acc.getCustomerType());
        System.out.println(acc.getCustomerVehReg());
    }

}
