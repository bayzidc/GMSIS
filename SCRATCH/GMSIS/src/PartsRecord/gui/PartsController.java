/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.gui;

import Authentication.sqlite;
import CustomerAccount.gui.BillController;
import PartsRecord.logic.parts;
import PartsRecord.logic.partsUsed;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import PartsRecord.logic.partsUsed;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * FXML Controller class
 *
 * @author Fabiha
 */
public class PartsController implements Initializable {

    @FXML
    private ComboBox<String> partNameCombo;
    @FXML
    private TextField quantity;
    @FXML
    private DatePicker dateOfInstall;
    @FXML
    private ComboBox<String> vehicleRegCombo;
    @FXML
    private ComboBox<String> customerNameCombo;
    @FXML
    private TextField searchParts;
    //@FXML
    //private ComboBox 
    @FXML
    private Button installParts;
    @FXML
    private Button editParts;
    @FXML
    private Button deleteParts;
    @FXML
    private Button clear;
    @FXML
    private Button addBill;
    @FXML
    private TableView<partsUsed> table;
    @FXML
    private TableColumn<partsUsed, String> rowID;
    @FXML
    private TableColumn<partsUsed, String> nameCol;
    @FXML
    private TableColumn<partsUsed, Double> costCol;
    @FXML
    private TableColumn<partsUsed, Integer> quantityCol;
    @FXML
    private TableColumn<partsUsed, String> installDateCol;
    @FXML
    private TableColumn<partsUsed, String> expireDateCol;
    @FXML
    private TableColumn<partsUsed, String> registrationNoCol;
    @FXML
    private TableColumn<partsUsed, String> customerNameCol;
    @FXML // Observable list to hold partsUsed object.
    ObservableList<partsUsed> data;
    public String nameOfPart;
    public static partsUsed part = new partsUsed(0, "", 0.0, 0, "", "", "", "");
    ObservableList<String> vehicleRegNo = FXCollections.observableArrayList();
    ObservableList<String> customerNames = FXCollections.observableArrayList();

    @FXML
    public void backButton(ActionEvent event) throws IOException, ClassNotFoundException // method which goes back to admin page
    {   // When pressed the back button load the Admin.fxml file
        Parent adminUser = FXMLLoader.load(getClass().getResource("/Authentication/Admin.fxml"));
        Scene admin_Scene = new Scene(adminUser);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage2.hide();
        stage2.setScene(admin_Scene);
        stage2.show();

    }

    public void clearButton(ActionEvent event) throws IOException, ClassNotFoundException {
        clearFields();

    }

    public boolean isFieldsCompleted() {
        if (partNameCombo.getValue() != null || quantity.getText().equals("") || dateOfInstall.getValue().equals("") || vehicleRegCombo.getValue() != null || customerNameCombo.getValue() != null) {
            return false;
        }
        return true;
    }

    @FXML
    public void addBill() throws ClassNotFoundException {
        BillController.showBill.addCostToBill(BillController.showBill, part, part.getQuantity());
        Double totalCost = BillController.showBill.getTotalCost();
        int customer_id = findCustomerID(table.getSelectionModel().getSelectedItem().getCustomerFullName());
        try {
            Connection conn = null;

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Creating data.");

            String sql = "insert into bill(customerID, bookingID, totalCost, settled) values(?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1, customer_id);
            state.setInt(2, 1);
            state.setDouble(3, totalCost);
            state.setBoolean(4, false);
            BillController.showBill.setTotalCost(0);
            state.execute();

            state.close();
            conn.close();
        }//submit=true;
        catch (Exception e) {
            alertInformation("Error.");
            System.out.println("Here 8.");
        }
    }

