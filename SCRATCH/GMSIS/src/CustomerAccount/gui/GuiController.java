package CustomerAccount.gui;

import Authentication.sqlite;
import CustomerAccount.logic.Bill;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import CustomerAccount.logic.CustomerAccount;
import DiagnosisAndRepair.logic.DiagnosisAndRepairBooking;
import VehicleRecord.gui.AddVehicleController;
import VehicleRecord.gui.VehicleController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Joen
 */
public class GuiController implements Initializable {

    @FXML
    private JFXButton users;
    @FXML
    private AnchorPane pane;
    //FOR BOOKING
    @FXML
    private TableView<DiagnosisAndRepairBooking> tableBooking;
    @FXML
    private JFXCheckBox futureBooking;
    @FXML
    private JFXCheckBox pastBooking;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> bookingIDBooking;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> vehicleID;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, String> mechanicName;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> date;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, Integer> duration;
    @FXML
    private ObservableList<DiagnosisAndRepairBooking> dataBooking;
    @FXML
    private TableColumn<DiagnosisAndRepairBooking, String> partsUsedBooking;
    //FOR BILL
    @FXML
    private TableView<Bill> tableBill;
    @FXML
    private TableColumn<Bill, Integer> bookingIDBill;
    @FXML
    private TableColumn<Bill, Boolean> billStatus;
    @FXML
    private TableColumn<Bill, Integer> cost;
    @FXML
    private ObservableList<Bill> dataBill;
    // FOR GUI
    @FXML
    private Button vehicleReg;
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
    private TableView<CustomerAccount> table;
    @FXML
    private TableColumn<CustomerAccount, Integer> customerID;
    @FXML
    private TableColumn<CustomerAccount, String> customerFullName;
    @FXML
    private TableColumn<CustomerAccount, String> customerAddress;
    @FXML
    private TableColumn<CustomerAccount, String> customerPostCode;
    @FXML
    private TableColumn<CustomerAccount, String> customerVehReg;
    @FXML
    private TableColumn<CustomerAccount, String> customerPhone;
    @FXML
    private TableColumn<CustomerAccount, String> customerEmail;
    @FXML
    private TableColumn<CustomerAccount, String> customerType;
    private ObservableList<CustomerAccount> data;
    private ObservableList<Bill> data2;
    public int ID;
    private IntegerProperty index = new SimpleIntegerProperty();
    public static CustomerAccount acc = new CustomerAccount(0, "", "", "", "", "", "", "");
    public static Bill showBill = new Bill(0, "", "", 0, 0, 0, false);
    private Bill buildBill = new Bill(0, "", "", 0, 0, 0, false);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (futureBooking.isSelected()) {
                futureBooking.setSelected(false);
            }
            if (!Authentication.LoginController.isAdmin) {
                users.setDisable(true);
            }
            pastBooking.setSelected(true);
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
                            conn = (new sqlite().connect());
                            ID = table.getSelectionModel().getSelectedItem().getCustomerID();
                            System.out.println("Opened Database Successfully here");
                            String whichType = "";
                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            ResultSet rs = state.executeQuery("SELECT * FROM customer WHERE customer_id= " + "'" + ID + "'");
                            while (rs.next()) {
                                ID = rs.getInt("customer_id");
                                fullNameText.setText(rs.getString("customer_fullname"));
                                addressText.setText(rs.getString("customer_address"));
                                postCodeText.setText(rs.getString("customer_postcode"));
                                phoneText.setText(rs.getString("customer_phone"));
                                emailText.setText(rs.getString("customer_email"));
                                if (rs.getString("customer_type").equals("Business")) {
                                    accTypeText.getSelectionModel().selectFirst();
                                    whichType = "Business";
                                } else {
                                    accTypeText.getSelectionModel().selectLast();
                                    whichType = "Private";
                                }
                            }
                            acc.setCustomerVehRegFromTable(table.getSelectionModel().getSelectedItem().getCustomerVehReg());
                            acc.setCustomerID(ID);
                            acc.setCustomerFullName(fullNameText.getText());
                            acc.setCustomerAddress(addressText.getText());
                            acc.setCustomerPostCode(postCodeText.getText());
                            acc.setCustomerPhone(phoneText.getText());
                            acc.setCustomerEmail(emailText.getText());
                            acc.setCustomerType(whichType);
                            getCustomerDetails(acc);
                            state.close();
                            conn.close();
                            buildDataBill();
                            buildDataBooking();
                        }
                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        e.printStackTrace();
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
        FilteredList<CustomerAccount> filteredData = new FilteredList<>(data, e -> true);
        searchCustomer.setOnKeyReleased(e -> {
            searchCustomer.textProperty().addListener((observableValue, oldValue2, newValue2) -> {
                filteredData.setPredicate((Predicate<? super CustomerAccount>) customerAccount -> {
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
                SortedList<CustomerAccount> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
            });
        });
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException, ClassNotFoundException, NumberFormatException {
        try {
            acc.setCustomerFullName(fullNameText.getText());
            acc.setCustomerAddress(addressText.getText());
            acc.setCustomerPostCode(postCodeText.getText());
            acc.setCustomerPhone(phoneText.getText());
            acc.setCustomerEmail(emailText.getText());
            acc.setCustomerType(String.valueOf(accTypeText.getSelectionModel().getSelectedItem()));
            if (createData(acc)) {
                buildData();
                Optional<ButtonType> selected = alertConfirm("Would you like to add a vehicle?");

                if (selected.get() != ButtonType.OK) {
                    return;

                }
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("/VehicleRecord/gui/AddVehicle.fxml").openStream());
                AddVehicleController c = (AddVehicleController) loader.getController();
                Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                c.addEntry.setVisible(true);
                c.updateBtn.setVisible(false);
                c.id.setVisible(false);
                c.vID.setVisible(false);
                c.customerNames.setValue(acc.getCustomerFullName());
                c.custID.setText(getCustomerID().toString());
                stage2.hide();
                Scene vehicle_Scene = new Scene(root);
                primaryStage.setScene(vehicle_Scene);
                primaryStage.setHeight(810);
                primaryStage.setWidth(1359);
                primaryStage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void vehicleRegButton(ActionEvent event) throws IOException, ClassNotFoundException, NumberFormatException {
        try {
            acc.setCustomerFullName(fullNameText.getText());
            acc.setCustomerAddress(addressText.getText());
            acc.setCustomerPostCode(postCodeText.getText());
            acc.setCustomerPhone(phoneText.getText());
            acc.setCustomerEmail(emailText.getText());
            acc.setCustomerType(String.valueOf(accTypeText.getSelectionModel().getSelectedItem()));

            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml").openStream());
            VehicleController c = (VehicleController) loader.getController();
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            c.buildCustomerVehicle(acc.getCustomerID(), acc.getCustomerFullName());
            c.buildCustomerData(acc.getCustomerID());
            stage2.hide();
            Scene vehicle_Scene = new Scene(root);
            primaryStage.setScene(vehicle_Scene);
            primaryStage.setHeight(810);
            primaryStage.setWidth(1359);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            Optional<ButtonType> selected = alertConfirm("Are you sure you want to delete this Customer");

            if (selected.get() != ButtonType.OK) {
                return;
            } else {
                isDeleted(acc);
                buildData();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            alertInf();
        }
    }

    @FXML
    private void updateButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            Optional<ButtonType> selected = alertConfirm("Are you sure you want to update this Customer");

            if (selected.get() != ButtonType.OK) {
                return;
            } else {
                acc.setCustomerFullName(fullNameText.getText());
                acc.setCustomerAddress(addressText.getText());
                acc.setCustomerPostCode(postCodeText.getText());
                acc.setCustomerPhone(phoneText.getText());
                acc.setCustomerEmail(emailText.getText());
                acc.setCustomerType(String.valueOf(accTypeText.getSelectionModel().getSelectedItem()));
                updateData(acc);
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

    @FXML
    private void bookingButton(ActionEvent event) throws IOException, ClassNotFoundException {
        try {
            Parent bills = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/Booking.fxml"));
            Scene parts_Scene = new Scene(bills);
            Stage billstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            billstage.setScene(parts_Scene);
            billstage.show();
        } catch (Exception e) {
            System.out.print("Exception caught.");
            e.printStackTrace();
        }
    }

    private boolean isDeleted(CustomerAccount acc) throws ClassNotFoundException {
        boolean customerDeleted = false;

        ID = table.getSelectionModel().getSelectedItem().getCustomerID();

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            String sql = "DELETE FROM customer WHERE customer_id= ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, acc.getCustomerID());
            state.executeUpdate();

            state.close();
            conn.close();

            customerDeleted = true;
            clearDetails();
            alertInfoSuccess("Success", "Customer was deleted.");
        } catch (SQLException e) {
            alertError();
            System.out.println("Here 5.");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return customerDeleted;

    }

    @FXML
    private void updateData(CustomerAccount acc) throws ClassNotFoundException {

        //System.out.println("SELECT * FROM NewUsers WHERE FirstName= " + "'" + firstName.getText() + "'" + "AND Surname= " + "'" + surname.getText() + "'" + "AND UserID= " + "'" + newUserID.getText() + "'");
        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            int ID = table.getSelectionModel().getSelectedItem().getCustomerID();

            String sql = "UPDATE customer SET customer_fullname=?,customer_address=?,customer_postcode=?,customer_phone=?,customer_email=?,customer_type=? WHERE customer_id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            String name = fullNameText.getText();
            String email = emailText.getText();
            String phoneNumber = phoneText.getText();
            int check = String.valueOf(phoneText.getText()).length();
            if (fullNameText.getText().equals("") || name.matches(".*\\d+.*") || !email.contains("@") || addressText.getText().equals("") || postCodeText.getText().equals("") || !phoneNumber.matches("^[0-9]+$") || check < 6 || emailText.getText().equals("")) {
                alertInf();
            } else {
                state.setString(1, acc.getCustomerFullName());
                state.setString(2, acc.getCustomerAddress());
                state.setString(3, acc.getCustomerPostCode());
                state.setString(4, acc.getCustomerPhone());
                state.setString(5, acc.getCustomerEmail());
                state.setString(6, acc.getCustomerType());
                state.setInt(7, acc.getCustomerID());

                state.execute();

                state.close();
                conn.close();
                clearDetails();
                alertInfoSuccess("Success", "Customer was updated.");
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

            conn = (new sqlite().connect());

            String SQL = "Select * from customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                acc.setCustomerVehReg("");
                acc.setCustomerID(rs.getInt(1));
                acc.setCustomerFullName(rs.getString(2));
                acc.setCustomerAddress(rs.getString(3));
                acc.setCustomerPostCode(rs.getString(4));
                acc.setCustomerPhone(rs.getString(5));
                acc.setCustomerEmail(rs.getString(6));
                acc.setCustomerType(rs.getString(7));
                java.sql.Statement state = null;
                state = conn.createStatement();
                ResultSet rs2 = state.executeQuery("SELECT * FROM vehicleList WHERE customerid= " + acc.getCustomerID());
                while (rs2.next()) {
                    acc.setCustomerVehReg(rs2.getString("RegNumber"));
                    System.out.println("This is what will be passed to constructer: " + acc.getCustomerVehReg());
                }
                data.add(new CustomerAccount(acc.getCustomerID(), acc.getCustomerFullName(), acc.getCustomerAddress(), acc.getCustomerPostCode(), acc.getCustomerPhone(), acc.getCustomerEmail(), acc.getCustomerType(), acc.getCustomerVehReg()));
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

    public boolean createData(CustomerAccount acc) throws ClassNotFoundException, NullPointerException {

        Connection conn = null;

        try {
            conn = (new sqlite().connect());
            System.out.println("Creating data.");

            String sql = "insert into customer(customer_fullname, customer_address, customer_postcode, customer_phone, customer_email, customer_type) values(?,?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            String name = fullNameText.getText();
            String email = emailText.getText();
            String phoneNumber = phoneText.getText();
            int check = String.valueOf(phoneText.getText()).length();
            if (fullNameText.getText().equals("") || name.matches(".*\\d+.*") || !email.contains("@") || addressText.getText().equals("") || postCodeText.getText().equals("") || !phoneNumber.matches("^[0-9]+$") || check < 6 || emailText.getText().equals("")) {
                alertInf();
            } else {
                state.setString(1, fullNameText.getText());
                state.setString(2, addressText.getText());
                state.setString(3, postCodeText.getText());
                state.setString(4, phoneText.getText());
                state.setString(5, emailText.getText());
                state.setString(6, accTypeText.getSelectionModel().getSelectedItem().toString());

                state.execute();

                state.close();
                conn.close();
                clearDetails();
                alertInfoSuccess("Success", "Customer was added.");
                return true;
            }//submit=true;
        } catch (SQLException e) {
            alertInf();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    @FXML
    public void clearDetails() {
        fullNameText.clear();
        addressText.clear();
        postCodeText.clear();
        phoneText.clear();
        emailText.clear();
        searchCustomer.clear();
        accTypeText.getSelectionModel().selectFirst();
    }

    public void buildDataBill() {
        dataBill = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            int getID = acc.getCustomerID();
            int bookingID = 0;
            conn = (new sqlite().connect());
            String date = "";
            String SQL = "Select * from bill WHERE customerID = " + getID;
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                bookingID = rs.getInt(3);
                String SQL2 = "Select * from booking WHERE booking_id = " + bookingID;
                ResultSet rs2 = conn.createStatement().executeQuery(SQL2);
                while (rs2.next()) {
                    date = rs2.getString(5);
                    dataBill.add(new Bill(0, rs.getString(1), date, rs.getDouble(7), 0, 0, rs.getBoolean(8)));
                }
            }

            rs.close();
            conn.close();
            filterByPastBill();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookingIDBill.setCellValueFactory(
                new PropertyValueFactory<>("bookingID"));
        cost.setCellValueFactory(
                new PropertyValueFactory<>("totalCost"));
        billStatus.setCellValueFactory(
                new PropertyValueFactory<>("billStatus"));
    }

    public void buildDataBooking() throws ClassNotFoundException {
        dataBooking = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            int ID = GuiController.acc.getCustomerID();
            conn = (new sqlite().connect());
            int parts = 0;
            String SQL = "Select * from booking WHERE customer_id=" + ID;
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                DiagnosisAndRepair.gui.DiagnosisAndRepairController.BookingObj.setPartName("");
                int vehicleID = rs.getInt(2);
                java.sql.Statement state = null;
                state = conn.createStatement();
                ResultSet rs2 = state.executeQuery("SELECT * FROM vehiclePartsUsed WHERE customerID= " + acc.getCustomerID() + " AND vehicleID=" + vehicleID);
                while (rs2.next()) {
                    parts = rs2.getInt("parts_id");
                    DiagnosisAndRepair.gui.DiagnosisAndRepairController.BookingObj.setPartName(findPartName(parts));
                }
                dataBooking.add(new DiagnosisAndRepairBooking(rs.getInt(1), findVehicleReg(rs.getInt(2)), String.valueOf(rs.getInt(2)), "", findMechanicName(rs.getInt(4)), rs.getString(5), rs.getInt(6), 0, rs.getString(7), rs.getString(8), DiagnosisAndRepair.gui.DiagnosisAndRepairController.BookingObj.getPartName()));
            }

            bookingIDBooking.setCellValueFactory(
                    new PropertyValueFactory<>("bookingID"));
            vehicleID.setCellValueFactory(
                    new PropertyValueFactory<>("vehicleReg"));
            mechanicName.setCellValueFactory(
                    new PropertyValueFactory<>("mechanicName"));
            date.setCellValueFactory(
                    new PropertyValueFactory<>("date"));
            duration.setCellValueFactory(
                    new PropertyValueFactory<>("duration"));
            partsUsedBooking.setCellValueFactory(
                    new PropertyValueFactory<>("partName"));

            tableBooking.setItems(dataBooking);
            rs.close();
            conn.close();
            if (futureBooking.isSelected()) {
                filterByFuture();
            }
            if (pastBooking.isSelected()) {
                filterByPast();
            }
        } catch (SQLException e) {
            alertError();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private String findVehicleReg(int vehicleReg) throws ClassNotFoundException {
        String vehicleRegIS = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select RegNumber from vehicleList where vehicleID='" + vehicleReg + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehicleRegIS = rs.getString("RegNumber");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding Customer Name.");
        }
        return vehicleRegIS;
    }

    private String findPartName(int partID) throws ClassNotFoundException {
        String PartName = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select nameofPart from vehiclePartsStock where parts_id='" + partID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                PartName = rs.getString("nameofPart");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding Customer Name.");
        }
        return PartName;
    }

    private String findMechanicName(int mechanicID) throws ClassNotFoundException {
        String mechanicName = "";
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            String SQL = "Select fullname from mechanic where mechanic_id='" + mechanicID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                mechanicName = rs.getString("fullname");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error finding Customer Name.");
        }
        return mechanicName;
    }

    @FXML
    private void filterByPastBill() throws ClassNotFoundException {
        try {
            ObservableList<Bill> pastList = FXCollections.observableArrayList(dataBill);

            for (int i = 0; i < pastList.size(); i++) {
                System.out.println(pastList.get(i).getDate());
            }

            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 0; i < pastList.size(); i++) {
                LocalDate tempDate = LocalDate.parse(pastList.get(i).getDate(), formatter);
                System.out.println("Doing this");
                if (now.isBefore(tempDate)) {
                    System.out.println("Removing: " + pastList.get(i).getDate());
                    pastList.remove(i);
                    i--;
                    System.out.println("Past list in loop" + pastList);
                }
            }

            if (pastList.isEmpty()) {
                System.out.println("It's empty past.");
            }

            System.out.println("Past list outside" + pastList);
            tableBill.setItems(pastList);
            tableBill.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void filterByPast() throws ClassNotFoundException {
        try {
            if (!pastBooking.isSelected()) {
                pastBooking.setSelected(true);
                return;
            }

            pastBooking.setSelected(true);
            futureBooking.setSelected(false);

            ObservableList<DiagnosisAndRepairBooking> pastList = FXCollections.observableArrayList(dataBooking);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (int i = 0; i < pastList.size(); i++) {
                LocalDateTime tempDate = LocalDateTime.parse(pastList.get(i).getDate() + " " + pastList.get(i).getStartTime(), formatter);

                if (now.isBefore(tempDate)) {
                    pastList.remove(i);
                    i--;
                }
            }

            if (pastList.isEmpty()) {
                System.out.println("It's empty past.");
            }

            tableBooking.setItems(pastList);
            tableBooking.refresh();
        } catch (Exception e) {
            System.out.println("Error catched");
        }
    }

    @FXML
    private void filterByFuture() throws ClassNotFoundException {
        try {
            if (!futureBooking.isSelected()) {
                futureBooking.setSelected(true);
                return;
            }

            futureBooking.setSelected(true);
            pastBooking.setSelected(false);

            ObservableList<DiagnosisAndRepairBooking> futureList = FXCollections.observableArrayList(dataBooking);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (int i = 0; i < futureList.size(); i++) {
                LocalDateTime tempDate = LocalDateTime.parse(futureList.get(i).getDate() + " " + futureList.get(i).getStartTime(), formatter);

                if (now.isAfter(tempDate)) {
                    futureList.remove(i);
                    i--;
                }
            }

            if (futureList.isEmpty()) {
                System.out.println("It's empty the future");
            }

            tableBooking.setItems(futureList);
            tableBooking.refresh();
        } catch (Exception e) {
            System.out.println("Error catched");
        }
    }

    @FXML
    private void addBooking(ActionEvent event) throws IOException, ClassNotFoundException {
        if (table.getSelectionModel().getSelectedItem() == null) {
            //select row;
            return;
        }

        if (table.getSelectionModel().getSelectedItem().getCustomerVehReg().isEmpty()) {
            //no vehicle
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));
        Parent root = (AnchorPane) loader.load();
        DiagnosisAndRepair.gui.DiagnosisAndRepairController obj = (DiagnosisAndRepair.gui.DiagnosisAndRepairController) loader.getController();
        obj.initiateBookingFromCustomer(table.getSelectionModel().getSelectedItem().getCustomerFullName(), table.getSelectionModel().getSelectedItem().getCustomerID());
        pane.getChildren().setAll(root);
    }

    public Integer getCustomerID() throws ClassNotFoundException {
        int cID = 0;
        Connection conn = null;
        try {
            conn = (new sqlite().connect());
            System.out.println("Opened Database Successfully");

            String SQL = "Select customer_id from customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            while (rs.next()) {
                cID = rs.getInt("customer_id");
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cID;
    }

    public void alertInf() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("One or more fields are incomplete, incorrect or a user is not selected.");
        alert.showAndWait();
    }

    private void alertInfoSuccess(String header, String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(information);
        alert.showAndWait();
    }

    public void alertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Something went wrong.");
        alert.showAndWait();
    }

    private Optional<ButtonType> alertConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public void getCustomerDetails(CustomerAccount acc) {
        System.out.println(acc.getCustomerID());
        System.out.println(acc.getCustomerFullName());
        System.out.println(acc.getCustomerAddress());
        System.out.println(acc.getCustomerPostCode());
        System.out.println(acc.getCustomerEmail());
        System.out.println(acc.getCustomerPhone());
        System.out.println(acc.getCustomerType());
        System.out.println(acc.getCustomerVehReg());
    }

    ///FXML BAR STUFF
    @FXML
    private void logout(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Login.fxml"));
        pane.getChildren().setAll(rootPane);
        Authentication.LoginController.isAdmin = false;
    }

    @FXML
    private void users(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        pane.getChildren().setAll(rootPane);
    }

    @FXML
    private void cus(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/CustomerAccount/gui/gui.fxml"));
        pane.getChildren().setAll(rootPane);
    }

    @FXML
    private void vehicleRecord(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/Vehicle.fxml"));
        pane.getChildren().setAll(rootPane);
    }

    @FXML
    private void vehicleEntry(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/VehicleRecord/gui/AddVehicle.fxml"));
        pane.getChildren().setAll(rootPane);
    }

    @FXML
    private void diag(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/DiagnosisAndRepair/gui/DiagnosisAndRepairGui.fxml"));

        pane.getChildren().setAll(rootPane);
    }

    @FXML
    private void pStock(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/partStock.fxml"));
        pane.getChildren().setAll(rootPane);
    }

    @FXML
    private void pUsed(ActionEvent event) throws IOException {
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource("/PartsRecord/gui/parts.fxml"));
        pane.getChildren().setAll(rootPane);
    }

}
