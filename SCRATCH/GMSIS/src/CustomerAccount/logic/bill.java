/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.logic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Joen
 */
public class bill {

    public DoubleProperty totalCost;
    public IntegerProperty bookingID;

    public bill(int ID, int costOfBill) {
        this.totalCost = new SimpleDoubleProperty(costOfBill);
        this.bookingID = new SimpleIntegerProperty(ID);
    }

    public double getTotalCost() {
        return totalCost.get();
    }

    public int getBookingID() {
        return bookingID.get();
    }

    public DoubleProperty totalCost() {
        return totalCost;
    }

    public IntegerProperty bookingID() {
        return bookingID;
    }

    public void getPriceFromPart(PartsRecord.logic.parts part) {
        totalCost.set(totalCost.get() + part.cost.get());
    }

}
