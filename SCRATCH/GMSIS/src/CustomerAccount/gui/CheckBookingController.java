/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.gui;

import DiagnosisAndRepair.logic.DiagnosisAndRepairBooking;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CheckBookingController implements Initializable {

    @FXML
    private Button bookingButton;
    @FXML
    private Button backButtton;
    @FXML
    private TableView<DiagnosisAndRepairBooking> table;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> bookingID;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> vehicleID;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> customerID;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, String> mechanicName;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> date;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> duration;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, String> startTime;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, String> endTime;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> mileage;

    @FXML
    private ObservableList<DiagnosisAndRepairBooking> data;
    public static DiagnosisAndRepair.logic.DiagnosisAndRepairBooking repair = new DiagnosisAndRepairBooking(0, "", "", "", "", 0, 0, "", "");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buildData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent adminUser = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/bill.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();
    }

    public void buildData() throws ClassNotFoundException {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            int ID = BillController.showBill.getBookingID();
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully");

            String SQL = "Select * from booking WHERE booking_id=" + ID;
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new DiagnosisAndRepairBooking(rs.getInt(1), String.valueOf(rs.getInt(2)), String.valueOf(rs.getInt(3)), String.valueOf(rs.getInt(4)), rs.getString(5), rs.getInt(6), rs.getDouble(7), rs.getString(8), rs.getString(9)));
            }

            bookingID.setCellValueFactory(
                    new PropertyValueFactory<>("bookingID"));
            vehicleID.setCellValueFactory(
                    new PropertyValueFactory<>("vehicleReg"));
            customerID.setCellValueFactory(
                    new PropertyValueFactory<>("custName"));
            mechanicName.setCellValueFactory(
                    new PropertyValueFactory<>("mechanicName"));
            date.setCellValueFactory(
                    new PropertyValueFactory<>("date"));
            duration.setCellValueFactory(
                    new PropertyValueFactory<>("duration"));
            mileage.setCellValueFactory(
                    new PropertyValueFactory<>("mileage"));
            startTime.setCellValueFactory(
                    new PropertyValueFactory<>("startTime"));
            endTime.setCellValueFactory(
                    new PropertyValueFactory<>("endTime"));

            table.setItems(data);
            rs.close();
            conn.close();
        } catch (SQLException e) {
            alertError();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void alertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong.");
        alert.showAndWait();
    }
}
