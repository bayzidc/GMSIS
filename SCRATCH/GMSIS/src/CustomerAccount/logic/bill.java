/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.logic;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Joen
 */
public class bill {

    public IntegerProperty billID;
    public DoubleProperty totalCost;
    public StringProperty bookingID;
    public BooleanProperty billStatus;

    public bill(int billID, String bookingID, int costOfBill, boolean isSettled) {
        this.totalCost = new SimpleDoubleProperty(costOfBill);
        this.bookingID = new SimpleStringProperty(bookingID);
        this.billStatus = new SimpleBooleanProperty(isSettled);
        this.billID = new SimpleIntegerProperty(billID);
    }

    public double getTotalCost() {
        return totalCost.get();
    }

    public boolean getBillStatus() {
        return billStatus.get();
    }

    public String getBookingID() {
        return bookingID.get();
    }

    public int getBillID() {
        return billID.get();
    }

    public void setBillID(int ID) {
        billID.set(ID);
    }

    public void setTotalCost(int cost) {
        totalCost.set(cost);
    }

    public void setBookingID(String ID) {
        bookingID.set(ID);
    }

    public void setBillStatus(boolean check) {
        billStatus.set(check);
    }

    public DoubleProperty totalCost() {
        return totalCost;
    }

    public StringProperty bookingID() {
        return bookingID;
    }

    public void addCostToBill(bill Bill, PartsRecord.logic.partsUsed part, int quantity) {
        Bill.totalCost.set(totalCost.get()+(quantity*part.getCost()));
    }

}
