/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.gui;

import static CustomerAccount.gui.GuiController.acc;
import CustomerAccount.logic.bill;
import CustomerAccount.logic.customerAccount;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joen
 */
public class BillController implements Initializable {

    @FXML
    private Button bookingButton;
    @FXML
    private Button backButtton;
    @FXML
    private TableView<bill> table;
    @FXML
    private TableColumn<bill, Integer> bookingID;
    @FXML
    private TableColumn<bill, Boolean> billStatus;
    @FXML
    private TableColumn<bill, Integer> cost;
    @FXML
    private ObservableList<bill> data;
    public static bill showBill = new bill(0,"" , 0, false);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildData();
        
                    table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {
                        if (table.getSelectionModel().getSelectedItem() != null) {
                            int ID = table.getSelectionModel().getSelectedItem().getBillID();
                            String CustomerName = table.getSelectionModel().getSelectedItem().getBookingID();
                            System.out.println("Booking id: " + bookingID);
                            Connection conn = null;
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                            System.out.println("Opened Database Successfully");
                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            ResultSet rs = state.executeQuery("SELECT * FROM bill WHERE bill_id= " + ID);
                            while (rs.next()) {
                                showBill.setBillID(rs.getInt(1));
                                showBill.setBookingID(findCustomerName(rs.getInt(3)));
                                showBill.setTotalCost(rs.getInt(4));
                                showBill.setBillStatus(rs.getBoolean(5));
                            }
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
        
    }

    @FXML
    public void bookingButton(ActionEvent event) throws IOException {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/checkBooking.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.setScene(admin_Scene);
        stage2.show();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/gui.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            int getID = GuiController.acc.getCustomerID();
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");

            String SQL = "Select * from bill WHERE customerID = " + getID;
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new bill(rs.getInt(1),findCustomerName(rs.getInt(2)), rs.getInt(4), rs.getBoolean(5)));
                System.out.println("Boolean is: " + rs.getBoolean(5));
            }
            
            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
            alertError();
        }

        bookingID.setCellValueFactory(
                new PropertyValueFactory<>("bookingID"));
        cost.setCellValueFactory(
                new PropertyValueFactory<>("totalCost"));
        billStatus.setCellValueFactory(
                new PropertyValueFactory<>("billStatus"));
    }

        private String findCustomerName(int customerID) throws ClassNotFoundException {
        String customerName = "";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String SQL = "Select customer_fullname from customer where customer_id='" + customerID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customerName = rs.getString("customer_fullname");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding Customer Name.");
        }
        return customerName;
    }
    
    public void alertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong.");
        alert.showAndWait();
    }

}
