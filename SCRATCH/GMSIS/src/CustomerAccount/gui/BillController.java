/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.gui;

import CustomerAccount.logic.bill;
import CustomerAccount.logic.customerAccount;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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
    private Button testButton;
    @FXML
    private Button backButtton;
    @FXML
    private TableView<bill> table;
    @FXML
    private TableColumn<bill, Integer> bookingID;
    @FXML
    private TableColumn<bill, Integer> cost;
    @FXML
    private ObservableList<bill> data;
    public static bill showBill = new bill(1, 20);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildData();
    }

    @FXML
    public void testButton() {
        System.out.println("Testing this");
        showBill.getPriceFromPart(PartsRecord.gui.PartStockController.showPart);
        System.out.println("Total cost is: " + showBill.getTotalCost());
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
                data.add(new bill(rs.getInt(3), rs.getInt(4)));
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
    }

    public void alertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong.");
        alert.showAndWait();
    }

}