    public void installButton() throws IOException, ClassNotFoundException {

        part.setPartName(partNameCombo.getValue());
        part.setQuantity(Integer.parseInt(quantity.getText()));
        part.setInstallDate(dateOfInstall.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        part.setVehicleRegNo(vehicleRegCombo.getValue());
        part.setcustomerFullName(customerNameCombo.getValue());
        int stockAvailable = findStockLevel(part.getPartName());
        if ((stockAvailable > 0) && stockAvailable >= part.getQuantity()) {
            decreaseStockLevel(part.getPartName(), stockAvailable);
            createData(part);
            buildPartsUsedData();
            clearFields();
        } else if (stockAvailable == 0) {
            alertInformation("The stock is empty.");
        } else if (stockAvailable < part.getQuantity()) {
            alertInformation("The quantity you want  is not available in the stock");

        }
    }

    public int findStockLevel(String name) throws ClassNotFoundException {
        int stockLevel = 0;

        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select stockLevelsOfParts from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                stockLevel = rs.getInt("stockLevelsOfParts");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return stockLevel;

    }

    public void decreaseStockLevel(String name, int stockAvailable) throws ClassNotFoundException {
        Connection conn = null;
        int quantityWanted = part.getQuantity();
        int newStockLevel = stockAvailable - quantityWanted;
        try {
            // Create a Java Connection to the database.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");

            String sql = "UPDATE vehiclePartsStock SET stockLevelsOfParts =? WHERE nameofPart =?";
            PreparedStatement state = conn.prepareStatement(sql);
            // Binding the parameters.
            state.setInt(1, newStockLevel);
            state.setString(2, part.getPartName());
            state.execute();

            state.close();
            conn.close();
            clearFields();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @FXML
    public void deleteButton(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        try {
            if (partNameCombo.getValue() == null || quantity.getText().equals("") || dateOfInstall.getValue().equals("") || vehicleRegCombo.getValue() == null || customerNameCombo.getValue() == null) {
                alertError("Select a row in order to delete a part.");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("");
                alert.setContentText("Are you sure you want to delete the parts?");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();

                String nameSelected = table.getSelectionModel().getSelectedItem().getPartName();

                if (result.get() == buttonTypeYes && isPartsDeleted(part)) {

                    alertInformation("PartsName: " + nameSelected + " has been deleted.");

                }
                buildPartsUsedData();
            }
        } catch (Exception e) {
            System.out.println(e);

        }

    }

    private boolean isPartsDeleted(partsUsed part) throws ClassNotFoundException {
        boolean partsDeleted = false;

        String name = table.getSelectionModel().getSelectedItem().getPartName();

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened Database Successfully");
            String sql = "DELETE FROM vehiclePartsUsed WHERE name =?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, part.getPartName());
            state.executeUpdate();

            state.close();
            conn.close();

            partsDeleted = true;
            clearFields();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return partsDeleted;
    }

    @FXML
    /**
     * public void editData(PartsRecord.logic.parts part) throws
     * ClassNotFoundException { Connection conn = null;
     *
     * try { // Create a Java Connection to the database.
     * Class.forName("org.sqlite.JDBC"); conn =
     * DriverManager.getConnection("jdbc:sqlite:database.sqlite");
     *
     * System.out.println("Opened Database Successfully 1");
     *
     * String sql = "UPDATE vehiclePartsStock SET
     * nameofPart=?,description=?,stockLevelsOfParts=?,cost=? WHERE parts_id=?";
     * PreparedStatement state = conn.prepareStatement(sql); // Binding the
     * parameters. state.setString(1, part.getPartName()); state.setString(2,
     * part.getPartDescription()); state.setInt(3, part.getPartStockLevel());
     * state.setDouble(4, part.getCost()); //state.setString(5,
     * part.getArrivedDate()); state.setInt(5, part.getPartIDentify());
     *
     * state.execute();
     *
     * state.close(); conn.close(); clearFields();
     *
     * } catch (SQLException e) { System.err.println(e.getClass().getName() + ":
     * " + e.getMessage()); System.exit(0); } }
     *
     *
     * @FXML public void editButton(ActionEvent event) throws IOException,
     * ClassNotFoundException { try { if (name.getText().equals("") ||
     * description.getText().equals("") || stockLevels.getText().equals("") ||
     * cost.getText().equals("")) {
     *
     * alertError("Please complete all the fields.");
     *
     * } else { //showPart.setPartId(Integer.parseInt(idNumber.getText()));
     * showPart.setPartName(name.getText());
     * showPart.setPartDescription(description.getText());
     * showPart.setPartStockLevel(Integer.parseInt(stockLevels.getText()));
     * showPart.setCost(Double.parseDouble(cost.getText()));
     * //showPart.setArrivedDate(arrivedStockDate.getText());
     * editData(showPart); buildPartsStockData(); alertInformation("The database
     * has been updated.");
     *
     * }
     * } catch (Exception e) {
     *
     * System.err.println(e.getClass().getName() + ": " + e.getMessage()); } }*
     */

    private void searchParts() {
        FilteredList<partsUsed> filteredData = new FilteredList<>(data, e -> true);
        searchParts.setOnKeyReleased(e -> {
            searchParts.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super partsUsed>) partsUsed -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String newLowerValue = newValue.toLowerCase();
                    if (partsUsed.getVehicleRegNo().toLowerCase().contains(newLowerValue)) {
                        return true; // Filter matches vehicle registraion number.
                    } else if (partsUsed.getCustomerFullName().toLowerCase().contains(newLowerValue)) {
                        return true; // filter matches customer full name.
                    }

                    return false;
                });
                SortedList<partsUsed> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
            });
        });
    }

    public void clearFields() {
        partNameCombo.setValue(null);
        quantity.clear();
        ((TextField) dateOfInstall.getEditor()).clear();
        vehicleRegCombo.setValue(null);
        customerNameCombo.setValue(null);

    }

    public void alertInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(information);
        alert.showAndWait();
    }

    public void alertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void alertConfirmation(String confirmation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation.");
        alert.setHeaderText(null);
        alert.setContentText(confirmation);
        alert.showAndWait();
    }

    private int findVehicleID(String vehicleRegNo) throws ClassNotFoundException {
        int vehRegID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select vehicleID from vehicleList where RegNumber='" + vehicleRegNo + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegID = rs.getInt("vehicleID");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehRegID;
    }

    private int findCustomerID(String customerName) throws ClassNotFoundException {
        int customerID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String SQL = "Select customer_id from customer where customer_fullname='" + customerName + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customerID = rs.getInt("customer_id");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return customerID;
    }

    private int findPartsID(String name) throws ClassNotFoundException {
        int partID = 0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select parts_id from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                partID = rs.getInt("parts_id");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return partID;
    }

    private Double findPartsCost(String name) throws ClassNotFoundException {
        double cost = 0.0;
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            String SQL = "Select cost from vehiclePartsStock where nameofPart='" + name + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                cost = rs.getDouble("cost");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return cost;
    }

    public String findExpireDate(String installDate) {

        Date date = null;
        String warrantyexpire = "";

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {

            date = df.parse(installDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, 1); // to get previous year add -1
            Date warrantyExpire = cal.getTime();
            warrantyexpire = df.format(warrantyExpire);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return warrantyexpire;
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
            System.out.println("Error");
        }
        return customerName;
    }

    private String findVehReg(int vehicleID) throws ClassNotFoundException {
        String vehRegNo = "";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String SQL = "Select RegNumber from vehicleList where vehicleID='" + vehicleID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehRegNo = rs.getString("RegNumber");
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehRegNo;
    }

    private void fillVehicleRegCombo() throws ClassNotFoundException {

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            //conn = (new sqlite().connect());
            String SQL = "Select RegNumber from vehicleList";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                vehicleRegNo.add(rs.getString("RegNumber"));
            }
            vehicleRegCombo.setItems(vehicleRegNo);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    private void fillCustomerNameCombo() throws ClassNotFoundException {
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String SQL = "Select customer_fullname from customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customerNames.add(rs.getString("customer_fullname"));
            }
            customerNameCombo.setItems(customerNames);

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    // Filling data to the tableView From the database.
    public void buildPartsUsedData() {
        data = FXCollections.observableArrayList();
        Connection conn = null;
        try {

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            System.out.println("Opened Database Successfully 2");

            String SQL = "Select * from vehiclePartsUsed";
            // Execute query and store results in a resultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                //  a ResultSet object that retrieves the results of your query,
                // and executes a simple while loop, which retrieves and displays those results.
                // Calling the constructor
                part.setRowID(rs.getInt(1));
                part.setPartName(rs.getString(2));
                part.setCost(rs.getDouble(3));
                part.setQuantity(rs.getInt(4));
                part.setInstallDate(rs.getString(5));
                part.setWarrantyExpireDate(rs.getString(6));
                part.setVehicleRegNo(findVehReg(rs.getInt(7)));
                part.setcustomerFullName(findCustomerName(rs.getInt(8)));

                data.add(new partsUsed(part.getRowID(), part.getPartName(), part.getCost(), part.getQuantity(), part.getInstallDate(), part.getWarrantyExpireDate(), part.getVehicleRegNo(), part.getCustomerFullName()));
            }

            table.setItems(data);
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data.");
        }

    }

    // add data to the database from the textfield.
    public void createData(partsUsed part) throws ClassNotFoundException {

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Opened database successfully 1.");
            System.out.println("Creating data.");
            //query you have to write to insert your data
            String sql = "insert into vehiclePartsUsed(partsId,name,cost,quantity, dateOfInstall, dateOfWarrantyExpire,vehicleID, customerID, bookingID) values(?,?,?,?,?,?,?,?,?)";
            //The query is sent to the database and prepared there which means the SQL statement is "analysed".
            PreparedStatement state = conn.prepareStatement(sql);

            if (isFieldsCompleted()) {

                alertError("Please complete all the fields.");

            } else {
                // the values are sent to the server
                // use the appropriate state.setXX() methods to set the appropriate values for the parameters that you've defined.
                // Then call state.executeUpdate() to execute the call to the database.
                state.setInt(1, findPartsID(part.getPartName()));
                state.setString(2, part.getPartName());
                state.setDouble(3, findPartsCost(part.getPartName()));
                state.setInt(4, part.getQuantity());
                state.setString(5, part.getInstallDate());
                state.setString(6, findExpireDate(part.getInstallDate()));// return a string date
                state.setInt(7, findVehicleID(part.getVehicleRegNo()));
                state.setInt(8, findCustomerID(part.getCustomerFullName()));
                state.setInt(9, 1); // booking ID
                
                state.execute();

                state.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Creating Data");
        }

    }

    public LocalDate convertStringToDate(String dateString) {
        LocalDate date = null;

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {

            date = LocalDate.parse(dateString, df);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> namesCombo = FXCollections.observableArrayList("Spark Plugs", "Prop Shaft", "Handbrake Cable", "Bumper", "Rims", "HeadLights", "Tail Lights", "Radiator", "Fender", "Roof Rack");
        partNameCombo.setItems(namesCombo);
        try {
            fillVehicleRegCombo();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fillCustomerNameCombo();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartsController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("partName"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        installDateCol.setCellValueFactory(
                new PropertyValueFactory<>("installDate"));
        expireDateCol.setCellValueFactory(
                new PropertyValueFactory<>("warrantyExpireDate"));
        registrationNoCol.setCellValueFactory(
                new PropertyValueFactory<>("vehicleRegNo"));
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<>("customerFullName"));
        rowID.setCellValueFactory(
                new PropertyValueFactory<>("rowID"));
        try {
            buildPartsUsedData();

            // selectedItemProperty = gives you the item the user selected form the table.
            // add listner to your tableview selectedItemProperty
            // The listener is gonna sit on the item of the tableView and its gonna wait for some action to happen, Ex: User selecting an item from the tableview 
            //
            table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
                @Override //This method will be called whenever user selected row

                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    try {   //Checks whether item is selected.
                        if (table.getSelectionModel().getSelectedItem() != null) {
                            Connection conn = null;
                            Class.forName("org.sqlite.JDBC");
                            conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
                            System.out.println("Opened Database Successfully initial");
                            nameOfPart = table.getSelectionModel().getSelectedItem().getPartName();
                            int getQuantity = table.getSelectionModel().getSelectedItem().getQuantity();
                            java.sql.Statement state = null;
                            state = conn.createStatement();
                            // Execute query and store results in a resultSet
                            ResultSet rs = state.executeQuery("SELECT * FROM vehiclePartsUsed WHERE rowid= ");
                            while (rs.next()) {
                                // get data from db and show it on the textFields.
                                partNameCombo.setValue(rs.getString("name"));
                                quantity.setText(String.valueOf(rs.getInt("quantity")));
                                dateOfInstall.setValue(convertStringToDate(rs.getString("dateOfInstall")));
                                vehicleRegCombo.setValue(String.valueOf(findVehReg(rs.getInt("vehicleID"))));
                                customerNameCombo.setValue(String.valueOf(findCustomerName(rs.getInt("customerID"))));

                            }

                            part.setPartName(nameOfPart);
                            part.setQuantity(Integer.parseInt(quantity.getText()));
                            part.setInstallDate((dateOfInstall.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                            part.setVehicleRegNo(vehicleRegCombo.getValue());
                            part.setcustomerFullName(customerNameCombo.getValue());
                            part.setCost(table.getSelectionModel().getSelectedItem().getCost());
                            getCustomerDetails(part);
                            state.close();
                            conn.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error 1.");
            e.printStackTrace();

        }
    }

    public void getCustomerDetails(partsUsed part) {
        System.out.println(part.getCost());
        System.out.println(part.getQuantity());
        System.out.println(part.getCustomerFullName());
    }
    // TODO
}
