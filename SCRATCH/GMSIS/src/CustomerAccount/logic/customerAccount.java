/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Joen
 */
public class customerAccount {

    public IntegerProperty customerID;
    public StringProperty customerFullName;
    public StringProperty customerAddress;
    public StringProperty customerPostCode;
    public StringProperty customerEmail;
    public StringProperty customerType;
    public StringProperty customerVehReg;
    public IntegerProperty customerPhone;

    public customerAccount(int id, String fullname, String address, String postcode, int phone, String email, String type, String vehReg) {
        this.customerID = new SimpleIntegerProperty(id);
        this.customerFullName = new SimpleStringProperty(fullname);
        this.customerAddress = new SimpleStringProperty(address);
        this.customerPostCode = new SimpleStringProperty(postcode);
        this.customerEmail = new SimpleStringProperty(email);
        this.customerType = new SimpleStringProperty(type);
        this.customerPhone = new SimpleIntegerProperty(phone);
        this.customerVehReg = new SimpleStringProperty(vehReg);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public String getCustomerFullName() {
        return customerFullName.get();
    }

    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public String getCustomerPostCode() {
        return customerPostCode.get();
    }

    public String getCustomerEmail() {
        return customerEmail.get();
    }

    public String getCustomerType() {
        return customerType.get();
    }

    public int getCustomerPhone() {
        return customerPhone.get();
    }

    public String getCustomerVehReg() {
        return customerVehReg.get();
    }

    public void setCustomerID(int number) {
        customerID.set(number);
    }

    public void setCustomerFullName(String name) {
        customerFullName.set(name);
    }

    public void setCustomerAddress(String address) {
        customerAddress.set(address);
    }

    public void setCustomerPostCode(String code) {
        customerPostCode.set(code);
    }

    public void setCustomerEmail(String email) {
        customerEmail.set(email);
    }

    public void setCustomerType(String type) {
        customerType.set(type);
    }

    public void setCustomerPhone(int number) {
        customerPhone.set(number);
    }

    public void setCustomerVehReg(String reg) {
        customerVehReg.set(reg);
    }

    //Property values
    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    public StringProperty customerFullNameProperty() {
        return customerFullName;
    }

    public StringProperty customerAddressProperty() {
        return customerAddress;
    }

    public StringProperty customerPostCodeProperty() {
        return customerPostCode;
    }

    public StringProperty customerEmailProperty() {
        return customerEmail;
    }

    public StringProperty customerTypeProperty() {
        return customerType;
    }

    public IntegerProperty customerPhoneProperty() {
        return customerPhone;
    }

    public StringProperty customerVehReg() {
        return customerVehReg;
    }
}
